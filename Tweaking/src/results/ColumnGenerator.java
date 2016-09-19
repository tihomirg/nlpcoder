package results;

import java.util.List;

import commons.examples.Example;
import commons.values.RankedValue;
import results.table.Column;

public abstract class ColumnGenerator {

	public String name;
	public List<Example<String, RankedValue>> examples;
	private int width;
	private String adjustment;
	
	public ColumnGenerator(String name, List<Example<String, RankedValue>> examples, int width, String adjustment) {
		this.name = name;
		this.examples = examples;
		this.width = width;
		this.adjustment = adjustment;
	}

	public Column create() {
		Column column = new Column(this.name, this.width, this.adjustment);
		setSettings();
		for (Example<String, RankedValue> example : this.examples) {
			column.addCell(createCell(example));
		}
		return column;
	}
	
	public abstract void setSettings();
	public abstract String createCell(Example<String, RankedValue> example);
	
}
