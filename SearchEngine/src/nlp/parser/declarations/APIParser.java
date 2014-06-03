package nlp.parser.declarations;

import java.util.Properties;

import nlp.parser.ComplexWordDecomposer;
import serializers.KryoSerializer;
import types.NameGenerator;
import config.Config;
import definitions.ClassInfo;
import definitions.Declaration;
import deserializers.Deserializer;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import api.InitialAPI;
import api.StabileAPI;

public class APIParser {
	private IDeclarationParser parser;
	
	public APIParser(IDeclarationParser parser) {
		this.parser = parser;
	}

	public InitialAPI parse(InitialAPI api){
		for (ClassInfo clazz : api.getClasses()) {
			for (Declaration decl : clazz.getDeclarations()) {
				parser.parse(decl);
				decl.propagateTokensToUniqueDecl();
			}
		}
		
		return api;
	}
	
	public static void main(String[] args) {
		Deserializer deserializer = new Deserializer();
		InitialAPI api = deserializer.deserialize(Config.getStorageLocation());
		
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP coreNLP = new StanfordCoreNLP(props);
		APIParser parser = new APIParser(new DeclarationParser(new ComplexWordDecomposer(coreNLP)));
		
		KryoSerializer serializer = new KryoSerializer();
		InitialAPI parse = parser.parse(api);
		
		System.out.println(parse.getClasses());
		
		serializer.writeObject(Config.getSecondStorageLocation(), parse);
	}
}
