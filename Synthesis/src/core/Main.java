package core;

import java.util.Arrays;
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
		//Loading class and type representation
		NameGenerator nameGen = new NameGenerator(Config.getDeserializerVariablePrefix());
		Deserializer deserializer = new Deserializer();
		StabileAPI api = new StabileAPI(deserializer.deserialize(Config.getStorageLocation()), nameGen);
						
		//Loading statistics
		HandlerTable handlerTable = new HandlerTable();		
		CompositionStatistics stat = new CompositionStatistics(api.getStf(), api.getDeclsMap(), Config.getCompositionStatisticLocation(), handlerTable);
		stat.read();
		
		
		//Loading test declarations
		List<List<ExprGroup>> exprGroupss = createExprGroupss(initDecls(api));
		setExprRelatedGroups(exprGroupss);
		
		
		PartialExpressionScorer scorer = new PartialExpressionScorer();
		GroupBuilder<SaturationSynthesisGroup> builder = new SaturationGroupBuilder(handlerTable, scorer, 10, 20);
		Synthesis<SaturationSynthesisGroup> synthesis = new Synthesis<SaturationSynthesisGroup>(exprGroupss, builder);
		
		synthesis.run();
		
		System.out.println(synthesis);
		
	}
	
	private static List<List<Declaration>> initDecls(StabileAPI api){
		List<List<Declaration>> declss = new LinkedList<List<Declaration>>();
		
		Imported imported = api.createImported();
		api.load(imported, "java.io", true);

		declss.add(initNewFileConst(imported));
		declss.add(initNewFileInputStream(imported));
		declss.add(initNewDataInputStream(imported));		
		
		return declss;
	}

	private static List<Declaration> initNewFileConst(Imported imported) {
		List<Declaration> decls = new LinkedList<Declaration>();	
		Set<Declaration> methods = imported.getConstructors("new File", 1);
		Declaration[] declArray = methods.toArray(new Declaration[methods.size()]);
		
		return Arrays.asList(declArray);
	}
	
	private static List<Declaration> initNewFileInputStream(Imported imported) {
		List<Declaration> decls = new LinkedList<Declaration>();	
		Set<Declaration> methods = imported.getConstructors("new FileInputStream", 1);
		Declaration[] declArray = methods.toArray(new Declaration[methods.size()]);
		
		return Arrays.asList(declArray);		
	}	
	
	private static List<Declaration> initNewDataInputStream(Imported imported) {
		List<Declaration> decls = new LinkedList<Declaration>();	
		Set<Declaration> methods = imported.getConstructors("new DataInputStream", 1);
		Declaration[] declArray = methods.toArray(new Declaration[methods.size()]);
		
		return Arrays.asList(declArray);		
	}		
	
	private static List<List<ExprGroup>> createExprGroupss(List<List<Declaration>> declss) {
		List<List<ExprGroup>> egroupss = new LinkedList<List<ExprGroup>>();
		
		for (List<Declaration> decls : declss) {
			List<ExprGroup> egroups = createExprGroups(decls);
			egroupss.add(egroups);
		}
		
		return egroupss;
	}

	private static List<ExprGroup> createExprGroups(List<Declaration> decls) {
		List<ExprGroup> egroups = new LinkedList<ExprGroup>();
		for (Declaration decl : decls) {
			egroups.add(new ExprGroup(createExpr(decl)));				
		}
		return egroups;
	}
	
	//TODO: Solve the case when there are two same 'exprs' in two different groups, they can block each other.
	//Basically we shouldn't make the check in the zeroth level. For now we do not do it.
	public static void setExprRelatedGroups(List<List<ExprGroup>> exprGroupss){
		
		for (List<ExprGroup> eGroups : exprGroupss) {			
			List<List<ExprGroup>> relatedGroupss = new LinkedList<List<ExprGroup>>();
			relatedGroupss.addAll(exprGroupss);
			relatedGroupss.remove(eGroups);
			
			for (ExprGroup eGroup : eGroups) {
				eGroup.setRelatedGroups(flatten(relatedGroupss));
			}
		}
	}

	private static <T> List<T> flatten(List<List<T>> lists) {
		List<T> flist = new LinkedList<T>();
		
		for (List<T> list : lists) {
			flist.addAll(list);
		}
		
		return flist;
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
