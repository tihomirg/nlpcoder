package tests;

import edu.mit.jwi.item.POS;

public class WordPOS {

	private String word;
	private POS pos;

	public WordPOS(String word, POS pos){
		this.word = word;
		this.pos = pos;
	}
	
	public String getWord() {
		return word;
	}

	public POS getPos() {
		return pos;
	}
	
	@Override
	public int hashCode() {
		final int prime = 112909;
		int result = 1;
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WordPOS other = (WordPOS) obj;
		if (pos != other.pos)
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}		
	
}