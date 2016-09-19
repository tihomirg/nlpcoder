package results.table;

import java.util.LinkedList;
import java.util.List;

public class Column {

	private List<String> cells;
	private String name;
	private int width;
	private String adjustment;
	
	public Column(String name, int width, String adjustment) {
		this.name = name;
		this.adjustment = adjustment;
		this.width = width;
		this.cells = new LinkedList<String>();
	}

	public void addCell(String input) {
		this.cells.add(input);
	}

	public List<String> getCells() {
		return cells;
	}

	public String getName() {
		return name;
	}

	public String getCell(int i) {
		String cell = cells.get(i);
		return format(cell);
	}

	private String format(String cell) {
		int width = this.width - cell.length();
		return " "+cell+padding(width);
	}

	private String padding(int size) {
		String s = "";
		for (int i = 0; i < size; i++) {
			s += " ";
		}
		return s;
	}

	public String getAdjustment() {
		return this.adjustment;
	}
}
