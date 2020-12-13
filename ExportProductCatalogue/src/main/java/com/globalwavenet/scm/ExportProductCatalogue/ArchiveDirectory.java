package com.globalwavenet.scm.ExportProductCatalogue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


public class ArchiveDirectory implements JavaDelegate {
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Properties prop = new Properties();
		InputStream inputStream = (ArchiveDirectory.class).getClassLoader().getResourceAsStream("app.properties");
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
		        String exportOutputDir=exportDir + "/" + exportFileDir + "/";
		        File theExportOutputDir = new File(exportOutputDir);
		        System.out.println(theExportOutputDir.getPath());
		        if (theExportOutputDir.exists()) {
					zipFile(theExportOutputDir, exportOutputDir, exportFileDir);
					deleteDirectory(theExportOutputDir);
					clearOldArchive(theExportDir);
		        }
			}else {
				throw new Exception();
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BpmnError("ArchiveError", "Error during archiving directory");
		}finally {
			System.out.println("Archive Directory Completed");

		}

	}
	private static void zipFile(File inFolder, String filePath, String zipName) throws Exception {
        File outFolder = new File(filePath + "../" + zipName + ".zip");
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                new FileOutputStream(outFolder)));
        BufferedInputStream in = null;
        byte[] data = new byte[1000];
        String files[] = inFolder.list();
        for (int i = 0; i < files.length; i++) {
            in = new BufferedInputStream(new FileInputStream(
                    inFolder.getPath() + "/" + files[i]), 1000);
            out.putNextEntry(new ZipEntry(files[i]));
            int count;
            while ((count = in.read(data, 0, 1000)) != -1) {
                out.write(data, 0, count);
            }
            out.closeEntry();
            in.close();
        }
        out.flush();
        out.close();
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
	
    private static void clearOldArchive(File path){
    	File[] filesArray = path.listFiles();
    	List<File> files = Arrays.asList(filesArray);
        files.stream()
                .filter((File p) -> p.getName().matches(".*zip"))
                .sorted(getReverseLastModifiedComparator())
                .skip(10)
                // to delete the file but keep the most recent ten
                .forEach(x -> ((File) x).delete());
                // or display the filenames which would be deleted
                //.forEach((x) -> System.out.printf("would be deleted: %s%n", x));
    }
	
    private static Comparator<File> getReverseLastModifiedComparator() {
        return (File o1, File o2) -> {
            if (o1.lastModified() < o2.lastModified()) {
                return 1;
            }
            if (o1.lastModified() > o2.lastModified()) {
                return -1;
            }
            return 0;
        };
    }
}
