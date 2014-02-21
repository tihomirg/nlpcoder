package selection;

import java.util.List;

public interface IJarLoader {
	 List<ClassLoader> getClassFiles(List<String> jarFiles);
}
