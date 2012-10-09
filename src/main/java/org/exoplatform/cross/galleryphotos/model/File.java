package org.exoplatform.cross.galleryphotos.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class File {
	String name;
	Calendar createdDate;
	String size;
	String pathNode;
	String publicUrl;
	String uuid;

	public String getPathNode() {
		return pathNode;
	}

	public void setPathNode(String pathNode) {
		this.pathNode = pathNode;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatedDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		return formatter.format(createdDate.getTime());
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public String getIcon() {
		return "/rest/thumbnailImage/custom/32x32/repository/collaboration"
				+ pathNode;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPublicUrl() {
		return publicUrl;
	}

	public void setPublicUrl(String publicUrl) {
		this.publicUrl = publicUrl;
	}

}
