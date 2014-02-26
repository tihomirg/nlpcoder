package selection.parser.one;

import java.util.ArrayList;
import java.util.List;

import selection.IParser;
import selection.ISentence;
import selection.WordFactory;
import selection.WordProcessor;
import selection.parser.one.trees.SentenceOne;
import edu.mit.jwi.item.POS;

public class ParserOne extends IParser {

	private WordProcessor processor;

	public ParserOne(WordProcessor processor) {
		this.processor = processor;
	}
	
	public List<List<Word>> slice(String sentence) {
		List<List<Word>> wordss = new ArrayList<List<Word>>();
		
		List<String> splits = tag(sentence);
		for(int i = 0; i < splits.size(); i++) {
			wordss.add(getWords(TaggedLemma.create(splits.get(i)), i));
		}

		return wordss;
	}

	private List<Word> getWords(TaggedLemma constituent, int consIndex) {
		WordFactory factory = processor.getFactory();
		List<String> splits = tag(processor.decompose(constituent.getWord()));
		
		List<Word> words = new ArrayList<Word>();
		for(int i = 0; i < splits.size(); i++){
			TaggedLemma lemma = TaggedLemma.create(splits.get(i));
			
			POS pos = lemma.getTag();
			String word = lemma.getWord();
			
			String name = processor.steam(word, pos);
			if (name != null) words.add(factory.createWordOne(name, pos, consIndex));
		}
		
		return words;
	}

	private List<String> tag(List<String> decomposedSentence) {
	  String s="";
	  for(String dec: decomposedSentence){
		  s+=dec+" ";
	  }
	  return tag(s);
    }

	private List<String> tag(String sentence) {
		String[] splits = tagSentence(sentence);
		List<String> list = new ArrayList<String>();
		
		for(String split: splits){
			System.out.println(split);
			
			if(!split.isEmpty()
				&& !split.equals("")
				&& Character.isJavaIdentifierPart(split.charAt(0))){
				list.add(split);
			}
		}
		
		return list;
	}

	public String[] tagSentence(String sentence){
		return processor.getTagger().tagString(sentence).split(" ");
	}

	public ISentence parse(SentenceZero curr) { 
		List<List<Word>> wordss = this.slice(curr.getRep());
		Word[] words = getWords(wordss);
		ConstituentOne[] cons = getConstituents(wordss);
		
		return new SentenceOne(cons, words);
	}

	private ConstituentOne[] getConstituents(List<List<Word>> wordss) {
		WordFactory factory = processor.getFactory();
		int length = wordss.size();
		ConstituentOne[] cons = new ConstituentOne[length];
		
		for(int i = 0; i< length; i++){
		  List<Word> list = wordss.get(i);
		  cons[i] = factory.getWords(list.toArray(new Word[list.size()]), i);
		}
		
		return cons;
	}

	private Word[] getWords(List<List<Word>> wordss) {
		List<Word> interm = new ArrayList<Word>();
		for(List<Word> words: wordss){
			interm.addAll(words);
		}
		return interm.toArray(new Word[interm.size()]);
	}
	
}
