import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellChecker;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JazzyMain {

    protected SpellDictionaryHashMap dictionary;
    protected SpellChecker spellChecker;

    public JazzyMain() {
    	try {
    		dictionary = new SpellDictionaryHashMap(new File("C:/Users/gvero/git/dictionary/eng_com.dic"));
    		spellChecker = new SpellChecker(dictionary);
    		
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    public List getSuggestions(String word, int threshold) {
        return spellChecker.getSuggestions(word, threshold);
    }
    
    public static void main(String[] args){
    	JazzyMain jazzy = new JazzyMain();
    	
    	
    	//for(int i=0;i<1000;i++)
    	System.out.println(Arrays.toString(jazzy.getSuggestions("wroote",10).toArray()));
    }
    
}