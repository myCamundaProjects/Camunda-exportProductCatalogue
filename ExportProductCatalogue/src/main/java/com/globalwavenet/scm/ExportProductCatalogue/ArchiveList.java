package com.globalwavenet.scm.ExportProductCatalogue;

import java.util.ArrayList;
import java.util.List;


public class ArchiveList {
	  protected String contextPath;
	  protected List<String> pathList = new ArrayList<String>();
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public List<String> getPathList() {
		return pathList;
	}
	public void setPathList(List<String> pathList) {
		this.pathList = pathList;
	}

}
