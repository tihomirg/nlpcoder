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


public class NewMain {

	public static void main(String[] args){
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		char[] fileContent = readFile("CityImpl.java");
		
		parser.setSource(fileContent);
		Map options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_7, options);
		parser.setCompilerOptions(options);
		
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setStatementsRecovery(true);
 
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		AbstractPCFGBuilder builder = new AbstractPCFGBuilder();
		StateSplitterType.setType(StateSplitterType.WITH_GRANDAD);
		
		cu.accept(builder);
		
		builder.getStatistics().print(System.out);
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
