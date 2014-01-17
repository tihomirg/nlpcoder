
public class ScannerException extends Exception {

	private String msg;
	
	public ScannerException(String msg){
		this.msg = msg;
	}

	@Override
	public String toString() {
		return msg;
	}
}
