package selection.trees;

import java.util.List;

import selection.Interval;

public class Sentence {
	
	private List<Word> words;
	private int wordNum; 
	private List<Constituent> constituents;
	private int consNum;
	private List<Interval> intervals;
	private int intNum;

	public Sentence(List<Interval> intervals, List<Constituent> constituent, List<Word> words) {
		this.intervals = intervals;
		this.intNum = intervals.size();
		this.constituents = constituent;
		this.consNum = constituent.size();
		this.words = words;
		this.wordNum = words.size();
	}
}
