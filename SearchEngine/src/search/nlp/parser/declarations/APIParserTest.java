package search.nlp.parser.declarations;

import java.util.List;

import types.NameGenerator;
import api.StabileAPI;
import config.Config;
import definitions.Declaration;
import deserializers.Deserializer;

public class APIParserTest {
	public static void main(String[] args) {
		NameGenerator nameGen = new NameGenerator(Config.getDeserializerVariablePrefix());
		Deserializer deserializer = new Deserializer();
		StabileAPI api = new StabileAPI(deserializer.deserialize(Config.getSecondStorageLocation()), nameGen);
		
		List<Declaration> decls = api.getUniqueDecls();
		
		for (Declaration declaration : decls) {
			if (declaration.getSimpleName().startsWith("print"))
			System.out.println(declaration);
		}
	}
}
