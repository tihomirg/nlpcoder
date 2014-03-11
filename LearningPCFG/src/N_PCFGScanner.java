import java.io.File;

import config.Config;

public class N_PCFGScanner extends N_Scanner {
	
	protected static File setTypeAndGetOutputFile(int type){
		switch(type){
		  case Config.NAIVE : 
			  Config.setType(Config.NAIVE);
			  return new File("naive.txt");
		  case Config.WITH_PARENT: 
			  Config.setType(Config.WITH_PARENT);	
			  return new File("with_parent.txt");
		  case Config.WITH_GRANDAD: 
			  Config.setType(Config.WITH_GRANDAD);	
			  return new File("with_parent.txt");
		  default: 
			  Config.setType(Config.NAIVE);	
			  return new File("naive.txt");
		}
	}
}
