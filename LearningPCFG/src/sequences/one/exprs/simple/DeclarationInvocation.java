package sequences.one.exprs.simple;

import java.util.List;

import definitions.Declaration;

import sequences.one.exprs.Expr;
import util.Pair;

public class DeclarationInvocation extends Expr {

	private Declaration decl;
	
	public DeclarationInvocation(Declaration decl) {
		super(decl.getRetType());
	}

	@Override
	public String shortRep() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void representations(List<Pair<String, String>> list) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String representation() {
		// TODO Auto-generated method stub
		return null;
	}

}
