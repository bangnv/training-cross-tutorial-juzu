/*
 * Copyright (C) 2010 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.exoplatform.cross.galleryphotos;

import java.io.IOException;

import javax.jcr.Credentials;
import javax.jcr.SimpleCredentials;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.chromattic.api.Chromattic;
import org.exoplatform.cross.galleryphotos.model.Document;
import org.exoplatform.cross.galleryphotos.model.Model;

/**
 * The logomattic context extends the {@link Model} class to provide a runtime
 * content for the portlet environment. A context is associated with a portlet
 * request with full access to the {@link PortletRequest} and
 * {@link PortletResponse} objects.
 * 
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class GaleryContext extends Model {

	/** . */
	private HttpServletRequest request;

	/** . */
	private HttpServletResponse response;

	/** . */
	private final String workspaceName;

	public GaleryContext(Chromattic chromattic, String workspaceName,
			HttpServletRequest request, HttpServletResponse response) {
		super(chromattic.openSession(new SimpleCredentials("__system","[]".toCharArray()),workspaceName));
		this.request = request;
		this.response = response;
		this.workspaceName = workspaceName;
	}

	/**
	 * Returns the URL that will invoke the update of the current image with the
	 * specified document.
	 * 
	 * @param doc
	 *            the document to use
	 * @return an URL
	 */
	

	
	public String getUseImageURL(Document doc) {
		// PortletURL url = ((RenderResponse) response).createActionURL();
		// url.setParameter("docid", doc.getId());
		// url.setParameter("action", "use");
		// return url.toString();
		return "";
	}

	/**
	 * Returns the URL that will invoke the removals of the specified document.
	 * 
	 * @param doc
	 *            the document to remove
	 * @return an URL
	 */
	public String getRemoveImageURL(Document doc) {
		// PortletURL url = ((RenderResponse) response).createActionURL();
		// url.setParameter("docid", doc.getId());
		// url.setParameter("action", "remove");
		// return url.toString();
		return "";
	}

	/**
	 * Returns the URL that will serve the specified document.
	 * 
	 * @param doc
	 *            the document to serve
	 * @return an URL
	 */
	public String getImageURL(Document doc) {
		String path = doc.getPath();
		return "/rest/private/jcr/repository/" + workspaceName + path;
	}

	/**
	 * Returns the URL of the image to display. The URL is computed using the
	 * portlet preferences. When no such preferences exist, then a default URL
	 * is returned.
	 * 
	 * @return an URL
	 */
	public String getImageURL() {
		// PortletPreferences prefs = request.getPreferences();
		// String url = prefs.getValue("url", null);
		// if (url == null) {
		// url = request.getContextPath() + "/bubbles.png";
		// } else if (!url.startsWith("http://")) {
		// Document doc = findDocumentById(url);
		// if (doc != null) {
		// url = getImageURL(doc);
		// }
		// }
		// return url;
		return "";
	}

	/**
	 * Saves the specified image in the repository.
	 * 
	 * @param image
	 *            the image to save
	 * @throws IOException
	 *             any IOException
	 */
	public void save(FileItem image) throws IOException {
		getRoot().saveDocument(image.getName(), image.getContentType(),
				image.getInputStream());
		save();
	}

	public String getWorkspaceName() {
		return workspaceName;
	}

}
