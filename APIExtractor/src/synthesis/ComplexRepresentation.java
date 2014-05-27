package synthesis;

import java.util.List;

import synthesis.trees.RepPointer;

public class ComplexRepresentation extends Representation {
	private SimpleRepresentation[] rep;
	
	public ComplexRepresentation(SimpleRepresentation simpleRep){
		super();
		this.rep = new SimpleRepresentation[INITIAL_CAP];
		this.rep[this.root.getId()] = simpleRep;
		simpleRep.setParent(this);
	}
	
	@Override
	protected ComplexRepresentation clone() {
		ComplexRepresentation representation = null;
		try {
			representation = (ComplexRepresentation) super.clone();
			representation.rep = this.rep.clone();
			for (int i=0; i < this.rep.length; i++) {
				representation.rep[i] = this.rep[i].clone();
				representation.rep[i].setParent(this);
			}
			
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return representation;
	}
	
	public String toString(int id){
		return rep[id].toString();
	}

	public List<Param> instantiate(Param param, statistics.posttrees.Expr expr) {
		throw new UnsupportedOperationException();
	}

	protected void ensureSize(int newLength) {
		if (rep.length < newLength){
			int length = newLength + INITIAL_CAP;
			SimpleRepresentation[] newArray = new SimpleRepresentation[length];
			System.arraycopy(rep, 0, newArray, 0, this.length);
			this.rep = newArray;
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
		this.rep[index] = rep.asSimpleRepresentation();
		
		//Set pointer
		this.rep[connection.getIndex()].setExpr(connection.getParam().getRepKey().getId(), new RepPointer(index));
		
		return index;
	}
	
	@Override
	public SimpleRepresentation asSimpleRepresentation() {
		return rep[root.getId()];
	}
	
	@Override
	public ComplexRepresentation asComplexRepresentation() {
		return this;
	}
}
