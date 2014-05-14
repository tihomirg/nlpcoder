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
	protected Tree clone() {
		Tree tree = null;
		try {
			tree = (Tree) super.clone();
			tree.rep = (HashMap<Integer, Expr>) this.rep.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tree;
	}
	
	public String toString(){
		return root.toString(rep);
	}
}
