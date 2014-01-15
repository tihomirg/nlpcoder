import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.eclipse.jdt.core.dom.ASTVisitor;

import builders.IBuilder;

public class N_Scanner {

	private static int fileNum;
	private static int intervalNum;
	private static int counter;
	private static int intervalCounter = 1;

	
	protected static void scan(IBuilder builder, File folder, File file){
		scan(builder, folder, file, 10000);
	}
	
	protected static void scan(IBuilder builder, File folder, File file, int fileNum){
		scan(builder, folder, file, fileNum, 10);
	}
	
	protected static void scan(IBuilder builder, File folder, File file, int fileNum, int intervalNum) {
		
		N_Scanner.fileNum = fileNum;
		N_Scanner.intervalNum = intervalNum;
		
		long startTime = System.currentTimeMillis();
		
		try{
		  listFilesForFolder(folder, builder);
		} catch(Throwable ex){
		  //System.out.println(ex);
		  ex.printStackTrace();
		}
		
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
		
		System.out.println("Scanned "+counter+" files in "+(System.currentTimeMillis() - startTime)+"ms");
	}

	public static void listFilesForFolder(final File folder, ASTVisitor builder)
			throws Throwable {
			    for (final File fileEntry : folder.listFiles()) {
			        if (fileEntry.isDirectory()) {
			            listFilesForFolder(fileEntry, builder);
			        } else {
			        	if (fileEntry.isFile() && fileEntry.getName().endsWith(".java")){
			               PCFGFileScanner.scan(fileEntry.getAbsolutePath(), builder);
			               counter++;
			               
			               if(counter >= fileNum){
			            	   throw new Exception("TERMINATED.");
			               } else {
			            	   if (counter >= intervalCounter *(fileNum / intervalNum)){
			            		   System.out.println("Processed "+counter+" files..."+intervalCounter *(100 / intervalNum)+"%");
			            		   
			            		   System.gc();
			            		   
			            		   intervalCounter++;
			            	   }
			               }
			        	}
			        }
			    }
			}
}