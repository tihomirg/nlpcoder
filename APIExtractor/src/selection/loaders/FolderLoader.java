package selection.loaders;

import java.io.File;
import java.util.LinkedList;
import java.util.List;


public class FolderLoader {
	public List<String> getJars(File folder) throws Exception {
		List<String> jars = new LinkedList<String>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				jars.addAll(getJars(fileEntry));
			} else {
				if (fileEntry.isFile() && fileEntry.getName().endsWith(".jar")) {
					jars.add(fileEntry.getAbsolutePath());
				}
			}
		}
		return jars;
	}
}
