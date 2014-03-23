package selection;

import java.util.Scanner;

import oldcorpus.LoadOldCorpus;
import selection.deserializers.Deserializer;
import selection.deserializers.FrequencyDeserializer;
import selection.parser.one.ParserOne;
import selection.parser.one.SentenceZero;
import selection.parser.three.ParserThree;
import selection.parser.two.ConstituentTwo;
import selection.parser.two.ParserTwo;
import selection.parser.two.SentenceTwo;
import selection.probability.designers.ConeProbabilityDesigner;
import selection.probability.designers.MixedProbabilityDesigner;
import selection.probability.designers.UniformProbabilityDesigner;
import selection.serializers.Serializer;
import selection.types.NameGenerator;
import selection.types.TypeFactory;

public class Main {
	private static SentenceTwo parse(String sentence, WordProcessor wordProcessor) {
		ParserPipeline parser = new ParserPipeline(new IParser[]{
				new ParserOne(wordProcessor), 
				new ParserTwo(wordProcessor.getWordNet(), Config.getNumOfLevels(), Config.getIntervalRadius()),
				new ParserThree(new MixedProbabilityDesigner(0.5), Config.getIntervalRadius())
		});

		return (SentenceTwo) parser.parse(new SentenceZero(sentence));
	}
	
	public static void main(String[] args) {
		TypeFactory factory = new TypeFactory(new NameGenerator(Config.getDeserializerVariablePrefix()));
		Deserializer deserializer = new Deserializer(factory);
		
		FrequencyDeserializer fDeserializer = new FrequencyDeserializer(Config.getDeclarationFrequencyLocation());
		
		Selection selection = new Selection(Config.topSelectedLength(), fDeserializer.getFreq());
		selection.add(deserializer.deserialize(Config.getStorageLocation()), Config.getMaxWords(), Config.getNullProbability());
		
		Scanner scanner = new Scanner(System.in);
		WordProcessor wordProcessor = new WordProcessor();
		
		String line = null;
		while((line = scanner.nextLine()) != null){
			if (line.equals("exit")) break;
			else {
				SentenceTwo sentence = parse(line, wordProcessor);
				
				System.out.println(sentence);
				for (ConstituentTwo cons : sentence.getConstituents()) {
					TopList top = selection.tryInc(cons);
					System.out.println(top);
				}
				selection.clear();
			}
		}
	}	
}
