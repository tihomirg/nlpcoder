package statistics.definition;

import java.util.LinkedList;
import java.util.List;

import selection.types.Type;

public class Sequence {

	private Type type;
	private List<Instruction> instructions;
	private List<String> location;

	public Sequence(Type type) {
		this(type, new LinkedList<Instruction>(), new LinkedList<String>());
	}

	public Sequence(Type type, List<Instruction> instructions,
			List<String> location) {
		this.type = type;
		this.instructions = instructions;
		this.location = location;
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<Instruction> instructions) {
		this.instructions = instructions;
	}

	public List<String> getLocation() {
		return location;
	}

	public void setLocation(List<String> location) {
		this.location = location;
	}

	public Sequence() {
		this.instructions = new LinkedList<Instruction>();
	}

	public void add(Instruction inst){
		inst.setLocation(location);
		instructions.add(inst);
	}
}
