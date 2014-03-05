package selection.loaders;

import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import selection.IWordExtractor;
import selection.WordExtractorFromName;

public class BoundedJarLoader implements IJarLoader {
	
	private int maxToScan = 1000;
	private int scanned = 0;
	
	public BoundedJarLoader(int maxToScan) {
		this.maxToScan = maxToScan;
	}

	@Override
	public ClassLoader[] getClassFiles(List<String> jarFiles, IWordExtractor extractor) {
		List<ClassLoader> files = new LinkedList<ClassLoader>();

		exit: for(String jarFile: jarFiles){

			JarFile jar;
			try {
				jar = new JarFile(jarFile);
				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					JarEntry file = entries.nextElement();

					file.isDirectory();
					if (!file.isDirectory() && file.getName().endsWith(".class")){
						files.add(new ClassLoader(jar.getInputStream(file), extractor));

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
		return files.toArray(new ClassLoader[files.size()]);
	}
}
