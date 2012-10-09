package org.exoplatform.cross.galleryphotos.servlets;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.cross.galleryphotos.model.File;
import org.exoplatform.portal.webui.util.Util;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.jcr.ext.hierarchy.NodeHierarchyCreator;

public class FileServlet extends HttpServlet {

	private String baseURI = null;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (baseURI == null) {
			baseURI = request.getScheme() + "://" + request.getServerName()
					+ ":" + String.format("%s", request.getServerPort());
		}
		try {
			if (ServletFileUpload.isMultipartContent(request)) {
				FileItem image = null;
				try {
					FileItemFactory factory = new DiskFileItemFactory();
					ServletFileUpload fu = new ServletFileUpload(factory);
					List<FileItem> list = fu.parseRequest(request);
					for (FileItem item : list) {
						if (item.getFieldName().equals("uploadedimage")) {
							if (item.getContentType().startsWith("image/")) {
								image = item;
								break;
							}
						}
					}
				} catch (FileUploadException e) {
					throw new ServletException("Could not process file upload",
							e);
				}
				if (image != null) {
					storeFile(image);
				}
			}
		} finally {
		}
		StringBuffer buff = new StringBuffer();
		buff.append(baseURI);
		buff.append("/portal/classic/home/juzu-gellery");
		response.sendRedirect(buff.toString());
	}

	private void storeFile(FileItem item) {
		String filename = item.getName();
		RepositoryService repositoryService = (RepositoryService) PortalContainer
				.getInstance().getComponentInstanceOfType(
						RepositoryService.class);
		NodeHierarchyCreator nodeHierarchyCreator = (NodeHierarchyCreator) PortalContainer
				.getInstance().getComponentInstanceOfType(
						NodeHierarchyCreator.class);

		SessionProvider sessionProvider = SessionProvider
				.createSystemProvider();
		try {
			// get info
			Session session = sessionProvider.getSession("portal-system",
					repositoryService.getCurrentRepository());

			Node rootNode = session.getRootNode();
			// Node homeNode = rootNode.getNode("Private");

			Node docNode;
			if (!rootNode.hasNode("Pictures")) {
				rootNode.addNode("Pictures", "nt:folder");
				rootNode.save();
			}
			docNode = rootNode.getNode("Pictures");
			if (!docNode.hasNode(filename)) {

				Node fileNode = docNode.addNode(filename, "nt:file");
				Node jcrContent = fileNode
						.addNode("jcr:content", "nt:resource");
				jcrContent.setProperty("jcr:data", item.getInputStream());
				jcrContent.setProperty("jcr:lastModified",
						Calendar.getInstance());
				jcrContent.setProperty("jcr:encoding", "UTF-8");
				jcrContent.setProperty("jcr:mimeType", item.getContentType());
				docNode.save();
				session.save();
			}
		} catch (Exception e) {
			System.out.println("JCR::" + e.getMessage());
		} finally {
			sessionProvider.close();
		}
	}
}