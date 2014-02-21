package selection;

import java.util.List;

import definitions.Declaration;

public interface IWordExtractor {

	List<WordIndex> get(Declaration decl);

	int getGroupNum();

}
