package selection;

import java.util.Scanner;
import selection.parser.one.ParserOne;
import selection.parser.one.SentenceZero;
import selection.parser.three.ParserThree;
import selection.parser.two.ConstituentTwo;
import selection.parser.two.ParserTwo;
import selection.parser.two.SentenceTwo;

public class Main {

	public static void main(String[] args) {
		DeclarationLoader loader = new DeclarationLoader();
		Selection selection = new Selection(Config.topSelectedLength());
		selection.add(loader.deserialize(Config.getStorageLocation()));
		
		Scanner scanner = new Scanner(System.in);
		WordProcessor wordProcessor = new WordProcessor();
		String line = null;
		while((line = scanner.nextLine()) != null){
			if (line.equals("exit")) break;
			else {
				SentenceTwo sentence = parse(line, wordProcessor);
				for (ConstituentTwo cons : sentence.getConstituents()) {
					TopList top = selection.tryInc(cons);
					System.out.println(top);
				}
				selection.clear();
			}
		}
	}

	private static SentenceTwo parse(String sentence, WordProcessor wordProcessor) {
		int intervalDiameter = 2;
		ParserPipeline parser = new ParserPipeline(new IParser[]{
				new ParserOne(wordProcessor), 
				new ParserTwo(wordProcessor.getWordNet(), 3, intervalDiameter),
				new ParserThree(intervalDiameter)
		});

		return (SentenceTwo) parser.parse(new SentenceZero(sentence));
	}
}
