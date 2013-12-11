import static java.lang.System.out;

import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import rules.PackedRule;

public class Main {

	//Change to Map<Rule -> Integer> 
	private static Map<String, Map<String, Integer>> map = new HashMap<String, Map<String, Integer>>();
	
	private static final String NONE = "<None>";
	private static final String QUESTION_OPERATOR = "{?}"; 
	
	public static void main(String args[]){
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		
		char[] fileContent = readFile("CityImpl.java");
		
		parser.setSource(fileContent);
		Map options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_7, options);
		parser.setCompilerOptions(options);
		
		
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setStatementsRecovery(true);
 
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		cu.accept(new ASTVisitor() {
 
			//This code extracts a relation between the nodes and comments.
//			public boolean preVisit2(ASTNode node){
//				
//				int commentFIndex = cu.firstLeadingCommentIndex(node);
//				int commentLIndex = cu.lastTrailingCommentIndex(node);
//				
//				if (commentFIndex > -1){
//				  System.out.println("Comments ["+commentFIndex+","+commentLIndex+"] associated with "+ node.getClass().getSimpleName());
//				  System.out.println(node);
//				}
//				
//				return true;
//			}
			
			
			public boolean visit(InfixExpression node){
				List<String> children = new ArrayList<String>();
				makeNonTerminal(children, node.getLeftOperand());
				makeTerminal(children, node.getOperator());
				makeNonTerminal(children, node.getRightOperand());
				
				if(node.hasExtendedOperands()){
					write(children, makeRuleWithNonTerminals(node.extendedOperands()));
				}
				
				addToMap(node, children);			
				return true;
			}
			
			public boolean visit(IfStatement node){
				
				
				return true;
			}
			
			public boolean visit(NumberLiteral node){
				List<String> children = new ArrayList<String>();
				write(children, node.toString());
				
				addToMap(node, children);
				return true;
			}
			
			public boolean visit(FieldAccess node){
				List<String> children = new ArrayList<String>();
				makeNonTerminal(children, node.getExpression());
				makeNonTerminal(children, node.getName());
				
				addToMap(node, children);
				return true;
			}

			public boolean visit(ForStatement node){
				List<String> children = new ArrayList<String>();
				write(children, makeRuleWithNonTerminals(node.initializers()));
				makeNonTerminal(children, node.getExpression());
				write(children, makeRuleWithNonTerminals(node.updaters()));
				makeNonTerminal(children, node.getBody());
				
				addToMap(node, children);
				return true;
			}
			
			
			public boolean visit(VariableDeclarationExpression node){
				List<String> children = new ArrayList<String>();
				write(children, makeRuleWithNonTerminals(node.modifiers()));
				makeNonTerminal(children, node.getType());
				write(children, makeRuleWithNonTerminals(node.fragments()));
				
				addToMap(node, children);
				return true;
			}
			
			public boolean visit(ThisExpression node){
				List<String> children = new ArrayList<String>();
				
				write(children, node.toString());
				
				addToMap(node, children);
				return true;				
			}
			
			public boolean visit(NullLiteral node){
				List<String> children = new ArrayList<String>();
				write(children, node.toString());
				addToMap(node, children);
				return true;
			}
			
			public boolean visit(SimpleName node){				
				List<String> children = new ArrayList<String>();
				write(children, node.getFullyQualifiedName());
				
				addToMap(node, children);
				return true;
			}
			
			
			public boolean visit(ConditionalExpression node){
				List<String> children = new ArrayList<String>();
				makeNonTerminal(children, node.getExpression());
				
				
				
				write(children, "?");
				
				makeNonTerminal(children, node.getThenExpression());
				makeNonTerminal(children, node.getElseExpression());
				
				addToMap(node, children);				
				return true;
			}
			
			private String makeRuleWithNonTerminals(List<ASTNode> children2) {
			  List<String> children3 = new ArrayList<String>();
				
			  String nodeName = makeList(children2);
				
			  for(ASTNode child: children2){
				makeNonTerminal(children3, child);
			  }
			  addToMap(nodeName, children3);
				
			  return nodeName; 
			}

			private String makeList(List<ASTNode> children2) {
				return "List"+children2.get(0).getClass().getSimpleName();
			}
			
			private void makeNonTerminal(List<String> children, ASTNode child) {
				write(children, child.getClass().getSimpleName());
			}
			
			private void makeTerminal(List<String> children, InfixExpression.Operator child) {
				write(children,"{"+child+"}");
			}			
			
			private void write(List<String> children, String child) {
				if(child != null) children.add(child);
				else children.add(NONE);
			}
 
		});
		
		
		List<PackedRule> rules = getRulesFromMap();
		
		for(PackedRule rule: rules){
		  out.println(rule);
		}
	}

	private static void addToMap(String leftName, List<String> children){
		StringBuffer sb = new StringBuffer();
		for(String right:children){
		  sb.append(right+" ");
		}
		
		String rightName = sb.toString();
		
		if (!map.containsKey(leftName)){
		  map.put(leftName, new HashMap<String, Integer>());
		}
		
		Map<String, Integer> frequencyMap = map.get(leftName);
		
		if(!frequencyMap.containsKey(rightName)){
			frequencyMap.put(rightName, 0);
		}
		
		int frequency = frequencyMap.get(rightName);
		frequencyMap.put(rightName, frequency+1);
	}	
	

	
	private static void addToMap(ASTNode node, List<String> children){
		addToMap(node.getClass().getSimpleName(), children);
	}
	
	private static List<PackedRule> getRulesFromMap(){
	  List<PackedRule> rules = new ArrayList<PackedRule>();
	  for(Map.Entry<String, Map<String, Integer>> entry: map.entrySet()){
		rules.addAll(getRulesFromEntry(entry));
	  }
	  return rules;
	}
	
	private static List<PackedRule> getRulesFromEntry(Map.Entry<String, Map<String, Integer>> entry){
	  List<PackedRule> rules = new ArrayList<PackedRule>();
	  String left = entry.getKey();
	  Map<String, Integer> rightList = entry.getValue();
	  
	  int totalFrequency = 0;
	  for(int frequency : rightList.values()){
		  totalFrequency += frequency;
	  }
	  
	  for(Map.Entry<String, Integer> entry2: rightList.entrySet()){
		  rules.add(new PackedRule(left, entry2.getKey(), entry2.getValue(), totalFrequency));
	  }
	  
	  return rules;
	}
	
	private static char[] readFile(String fileName){
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = null;
		
		try{
		
		  reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
		
		  String line = null;
		  while ((line = reader.readLine()) != null) {
		    sb.append(line+"\n");
		  }
		
		} catch(IOException e){
		} finally{
			
		  if (reader != null)
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
		
		return sb.toString().toCharArray();
	}
}