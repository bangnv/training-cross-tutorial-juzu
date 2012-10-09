package org.exoplatform.cross.mapservice;

import java.io.IOException;

import javax.inject.Inject;
import juzu.Path;
import juzu.Resource;
import juzu.Response;
import juzu.View;
import juzu.io.Stream;
import juzu.plugin.ajax.Ajax;

public class Controller {

	@Inject
	@Path("index.gtmpl")
	org.exoplatform.cross.mapservice.templates.index index;

	@View
	public void index() throws IOException {
		index("", "");
	}

	@View
	public void index(String location, String mapURL) throws IOException {
		index.with().location(location).mapURL(mapURL).render();
	}

	@Ajax
	@Resource
	public Response.Content<Stream.Char> getMapURL(String location)
			throws IOException {
		System.out.println("helloo----------------------");
		String mapURL = "https://maps.google.fr/maps?f=q&source=s_q&hl=en&geocode=&q="
				+ location
				+ "&aq=&t=m&ie=UTF8&hq=&hnear="
				+ location
				+ "&z=12&output=embed";
		return Response.ok("{\"mapURL\": \"" + mapURL + "\"}").withMimeType(
				"application/json");
	}
}
