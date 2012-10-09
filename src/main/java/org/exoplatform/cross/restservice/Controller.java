package org.exoplatform.cross.restservice;

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
	juzu.template.Template index;

	@View
	public void index() throws IOException {
		 index.render();
//		index("", "");
	}

//	@View
//	public void index(String location, String mapURL) throws IOException {
//		index.with().location(location).mapURL(mapURL).render();
//	}

//	@Action
//	public Response updateLocation(String location) throws IOException {
//		String mapURL = "https://maps.google.fr/maps?f=q&source=s_q&hl=en&geocode=&q="
//				+ location
//				+ "&aq=&t=m&ie=UTF8&hq=&hnear="
//				+ location
//				+ "&z=12&output=embed";
//		return Controller_.index(location, mapURL);
//	}

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
