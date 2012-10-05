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

import java.io.File;

import javax.inject.Inject;

import juzu.Action;
import juzu.Param;
import juzu.Path;
import juzu.Resource;
import juzu.View;

/**
 * Created by The eXo Platform SAS Author : Nguyen Viet Bang
 * bangnv@exoplatform.com Oct 3, 2012
 */
public class Controller extends juzu.Controller {

	@Inject
	@Path("index.gtmpl")
	org.exoplatform.cross.galleryphotos.templates.index index;

	// juzu.template.Template index;

	
	@View
	public void index() {
		index.with().sendFileURL(getSendFileURL()).render();
		// index.render();
	}

	@Action
	@Resource
	public void uploadFile() {
		System.out.println("vao day");
	}

	private String getSendFileURL() {
		StringBuilder b = new StringBuilder();
	    String temp = httpContext.getContextPath();
	    b.append(httpContext.getScheme()).append("://");
	    b.append(httpContext.getServerName()).append(":");
	    b.append(httpContext.getServerPort());
	    b.append(temp+ "/sendFile");
		return b.toString();

	}

}
