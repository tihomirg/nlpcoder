package tests;

import java.util.Arrays;

import types.NameGenerator;
import api.StabileAPI;
import config.Config;
import definitions.Declaration;
import deserializers.Deserializer;

public class APIParserTest {
	public static void main(String[] args) {

		//Loading class and type representation
		NameGenerator nameGen = new NameGenerator(Config.getDeserializerVariablePrefix());
		Deserializer deserializer = new Deserializer();
		StabileAPI api = new StabileAPI(deserializer.deserialize(Config.getSecondStorageLocation()), nameGen);
		
		for (Declaration decl : api.getDeclsMap().values()) {
			System.out.println(decl);
		}
		
	}
}
