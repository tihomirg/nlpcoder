package selection.loaders;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import selection.IWordExtractor;
import definitions.ClassInfo;

public class TargetJarLoader implements IJarLoader {

	private int maxToScan = 1000;
	private int scanned = 0;
	private String pkg;

	public TargetJarLoader(int maxToScan, String pkg) {
		this.maxToScan = maxToScan;
		this.pkg = pkg;
	}

	@Override
	public Map<String, ClassInfo> getClassFiles(List<String> jarFiles, IWordExtractor extractor) {
		exit: for(String jarFile: jarFiles){

			JarFile jar;
			try {
				jar = new JarFile(jarFile);
				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					JarEntry file = entries.nextElement();
					
					if (!file.isDirectory() && file.getName().endsWith(".class")){
						String fullName = file.getName();
						System.out.println(file);
						String pkgName = fullName.substring(0, fullName.lastIndexOf("/")).replace("/", ".");
						if (pkgName.equals(this.pkg)){
						
							new ClassInfo(new ClassParser(jar.getInputStream(file), null).parse(), extractor);

							scanned++;
							if(scanned >= maxToScan){
								break exit;
							}
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	return ClassInfo.getClasses();
	}
}