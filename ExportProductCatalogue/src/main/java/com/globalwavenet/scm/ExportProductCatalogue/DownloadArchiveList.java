package com.globalwavenet.scm.ExportProductCatalogue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class DownloadArchiveList implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Properties prop = new Properties();
		InputStream inputStream = (ExportProductCatalogue.class).getClassLoader().getResourceAsStream("app.properties");
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			String exportDir=prop.get("exportDir").toString();
			File theExportDir = new File(exportDir);
			if (theExportDir.exists()){
				List<String> archiveFileList = listFilesForFolder(theExportDir);
				ArchiveList archiveList = new ArchiveList();
				String contextPath=prop.get("contextPath").toString();
				archiveList.setContextPath(contextPath);
				archiveList.setPathList(archiveFileList);
				execution.setVariable("archiveList", archiveList);
			}else {
				throw new Exception();
			}
		}catch(Throwable e){
			e.printStackTrace();
			throw new BpmnError("DownloadError", "Error during list archieve");
		}finally {
			System.out.println("Archive download Completed");
		}	

	}
	
	public static List<String> listFilesForFolder(File theDir) {
		List<String> fileList = new ArrayList<String>();
	    for (File fileEntry : theDir.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            //listFilesForFolder(fileEntry);
	        	System.out.println("Skipping Directory");
	        } else {
	        	String path = fileEntry.getName();
	        	fileList.add(path);
	            System.out.println(path);
	        }
	    }
	    return fileList;
	}

}
