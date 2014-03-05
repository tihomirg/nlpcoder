package selection.loaders;

import java.util.List;

import selection.IWordExtractor;
import selection.WordExtractorFromName;

public interface IJarLoader {
	 ClassLoader[] getClassFiles(List<String> jarFiles, IWordExtractor extractor);
}
