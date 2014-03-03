package selection;

import java.util.List;

import selection.trees.Constituent;
import selection.trees.Word;

import definitions.Declaration;

public interface IWordExtractor {

	Indexes get(Declaration decl);

}
