import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import symbol.StateSplitterType;


public class PCFGFolderScanner {

	public static void main(String[] args){
		AbstractPCFGBuilder builder = new AbstractPCFGBuilder();
		StateSplitterType.setType(StateSplitterType.NAIVE);	
		
		File folder = new File("C:\\Users\\gvero\\java_projects\\java_projects\\chombo");
		
		listFilesForFolder(folder, builder);
		
		builder.getStatistics().print(System.out);		
	}
	
	public static void listFilesForFolder(final File folder, AbstractPCFGBuilder builder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry, builder);
	        } else {
	        	if (fileEntry.isFile() && fileEntry.getName().endsWith(".java")){
	               PCFGFileScanner.collect(fileEntry.getAbsolutePath(), builder);
	        	}
	        }
	    }
	}	
	
	
}
