


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import linker.PackageCollector;

import org.eclipse.jdt.core.dom.ASTVisitor;

import symbol.StateSplitterType;

public class N_ImporFolderScanner {

	private static int NUM_OF_FILES = 10000;
	private static int NUM_OF_INTERVALS = 10;
	
	private static int counter;
	private static int intervalCounter = 1;
	
	public static void main(String[] args){
		PackageCollector builder = new PackageCollector();
		
		File folder = new File("C:\\Users\\gvero\\java_projects\\java_projects");
		
		counter = 0;
		
		try{
		  listFilesForFolder(folder, builder);
		} catch(Throwable ex){
		  //System.out.println(ex);
		  ex.printStackTrace();
		}
		
		File file = new File("naive.txt");
		PrintStream output = null;
		try {
			output = new PrintStream(file);
			builder.print(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (output != null) {
			output.flush();
			output.close();
		}
		
		System.out.println("Scanned "+counter+" files.");		
	}
	
	public static void listFilesForFolder(final File folder, ASTVisitor builder) throws Throwable {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry, builder);
	        } else {
	        	if (fileEntry.isFile() && fileEntry.getName().endsWith(".java")){
	               PCFGFileScanner.collect(fileEntry.getAbsolutePath(), builder);
	               counter++;
	               
	               if(counter >= NUM_OF_FILES){
	            	   throw new Exception("TERMINATED.");
	               } else {
	            	   if (counter >= intervalCounter *(NUM_OF_FILES / NUM_OF_INTERVALS)){
	            		   System.out.println("Processed "+counter+" files...");
	            		   
	            		   System.gc();
	            		   
	            		   intervalCounter++;
	            	   }
	               }
	        	}
	        }
	    }
	}	
	
	
}
