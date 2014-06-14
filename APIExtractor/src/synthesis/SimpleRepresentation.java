package synthesis;

import java.util.List;

import synthesis.trees.Expr;
import synthesis.trees.RepPointer;

public class SimpleRepresentation extends Representation {

	private Representation parent;

	private Expr[] nodes;

	public SimpleRepresentation(){
		super();
		this.nodes = new Expr[INITIAL_CAP];
		this.nodes[this.root.getId()] = Expr.REP_HOLE;
	}

	@Override
	protected SimpleRepresentation clone() {
		SimpleRepresentation representation = null;
		try {
			representation = (SimpleRepresentation) super.clone();
			representation.nodes = this.nodes.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return representation;
	}

	public String toString(int id){
		return nodes[id].toString(this);
	}

	public List<Param> instantiate(Param param, statistics.posttrees.Expr expr) {
		RepKey repKey = param.getRepKey();
		List<statistics.posttrees.Expr> args = expr.getArgs();
		
		List<Integer> ids = allocate(args.size());

		nodes[repKey.getId()] = expr.createRep(ids);

		setRepHoles(ids);

		return createParams(args, expr.getArgTypes(), ids);
	}

	protected void setRepHoles(List<Integer> ids) {
		for (int id : ids) {
			nodes[id] = Expr.REP_HOLE;
		}
	}

	protected void ensureSize(int newLength) {
		if (nodes.length < newLength){
			int length = newLength + INITIAL_CAP;
			Expr[] newArray = new Expr[length];
			System.arraycopy(nodes, 0, newArray, 0, this.length);
			this.nodes = newArray;
		}
	}

	@Override
	public Representation getParent() {
		return this.parent;
	}

	@Override
	public void setParent(Representation parent) {
		this.parent = parent;
	}

	@Override
	public int connect(Connection connection, Representation rep) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SimpleRepresentation asSimpleRepresentation() {
		return this;
	}

	public void setExpr(int id, RepPointer repPointer) {
		nodes[id] = repPointer;
	}

	@Override
	public ComplexRepresentation asComplexRepresentation() {
		return new ComplexRepresentation(this);
	}
}
