package selection.loaders;

import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import selection.IWordExtractor;
import selection.WordExtractorFromName;

public class JarLoder implements IJarLoader {

	@Override
	public ClassLoader[] getClassFiles(List<String> jarFiles, IWordExtractor extractor) {
		List<ClassLoader> files = new LinkedList<ClassLoader>();

		for(String jarFile: jarFiles){

			JarFile jar;
			try {
				jar = new JarFile(jarFile);
				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					JarEntry file = entries.nextElement();

					if (!file.isDirectory() && file.getName().endsWith(".class")){
						files.add(new ClassLoader(jar.getInputStream(file), extractor));
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return files.toArray(new ClassLoader[files.size()]);
	}
}
