package core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import statistics.CompositionStatistics;
import statistics.posttrees.ConstructorInvocation;
import statistics.posttrees.Expr;
import statistics.posttrees.InstanceMethodInvocation;
import synthesis.Param;
import synthesis.PartialExpression;
import synthesis.PartialExpressionScorer;
import synthesis.RepKey;
import synthesis.handlers.SearchKey;
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
		
		PartialExpressionScorer scorer = new PartialExpressionScorer();
		GroupBuilder<SaturationSynthesisGroup> builder = new SaturationGroupBuilder(handlerTable, scorer, 10, 20);
		Synthesis<SaturationSynthesisGroup> synthesis = new Synthesis<SaturationSynthesisGroup>(makePexprs(api), builder);
		
		synthesis.run();
		
		System.out.println(synthesis);
		
	}

	private static List<List<PartialExpression>> makePexprs(StabileAPI api) {
		List<PartialExpression> pexprs = new LinkedList<PartialExpression>();
		
		Imported imported = api.createImported();
		api.load(imported, "java.io", true);
		
		Set<Declaration> methods = imported.getConstructors("new File", 1);
		Declaration[] decls = methods.toArray(new Declaration[methods.size()]);
				
		Expr expr = new ConstructorInvocation(decls[0]);

		System.out.println(expr.getDecl());
		
		SearchKey searchKey = new SearchKey(expr);
		RepKey repKey = initialRepKey();
		Param param = new Param(searchKey, repKey);
		
		pexprs.add(new PartialExpression(param));
		List<List<PartialExpression>> pexprss = new LinkedList<List<PartialExpression>>();
		pexprss.add(pexprs);
		return pexprss;
	}

	private static RepKey initialRepKey() {
		return new RepKey();
	}
}
