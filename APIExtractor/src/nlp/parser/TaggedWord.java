package nlp.parser;

public class TaggedWord {
	private String lemma;
	private String pos;

	public TaggedWord(String lemma, String pos) {
		this.lemma = lemma;
		this.pos = pos;
	}
	
	public String getLemma() {
		return lemma;
	}
	
	public String getPos() {
		return pos;
	}

	@Override
	public String toString() {
		return "("+ lemma + ", " + pos + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		TaggedWord that = (TaggedWord) obj;
		return this.lemma.equals(that.lemma) && this.pos.equals(that.pos);
	}
}
