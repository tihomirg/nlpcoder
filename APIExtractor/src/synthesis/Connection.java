package synthesis;

import java.util.List;

public class Connection {

	private Param param;
	private List<ExprGroup> relatedGroups;

	public Connection(Param param, List<ExprGroup> relatedGroups) {
		this.param = param;
		this.relatedGroups = relatedGroups;
	}
	
	@Override
	public String toString() {
		return param.toString();
	}

}
