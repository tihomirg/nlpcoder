package core;

import java.util.Map;

import api.StabileAPI;
import config.Config;
import definitions.Declaration;
import statistics.CompositionStatistics;
import synthesis.deserializers.Deserializer;

public class Main {
	public static void main(String[] args) {
		Deserializer deserializer = new Deserializer();
		StabileAPI api = deserializer.deserialize(Config.getSynthesisStorageLocation());
				
		CompositionStatistics stat = new CompositionStatistics(api.getStf(), api.getDeclsMap(), Config.getCompositionStatisticLocation());
		stat.read();
	}
}
