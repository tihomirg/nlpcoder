package synthesis;

import java.util.List;

import synthesis.trees.RepPointer;

public class ComplexRepresentation extends Representation {
	private SimpleRepresentation[] nodes;
	
	public ComplexRepresentation(SimpleRepresentation simpleRep){
		super();
		this.nodes = new SimpleRepresentation[INITIAL_CAP];
		this.nodes[this.root.getId()] = simpleRep;
		simpleRep.setParent(this);
	}
	
	@Override
	protected ComplexRepresentation clone() {
		ComplexRepresentation representation = null;
		try {
			representation = (ComplexRepresentation) super.clone();
			representation.nodes = this.nodes.clone();
			for (int i=0; i < this.length; i++) {
				representation.nodes[i] = this.nodes[i].clone();
				representation.nodes[i].setParent(representation);
			}
			
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return representation;
	}
	
	public String toString(int id){
		return nodes[id].toString();
	}

	public List<Param> instantiate(Param param, statistics.posttrees.Expr expr) {
		throw new UnsupportedOperationException();
	}

	protected void ensureSize(int newLength) {
		if (nodes.length < newLength){
			int length = newLength + INITIAL_CAP;
			SimpleRepresentation[] newArray = new SimpleRepresentation[length];
			System.arraycopy(nodes, 0, newArray, 0, this.length);
			this.nodes = newArray;
		}
	}

	@Override
	protected void setRepHoles(List<Integer> ids) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Representation getParent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setParent(Representation parent) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public int connect(Connection connection, Representation rep) {		
		int index = allocate(1).get(0);
		SimpleRepresentation simpleRepresentation = rep.asSimpleRepresentation();
		this.nodes[index] = simpleRepresentation.clone();
		simpleRepresentation.setParent(this);
		
		//Set pointer
		this.nodes[connection.getIndex()].setExpr(connection.getParam().getRepKey().getId(), new RepPointer(index));
		
		return index;
	}
	
	@Override
	public SimpleRepresentation asSimpleRepresentation() {
		return nodes[root.getId()];
	}
	
	@Override
	public ComplexRepresentation asComplexRepresentation() {
		return this;
	}
}
