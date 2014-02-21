package selection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellChecker;

public class WordCorrector {

	private static final String DICTIONARY = "C:/Users/gvero/git/dictionary/eng_com.dic";
	private static final int THRESHOLD = 10;
	
	private SpellChecker spellChecker;

	public WordCorrector() {
		try {
			this.spellChecker = new SpellChecker(new SpellDictionaryHashMap(new File(DICTIONARY)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String correct(String word) {
		String newWord = word;
		while(true){
			List suggestions = spellChecker.getSuggestions(newWord, THRESHOLD);

			if (suggestions.size() > 0) return suggestions.get(0).toString();
			else if (newWord.length() > 1){
				newWord = newWord.substring(0, newWord.length()-1);
			} else return null;
		}
    }
}
