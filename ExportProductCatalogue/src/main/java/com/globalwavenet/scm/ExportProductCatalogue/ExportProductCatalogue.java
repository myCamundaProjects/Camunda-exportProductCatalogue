package com.globalwavenet.scm.ExportProductCatalogue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


import tutorial_project.talendexpprodcatalogue_0_1.TalendExpProdCatalogue;

public class ExportProductCatalogue implements JavaDelegate {
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
			String postgresHost=prop.get("postgresHost").toString();
			String postgresPort=prop.get("postgresPort").toString();
			String postgresUser=prop.get("postgresUser").toString();
			String postgresPass=prop.get("postgresPass").toString();
			String postgresDatabase=prop.get("postgresDatabase").toString();
			String partnerManagementEndPoint=prop.get("partnerManagementEndPoint").toString();
			String prtnerManagementPartnersRelativePath=prop.get("prtnerManagementPartnersRelativePath").toString();
			String exportDir=prop.get("exportDir").toString();
			String exportFileDir = (String)execution.getVariable("exportFileDir");
	        String exportOutputDir=exportDir + "/" + exportFileDir + "/";
			String exportFile=prop.get("exportFile").toString();
			String exportOutput= exportOutputDir + exportFile; 

			TalendExpProdCatalogue talendJob=new TalendExpProdCatalogue();
			talendJob.runJob(new String[]{"--context_param postgresHost="+postgresHost,
										"--context_param postgresPort="+postgresPort,
										"--context_param postgresUser="+postgresUser,
										"--context_param postgresPass="+postgresPass,
										"--context_param postgresDatabase="+postgresDatabase,
										"--context_param partnerManagementEndPoint="+partnerManagementEndPoint,
										"--context_param prtnerManagementPartnersRelativePath="+prtnerManagementPartnersRelativePath,
										"--context_param exportOutput="+exportOutput
										});
										
			talendJob.destroy();
		}catch(Exception e){
			e.printStackTrace();
			throw new BpmnError("ExportError", "Error during exporting product catalogue ");
		}finally {
			System.out.println("ExportProductCatalogue Completed");
		}
	}
}
