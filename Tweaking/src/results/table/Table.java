package results.table;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

public class Table {

	private List<Column> columns;
	private int size;
	
	public Table(int size) {
		this.size = size;
		this.columns = new LinkedList<Column>();
	}

	public void addColumn(Column column) {
		this.columns.add(column);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(beginTable()+"\n");
		sb.append(getHeader()+"\n");
		for (int i = 0; i < size; i++) {
			sb.append(getRow(i)+"\n");
		}
		sb.append(endTable()+"\n");
		return sb.toString();
	}

	private String endTable() {
		return "\\end{tabular}\n\\end{figure*}";
	}

	private String beginTable() {
		String s = "\\begin{figure*}\n\\scriptsize\n\\begin{tabular}{";
		int size = this.columns.size();
		for (int i = 0; i < size; i++) {
			s+=this.columns.get(i).getAdjustment()+(i<size-1 ? " | ":"");
		}
		return s+"}";
	}

	private String getRow(int index) {
		StringBuffer sb = new StringBuffer();
		int size = columns.size();
		for(int i = 0; i < size; i++){
			Column column = this.columns.get(i);
			sb.append(column.getCell(index)+(i < size-1 ? " & ": "\\\\"));
		}
		return sb.toString();
	}

	private String getHeader() {
		StringBuffer sb = new StringBuffer();
		int size = columns.size();
		for(int i = 0; i < size; i++){
			Column column = this.columns.get(i);
			sb.append(column.getName()+(i < size-1 ? " & ": "\\\\ \\hline"));
		}
		return sb.toString();
	}
	
	public void print(String location) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(location, "UTF-8");
			writer.println(this);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (writer != null) writer.close();		
	}
}
