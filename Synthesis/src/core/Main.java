package core;

import statistics.CompositionStatistics;
import statistics.HandlerTable;
import types.NameGenerator;
import api.StabileAPI;
import config.Config;
import deserializers.Deserializer;

public class Main {
	public static void main(String[] args) {
		NameGenerator nameGen = new NameGenerator(Config.getDeserializerVariablePrefix());
		Deserializer deserializer = new Deserializer();
		StabileAPI api = new StabileAPI(deserializer.deserialize(Config.getStorageLocation()), nameGen);
		
		//StabileAPI api = deserializer.deserialize(Config.getStabileAPIStorageLocation());
				
		HandlerTable handlerTable = new HandlerTable();
		CompositionStatistics stat = new CompositionStatistics(api.getStf(), api.getDeclsMap(), Config.getCompositionStatisticLocation(), handlerTable);
		stat.read();
	}
}
