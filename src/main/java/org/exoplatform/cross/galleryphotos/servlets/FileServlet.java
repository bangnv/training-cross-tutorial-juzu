package org.exoplatform.cross.galleryphotos.servlets;

import java.io.IOException;
import java.util.List;

import javax.portlet.PortletException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticBuilder;
import org.exoplatform.cross.galleryphotos.GaleryContext;
import org.exoplatform.cross.galleryphotos.model.Content;
import org.exoplatform.cross.galleryphotos.model.CurrentRepositoryLifeCycle;
import org.exoplatform.cross.galleryphotos.model.Directory;
import org.exoplatform.cross.galleryphotos.model.Document;
import org.exoplatform.cross.galleryphotos.model.File;

public class FileServlet extends HttpServlet {
	private Chromattic chromattic;
	/** . */
	private String workspaceName;
	// HttpServlet is Serializable so, we should have a serialVersionUID
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		workspaceName = "portal-system";
		ChromatticBuilder builder = ChromatticBuilder.create();
		builder.add(Directory.class);
		builder.add(Content.class);
		builder.add(Document.class);
		builder.add(File.class);
		builder.setOptionValue(ChromatticBuilder.SESSION_LIFECYCLE_CLASSNAME,
				CurrentRepositoryLifeCycle.class.getName());
		builder.setOptionValue(ChromatticBuilder.CREATE_ROOT_NODE, true);
		builder.setOptionValue(ChromatticBuilder.ROOT_NODE_PATH, "/logomattic");
		chromattic = builder.build();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("do Get");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		GaleryContext model = new GaleryContext(chromattic, workspaceName, req,
				resp);
		try {
			if (ServletFileUpload.isMultipartContent(req)) {
				FileItem image = null;
				try {
					FileItemFactory factory = new DiskFileItemFactory();
					ServletFileUpload fu = new ServletFileUpload(factory);
					List<FileItem> list = fu.parseRequest(req);
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
					model.save(image);
				}
			}
		} finally {
			model.close();
		}
		StringBuffer buf = new StringBuffer();
		buf.append(req.getScheme()).append("://");
		buf.append(req.getServerName()).append(":");
		buf.append(req.getServerPort());
		buf.append("/portal/classic/home/juzu-gellery");
		resp.sendRedirect(buf.toString());
	}

}