package com.globalwavenet.scm.ExportProductCatalogue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


public class CompensateExport implements JavaDelegate {
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Properties prop = new Properties();
		InputStream inputStream = (CompensateExport.class).getClassLoader().getResourceAsStream("app.properties");
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try{
			String exportDir=prop.get("exportDir").toString();
			File theExportDir = new File(exportDir);
			if (theExportDir.exists()){		        
		        String exportFileDir = (String)execution.getVariable("exportFileDir");
		        String exportOutputDir=exportDir + "/" + exportFileDir;
		        System.out.println(exportOutputDir);
		        File theExportOutputDir = new File(exportOutputDir);
		        File theExportZipDir = new File(exportOutputDir + ".zip");
		        System.out.println(theExportZipDir);
		        if (theExportOutputDir.exists()) {
		        	deleteDirectory(theExportOutputDir);
		        }
		        if (theExportZipDir.exists()) {
		        	theExportZipDir.delete();
		        }
			}else {
				throw new Exception();
			} 
				
		}catch(Throwable e){
			e.printStackTrace();
			throw new BpmnError("ExportError", "Error during compensating export");
		}finally {
			System.out.println("Compensate export completed");

		}

	}
	
	static public boolean deleteDirectory(File path) {
	    if (path.exists()) {
	        File[] files = path.listFiles();
	        for (int i = 0; i < files.length; i++) {
	            if (files[i].isDirectory()) {
	                deleteDirectory(files[i]);
	            } else {
	                files[i].delete();
	            }
	        }
	    }
	    return (path.delete());
	}

}
