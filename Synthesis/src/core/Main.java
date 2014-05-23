package core;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import statistics.CompositionStatistics;
import statistics.posttrees.ConstructorInvocation;
import statistics.posttrees.Expr;
import statistics.posttrees.InstanceFieldAccess;
import statistics.posttrees.InstanceMethodInvocation;
import synthesis.ExprGroup;
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
		
		
		List<ExprGroup> exprGroups = createExprGroups(initDecls(api));
		setExprRelatedGroups(exprGroups);
		
		
		
		PartialExpressionScorer scorer = new PartialExpressionScorer();
		GroupBuilder<SaturationSynthesisGroup> builder = new SaturationGroupBuilder(handlerTable, scorer, 10, 20);
		Synthesis<SaturationSynthesisGroup> synthesis = new Synthesis<SaturationSynthesisGroup>(exprGroups, builder);
		
		synthesis.run();
		
		System.out.println(synthesis);
		
	}
	
	private static List<Declaration> initDecls(StabileAPI api){
		List<Declaration> decls = new LinkedList<Declaration>();
		
		Imported imported = api.createImported();
		api.load(imported, "java.io", true);
		
		Set<Declaration> methods = imported.getConstructors("new File", 1);
		Declaration[] declArray = methods.toArray(new Declaration[methods.size()]);
				
		decls.add(declArray[0]);		
		
		return decls;
	}
	
	private static List<ExprGroup> createExprGroups(List<Declaration> decls) {
		List<ExprGroup> egroups = new LinkedList<ExprGroup>();
		
		for (Declaration decl : decls) {
			egroups.add(new ExprGroup(createExpr(decl)));
		}
		
		return egroups;
	}
	
	//TODO: Solve the case when there are two same 'exprs' in two different groups, they can block each other.
	//Basically we shouldn't make the check in the zeroth level.
	public static void setExprRelatedGroups(List<ExprGroup> egroups){
		for (ExprGroup eGroup : egroups) {
			Set<ExprGroup> set = new HashSet<ExprGroup>();
			set.addAll(egroups);
			set.remove(eGroup);
			eGroup.setRelatedGroups(set);
		}
	}

	private static Expr createExpr(Declaration decl) {
		if (decl.isMethod()){
			if (decl.isConstructor()){
				return new ConstructorInvocation(decl);
			} else {
				return new InstanceMethodInvocation(decl);
			}
		} else {
			return new InstanceFieldAccess(decl);			
		}
	}
}
