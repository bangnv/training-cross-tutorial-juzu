package org.exoplatform.cross.galleryphotos.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.ext.common.SessionProvider;

//@Named("documentsData")
//@SessionScoped
public class DocumentsData {
	RepositoryService repositoryService;

	private static DocumentsData instance;

	private DocumentsData() {
		repositoryService = (RepositoryService) PortalContainer.getInstance()
				.getComponentInstanceOfType(RepositoryService.class);
	}

	public static DocumentsData getInstance() {
		if (instance == null) {
			instance = new DocumentsData();
		}
		return instance;
	}

	public List<File> getNodes() {
		SessionProvider sessionProvider = SessionProvider
				.createSystemProvider();
		List<File> files = new ArrayList<File>();
		try {
			Session session = sessionProvider.getSession("portal-system",
					repositoryService.getCurrentRepository());
			Node rootNode = session.getRootNode();

			Node docNode;
			if (!rootNode.hasNode("Pictures")) {
				rootNode.addNode("Pictures", "nt:folder");
				rootNode.save();
			}
			docNode = rootNode.getNode("Pictures");
			NodeIterator nodes = docNode.getNodes();
			while (nodes.hasNext()) {
				Node node = nodes.nextNode();
				File file = getFileFromNode(node);
				files.add(file);
			}

		} catch (Exception e) {
			System.out.println("JCR::\n" + e.getMessage());
		} finally {
			sessionProvider.close();
		}
		if (files.size() > 0) {
			return files;
		} else
			return null;
	}

	private File getFileFromNode(Node node) throws Exception {
		File file = new File();
		file.setName(node.getName());
		if (node.hasNode("jcr:content")) {
			Node contentNode = node.getNode("jcr:content");
			if (contentNode.hasProperty("jcr:data")) {
				double size = contentNode.getProperty("jcr:data").getLength();
				String fileSize = calculateFileSize(size);
				file.setSize(fileSize);
			}
		}
		file.setPathNode(node.getPath());
		String url = "http://localhost:8080"
				+ "/rest/private/jcr/repository/portal-system" + node.getPath();
		file.setPublicUrl(url);
		return file;
	}

	public static String calculateFileSize(double fileLengthLong) {
		int fileLengthDigitCount = Double.toString(fileLengthLong).length();
		double fileSizeKB = 0.0;
		String howBig = "";
		if (fileLengthDigitCount < 5) {
			fileSizeKB = Math.abs(fileLengthLong);
			howBig = "Byte(s)";
		} else if (fileLengthDigitCount >= 5 && fileLengthDigitCount <= 6) {
			fileSizeKB = Math.abs((fileLengthLong / 1024));
			howBig = "KB";
		} else if (fileLengthDigitCount >= 7 && fileLengthDigitCount <= 9) {
			fileSizeKB = Math.abs(fileLengthLong / (1024 * 1024));
			howBig = "MB";
		} else if (fileLengthDigitCount > 9) {
			fileSizeKB = Math.abs((fileLengthLong / (1024 * 1024 * 1024)));
			howBig = "GB";
		}
		String finalResult = roundTwoDecimals(fileSizeKB);
		return finalResult + " " + howBig;
	}

	private static String roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return twoDForm.format(d);
	}

	public void deleteNode(String pathNode) {
		SessionProvider sessionProvider = SessionProvider
				.createSystemProvider();
		try {
			Session session = sessionProvider.getSession("portal-system",
					repositoryService.getCurrentRepository());
			Node rootNode = session.getRootNode();
			Node picturesNode;
			if (!rootNode.hasNode("Pictures")) {
				return;
			}
			picturesNode = rootNode.getNode("Pictures");
			NodeIterator nodes = picturesNode.getNodes();
			List<File> files = new ArrayList<File>();
			while (nodes.hasNext()) {
				Node node = nodes.nextNode();
				if (node.getPath().equalsIgnoreCase(pathNode))
					;
				{
					node.remove();
					session.save();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("JCR::\n" + e.getMessage());
		} finally {
			sessionProvider.close();
		}

	}

}
