package builders;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;

import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import statistics.DeclarationStatistics;

public class ImprovedPackageBuilder extends IBuilder {

	private Map<String, DeclarationStatistics> map = new HashMap<String, DeclarationStatistics>();
	
	private Map<String, Integer> stat = new HashMap<String, Integer>();
	
	private List<String> pkgs = new LinkedList<String>();
	
	public void incDecl(String pkg, String decl){
		if(!map.containsKey(pkg)){
			map.put(pkg, new DeclarationStatistics());
		}
		
		DeclarationStatistics val = map.get(pkg);
		val.inc(decl);
	}
	
	public void incDecl(String decl){
		for(String pkg: pkgs){
			incDecl(pkg, decl);
		}
	}	
	
	public void inc(String name){
		if(!stat.containsKey(name)){
			stat.put(name, 0);
		}
		
		int val = stat.get(name);
		stat.put(name, val+1);
	}	
	
	public boolean visit(ImportDeclaration node) {
		pkgs.add(node.getName().toString()+(node.isOnDemand()?".*":""));
		this.inc(node.getName().toString()+(node.isOnDemand()?".*":""));
		return true;
	}

	public boolean visit(ClassInstanceCreation node) {
		incDecl("("+node.getType()+","+node.arguments().size()+")");
		return true;
	}
	
	public boolean visit(FieldAccess node){
		incDecl("("+node.getName().getIdentifier()+")");
		return true;
	}
	
	public boolean visit(MethodInvocation node) {
		incDecl("("+node.getName().getIdentifier()+","+node.arguments().size()+")");
		
		return true;
	}
	
	public boolean visit(CompilationUnit node) {
		pkgs.clear();
		return true;
	}

	public static class EntryComparator implements Comparator<Entry<String, Integer>>
	{
	    @Override
	    public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2)
	    {
	    	int x = e1.getValue();
	    	int y = e2.getValue();
	    	
	        if (x < y)
	        {
	            return 1;
	        }
	        if (x > y)
	        {
	            return -1;
	        }
	        return 0;
	    }
	}	
	
	public void print(PrintStream out){
		PriorityQueue<Entry<String, Integer>> pq = new PriorityQueue<Map.Entry<String,Integer>>(100, new EntryComparator());
		for(Entry<String, Integer> entry:stat.entrySet()){
			pq.add(entry);
		}
		
		int length = pq.size();
		for(int i=0; i <length; i++){
			Entry<String, Integer> entry = pq.remove();
			String name = entry.getKey();
			int frequency = entry.getValue();
			DeclarationStatistics decls = map.get(name);
			
			out.println(frequency+"  "+name+"  "+(decls != null ? decls : ""));
		}
	}

	@Override
	public void releaseUnder(int percent) {
		// TODO Auto-generated method stub
		
	}

}
