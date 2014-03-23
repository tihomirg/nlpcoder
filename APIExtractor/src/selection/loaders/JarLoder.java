package selection.loaders;

import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.bcel.classfile.ClassParser;

import definitions.ClassInfo;

import selection.IWordExtractor;

public class JarLoder implements IJarLoader {

	@Override
	public Map<String, ClassInfo> getClassFiles(List<String> jarFiles, IWordExtractor extractor) {
		for(String jarFile: jarFiles){

			JarFile jar;
			try {
				jar = new JarFile(jarFile);
				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					JarEntry file = entries.nextElement();

					if (!file.isDirectory() && file.getName().endsWith(".class")){
						new ClassInfo(new ClassParser(jar.getInputStream(file), null).parse(), extractor);
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
