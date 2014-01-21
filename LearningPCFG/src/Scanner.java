import java.io.File;

import config.Config;

import builders.BasicBuilder;



public class Scanner {

	public static void main(String[] args){
		BasicBuilder builder = new BasicBuilder();
		Config.setType(Config.NAIVE);	
		
		File folder = new File("C:\\Users\\gvero\\java_projects\\java_projects\\chombo");
		
		listFilesForFolder(folder, builder);
		
		builder.getStatistics().print(System.out);		
	}
	
	public static void listFilesForFolder(final File folder, BasicBuilder builder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry, builder);
	        } else {
	        	if (fileEntry.isFile() && fileEntry.getName().endsWith(".java")){
	               FileScanner.scan(fileEntry.getAbsolutePath(), builder);
	        	}
	        }
	    }
	}	
	
	
}
