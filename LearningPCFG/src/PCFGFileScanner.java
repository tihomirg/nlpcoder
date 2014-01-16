import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

import config.Config;

import builders.PCFGBuilder;



public class PCFGFileScanner {

	public static void main(String[] args){
		String fileName = "CityImpl.java";
		PCFGBuilder builder = new PCFGBuilder();
		Config.setType(Config.NAIVE);
		
		scan(fileName, builder);
		
		builder.getStatistics().print(System.out);
	}

	public static void scan(String fileName, ASTVisitor builder) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		char[] fileContent = readFile(fileName);
		
		parser.setSource(fileContent);
		Map options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_7, options);
		parser.setCompilerOptions(options);
		
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setStatementsRecovery(true);	
		
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(builder);
	}

	private static char[] readFile(String fileName){
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = null;
		
		try{
		
		  reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
		
		  String line = null;
		  while ((line = reader.readLine()) != null) {
		    sb.append(line+"\n");
		  }
		
		} catch(IOException e){
		} finally{
			
		  if (reader != null)
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
		
		return sb.toString().toCharArray();
	}
}
