import java.io.File;

import config.Config;

import builders.PCFGBuilder;



public class PCFGScanner {

	public static void main(String[] args){
		PCFGBuilder builder = new PCFGBuilder();
		Config.setType(Config.NAIVE);	
		
		File folder = new File("C:\\Users\\gvero\\java_projects\\java_projects\\chombo");
		
		listFilesForFolder(folder, builder);
		
		builder.getStatistics().print(System.out);		
	}
	
	public static void listFilesForFolder(final File folder, PCFGBuilder builder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry, builder);
	        } else {
	        	if (fileEntry.isFile() && fileEntry.getName().endsWith(".java")){
	               PCFGFileScanner.scan(fileEntry.getAbsolutePath(), builder);
	        	}
	        }
	    }
	}	
	
	
}
