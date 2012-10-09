/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.cross.galleryphotos;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import juzu.Action;
import juzu.Path;
import juzu.Resource;
import juzu.Response;
import juzu.SessionScoped;
import juzu.View;

import org.exoplatform.cross.galleryphotos.model.DocumentsData;
import org.exoplatform.cross.galleryphotos.model.File;

/**
 * Created by The eXo Platform SAS Author : Nguyen Viet Bang
 * bangnv@exoplatform.com Oct 3, 2012
 */
@SessionScoped
public class Controller extends juzu.Controller {

	// @Inject
	// DocumentsData documentsData;

	@Inject
	@Path("index.gtmpl")
	juzu.template.Template index;

	@Inject
	@Path("documents.gtmpl")
	org.exoplatform.cross.galleryphotos.templates.documents documents;

	@View
	public void index() {
		index.render();
	}

	@Resource
	public void getDocuments() {
		List<File> files = DocumentsData.getInstance().getNodes();
		if (files != null && files.size() > 0) {
			files = DocumentsData.getInstance().getNodes();
		} else
			files = new ArrayList<File>();
		documents.with().files(files).render();
	}

	@Action
	public void deleteFile(String pathNode) {
		DocumentsData.getInstance().deleteNode(pathNode);
		index.render();
	}

}
