package synthesis;

import java.util.List;

public class Connection implements Cloneable {

	private int index;
	private Param param;
	private List<ExprGroup> relatedGroups;

	public Connection(Param param, List<ExprGroup> relatedGroups) {
		this.param = param;
		this.relatedGroups = relatedGroups;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public Param getParam() {
		return param;
	}
	
	@Override
	protected Connection clone(){
		Connection connection = null;
		try {
			connection = (Connection) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	@Override
	public String toString() {
		return param.toString();
	}
	
	public List<ExprGroup> getRelatedGroups() {
		return relatedGroups;
	}

}
