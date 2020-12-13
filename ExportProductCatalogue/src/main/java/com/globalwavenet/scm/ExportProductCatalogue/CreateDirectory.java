package com.globalwavenet.scm.ExportProductCatalogue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CreateDirectory implements JavaDelegate {
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Properties prop = new Properties();
		InputStream inputStream = (CreateDirectory.class).getClassLoader().getResourceAsStream("app.properties");
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try{
			String exportDir=prop.get("exportDir").toString();
			File theExportDir = new File(exportDir);
			if (theExportDir.exists()){		        
		        Date date = Calendar.getInstance().getTime();  
		        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss");  
		        String strDate = dateFormat.format(date); 
		        String exportFileDir = "productCatalogue-" + strDate;
		        String exportOutputDir=exportDir + "/" + exportFileDir + "/";
		        System.out.println(exportOutputDir);
		        File theExportOutputDir = new File(exportOutputDir);
		        theExportOutputDir.mkdirs();
		        execution.setVariable("exportFileDir", exportFileDir);
			}else {
				throw new Exception();
			} 	
		}catch(Exception e){
			e.printStackTrace();
			throw new BpmnError("ExportError", "Error during create directory");
		}finally {
			System.out.println("Create directory Completed");
		}
	}	
}
