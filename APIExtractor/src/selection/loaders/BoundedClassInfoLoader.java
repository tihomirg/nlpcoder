package selection.loaders;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.bcel.classfile.ClassParser;
import definitions.ClassInfo;
import selection.types.InitialTypeFactory;

public class BoundedClassInfoLoader extends ClassInfoLoader {
	
	private int maxToScan = 1000;
	private int scanned = 0;
	
	public BoundedClassInfoLoader(int maxToScan) {
		this.maxToScan = maxToScan;
	}

	@Override
	public void load(List<String> jarFiles, InitialTypeFactory factory) {
		exit: for(String jarFile: jarFiles){

			JarFile jar;
			try {
				jar = new JarFile(jarFile);
				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					JarEntry file = entries.nextElement();

					file.isDirectory();
					if (!file.isDirectory() && file.getName().endsWith(".class")){
						ClassInfo.getClassInfo(new ClassParser(jar.getInputStream(file), null).parse(), factory, getClassesMap());

						scanned++;
						if(scanned >= maxToScan){
							break exit;
						}			
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
       factory.connectTypesAndClassInfos(getClassesMap());	
	}
}
