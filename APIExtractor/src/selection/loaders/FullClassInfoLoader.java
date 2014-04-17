package selection.loaders;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.bcel.classfile.ClassParser;
import definitions.InitialClassInfoFactory;

public class FullClassInfoLoader extends ClassInfoLoader {

	public FullClassInfoLoader(InitialClassInfoFactory cif) {
		super(cif);
	}

	@Override
	public void load(List<String> jarFiles) {
		for(String jarFile: jarFiles){

			JarFile jar;
			try {
				jar = new JarFile(jarFile);
				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					JarEntry file = entries.nextElement();

					if (!file.isDirectory() && file.getName().endsWith(".class")){
						cif.createClassInfo(new ClassParser(jar.getInputStream(file), null).parse());
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	    cif.connectTypesAndClassInfos();		
	}
}
