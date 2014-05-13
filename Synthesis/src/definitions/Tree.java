package definitions;

import java.util.HashMap;

import synthesis.trees.Expr;
import synthesis.trees.Pointer;

public class Tree implements Cloneable {
	private Pointer root;
	private HashMap<Integer, Expr> rep;
	
	public void add(int key, Expr expr){
		rep.put(key, expr);
	}
	
	@Override
	protected Tree clone() throws CloneNotSupportedException {
		Tree tree = (Tree) super.clone();
		tree.rep = (HashMap<Integer, Expr>) this.rep.clone();
		return tree;
	}
	
	public String toString(){
		return root.toString(rep);
	}
}
