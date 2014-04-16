package selection.loaders;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import selection.IWordExtractor;
import selection.types.InitialTypeFactory;
import definitions.ClassInfo;

public class TargetJarLoader implements IJarLoader {

	private int maxToScan = 1000;
	private int scanned = 0;
	private Set<String> pkg;

	public TargetJarLoader(int maxToScan, String[] pkg) {
		this.maxToScan = maxToScan;
		this.pkg = new HashSet<String>(Arrays.asList(pkg));
	}

	@Override
	public Map<String, ClassInfo> getClassFiles(List<String> jarFiles, IWordExtractor extractor, InitialTypeFactory factory) {
		exit: for(String jarFile: jarFiles){

			JarFile jar;
			try {
				jar = new JarFile(jarFile);
				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					JarEntry file = entries.nextElement();
					
					if (!file.isDirectory() && file.getName().endsWith(".class")){
						String fullName = file.getName();

						String pkgName = fullName.substring(0, fullName.lastIndexOf("/")).replace("/", ".");
						if (this.pkg.contains(pkgName)){
							System.out.println(file);						
							new ClassInfo(new ClassParser(jar.getInputStream(file), null).parse(), extractor, factory);

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