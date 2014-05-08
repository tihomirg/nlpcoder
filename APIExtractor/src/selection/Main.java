package selection;

import java.util.Scanner;

import config.Config;

import nlp.parser.IParser;
import nlp.parser.ParserPipeline;
import nlp.parser.minusone.ParserMinusOne;
import nlp.parser.minusone.SentenceMinusOne;
import nlp.parser.one.ParserOne;
import nlp.parser.three.ParserThree;
import nlp.parser.two.ConstituentTwo;
import nlp.parser.two.ParserTwo;
import nlp.parser.two.SentenceTwo;
import nlp.processors.WordProcessor;

import deserializers.Deserializer;
import deserializers.FrequencyDeserializer;


public class Main {
	private static SentenceTwo parse(String sentence, WordProcessor wordProcessor) {
		ParserPipeline parser = new ParserPipeline(new IParser[]{
				new ParserMinusOne(),
				new ParserOne(wordProcessor), 
				new ParserTwo(wordProcessor.getWordNet(), Config.getNumOfLevels(), Config.getIntervalRadius()),
				new ParserThree(new ScoreDesigner(Config.getScores()))
		});

		return (SentenceTwo) parser.parse(new SentenceMinusOne(sentence));
	}
	
	public static void main(String[] args) {
		Deserializer deserializer = new Deserializer();
		
		FrequencyDeserializer fDeserializer = new FrequencyDeserializer(Config.getDeclarationFrequencyLocation());
		
		Selection selection = new Selection(Config.topSelectedLength(), fDeserializer.getFreq());
		selection.add(deserializer.deserialize(Config.getStorageLocation()).getClasses(), Config.getMaxWords(), Config.getNullProbability());
		
		Scanner scanner = new Scanner(System.in);
		WordProcessor wordProcessor = new WordProcessor();
		
		String line = null;
		while((line = scanner.nextLine()) != null){
			if (line.equals("exit")) break;
			else {
				SentenceTwo sentence = parse(line, wordProcessor);
				//System.out.println(sentence);
				for (ConstituentTwo cons : sentence.getConstituents()) {
					TopList top = selection.tryInc(cons);
					System.out.println(top);
				}
				selection.clear();
			}
		}
	}	
}
