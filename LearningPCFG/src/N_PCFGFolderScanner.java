import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import symbol.StateSplitterType;


public class N_PCFGFolderScanner {

	private static int NUM_OF_FILES = 3000;
	private static int NUM_OF_INTERVALS = 10;
	
	private static int counter;
	private static int intervalCounter = 1;
	
	public static void main(String[] args){
		AbstractPCFGBuilder builder = new AbstractPCFGBuilder();
		StateSplitterType.setType(StateSplitterType.NAIVE);	
		
		File folder = new File("C:\\Users\\gvero\\java_projects\\java_projects");
		
		counter = 0;
		
		try{
		  listFilesForFolder(folder, builder);
		} catch(Throwable ex){
		  //System.out.println(ex);
		  ex.printStackTrace();
		}
		
		File file = new File("output.txt");
		PrintStream output = null;
		try {
			output = new PrintStream(file);
			builder.getStatistics().print(output);
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
	
	public static void listFilesForFolder(final File folder, AbstractPCFGBuilder builder) throws Throwable {
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
