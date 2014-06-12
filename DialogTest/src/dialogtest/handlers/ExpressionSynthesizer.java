package dialogtest.handlers;

public class ExpressionSynthesizer {

	public ExpressionSynthesizer() {
		
	}
	
	public String[] run() {
		return new String[]{
				"new FileInputStream(new File(\"text.txt\"))", 
				"new BufferedInputStream(new File(\"text.txt\"))", 
				"new FileInputStream(new BufferedInputStream(new File(\"text.txt\")))", 
				"new DataInputStream(new File(\"text.txt\"))", 
				"new ArrayInputStream(new File(\"text.txt\"))"};
	}

}
