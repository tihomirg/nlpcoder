import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.NumberFormat;

import org.eclipse.jdt.core.dom.ASTVisitor;

import config.Config;

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
	
	private static long startTime;
	
	protected static void scan(IBuilder builder, File folder, File file, int fileNum, int intervalNum) {
		
		N_Scanner.fileNum = fileNum;
		N_Scanner.intervalNum = intervalNum;
		
		startTime = System.currentTimeMillis();
		
		long fstartTime = startTime;
		
		try{
			scanProjects(folder, builder);
		} catch(ScannerException ex){
			System.out.println(ex);
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
		
		System.out.println("Scanned "+counter+" files in "+(System.currentTimeMillis() - fstartTime)+"ms");
	}

	
	public static void scanProjects(final File folder, IBuilder builder)
			throws Throwable {
		int projCount = 0;
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry, builder);
	        }
	        projCount++;
	        
	        if(projCount != 0 && projCount % 100 == 0){
	        	System.out.println("So far "+projCount+" projects processed.");
	        }
	    }
		
	}
	
	
	public static void listFilesForFolder(final File folder, IBuilder builder)
			throws Throwable {
			    for (final File fileEntry : folder.listFiles()) {
			        if (fileEntry.isDirectory()) {
			            listFilesForFolder(fileEntry, builder);
			        } else {
			        	if (fileEntry.isFile() && fileEntry.getName().endsWith(".java")){
			        	   //System.out.println(fileEntry.getAbsolutePath());
			        	   PCFGFileScanner.scan(fileEntry.getAbsolutePath(), builder);
			               counter++;
			               
			               if(counter >= fileNum){
			            	   throw new ScannerException("TERMINATED.");
			               } else {
			            	   if (counter >= intervalCounter *(fileNum / intervalNum)){
			            		   System.out.println("Processed "+counter+" files..."+intervalCounter *(100 / intervalNum)+"%   in "
			            	           +(System.currentTimeMillis() - startTime)/1000 +"s");
			            		   
			            		    Runtime runtime = Runtime.getRuntime();
			            	        NumberFormat format = NumberFormat.getInstance();
			            	        StringBuilder sb = new StringBuilder();
			            	        long maxMemory = runtime.maxMemory();
			            	        long allocatedMemory = runtime.totalMemory();
			            	        long freeMemory = runtime.freeMemory();
			            	        sb.append("Free memory: ");
			            	        sb.append(format.format(freeMemory / 1024));
			            	        sb.append("  ");
			            	        sb.append("Allocated memory: ");
			            	        sb.append(format.format(allocatedMemory / 1024));
			            	        sb.append("  ");
			            	        sb.append("Max memory: ");
			            	        sb.append(format.format(maxMemory / 1024));
			            	        sb.append("  ");
			            	        sb.append("Total free memory: ");
			            	        sb.append(format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024));
			            	        System.out.println(sb.toString());     		   
			            		   
			            		   startTime = System.currentTimeMillis();
			            		   
			            		   builder.releaseUnder(0);
			            		   
			            		   
			            		   System.out.println(Config.getFactory());
			            		   
			            		   Config.getFactory().clear();
			            		   
			            		   System.out.println();
			            		   System.out.println();
			            		   
			            		   System.gc();
			            		   Runtime.getRuntime().runFinalization();
			            		   
			            		   intervalCounter++;
			            	   }
			               }
			        	}
			        }
			    }
			}
}