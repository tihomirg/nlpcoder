package selection.loaders;

import java.util.List;

import selection.WordExtractorFromName;

public interface IJarLoader {
	 ClassLoader[] getClassFiles(List<String> jarFiles, WordExtractorFromName extractor);
}
