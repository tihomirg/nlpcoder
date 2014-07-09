package nlp.parser;

public class TaggedWord {
	private String lemma;
	private String pos;

	public TaggedWord() {}
	
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lemma == null) ? 0 : lemma.hashCode());
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		TaggedWord other = (TaggedWord) obj;
		return this.lemma.equals(other.lemma) && this.pos.equals(other.pos);
	}
}
