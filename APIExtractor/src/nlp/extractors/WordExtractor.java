package nlp.extractors;

import java.util.Collection;

import nlp.parser.one.Word;

import selection.scorers.Scorer;

import definitions.ClassInfo;
import definitions.Declaration;

public abstract class WordExtractor {

	protected abstract Word[] getWords(Declaration decl);
	
	public void addWords(Collection<ClassInfo> classes) {
		for (ClassInfo classInfo : classes) {
			Declaration[] uDecls = classInfo.getUniqueDeclarations();
			for (Declaration declaration : uDecls) {
				declaration.setWords(getWords(declaration));
			}
		}
	}	

}
