import java.io.File;

import builders.PCFGBuilder;

import symbol.StateSplitterType;


public class PCFGScanner {

	public static void main(String[] args){
		PCFGBuilder builder = new PCFGBuilder();
		StateSplitterType.setType(StateSplitterType.NAIVE);	
		
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
