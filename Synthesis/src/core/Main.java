package core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import statistics.CompositionStatistics;
import statistics.HandlerTable;
import statistics.handlers.SearchKey;
import statistics.posttrees.ConstructorInvocation;
import statistics.posttrees.Expr;
import statistics.posttrees.InstanceMethodInvocation;
import synthesis.Param;
import synthesis.PartialExpression;
import synthesis.RepKey;
import types.NameGenerator;
import api.Imported;
import api.StabileAPI;
import config.Config;
import definitions.Declaration;
import deserializers.Deserializer;

public class Main {


	public static void main(String[] args) {
		NameGenerator nameGen = new NameGenerator(Config.getDeserializerVariablePrefix());
		Deserializer deserializer = new Deserializer();
		StabileAPI api = new StabileAPI(deserializer.deserialize(Config.getStorageLocation()), nameGen);
						
		HandlerTable handlerTable = new HandlerTable();		
		CompositionStatistics stat = new CompositionStatistics(api.getStf(), api.getDeclsMap(), Config.getCompositionStatisticLocation(), handlerTable);
		stat.read();
		
		Synthesis synthesis = new Synthesis(makePexprs(api), handlerTable, 10, 10);
		
		synthesis.run();
		
	}

	private static List<PartialExpression> makePexprs(StabileAPI api) {
		List<PartialExpression> pexps = new LinkedList<PartialExpression>();
		
		Imported imported = api.createImported();
		api.load(imported, "java.io", true);
		
		Set<Declaration> methods = imported.getConstructors("new File", 1);
		Declaration[] decls = methods.toArray(new Declaration[methods.size()]);
				
		Expr expr = new ConstructorInvocation(decls[0]);

		System.out.println(expr.getDecl());
		
		SearchKey searchKey = new SearchKey(expr);
		RepKey repKey = initialRepKey();
		Param param = new Param(searchKey, repKey);
		
		pexps.add(new PartialExpression(param, 1.0));
		
		return pexps;
	}

	private static RepKey initialRepKey() {
		return new RepKey();
	}
}
