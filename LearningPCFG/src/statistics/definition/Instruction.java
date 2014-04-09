package statistics.definition;

import java.util.List;

public abstract class Instruction {

	private List<String> location;

	public List<String> getLocation() {
		return location;
	}

	public void setLocation(List<String> location) {
		this.location = location;
	}	
	
	public void addLocation(String location){
		this.location.add(location);
	}
}
