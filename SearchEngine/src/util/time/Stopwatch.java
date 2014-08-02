package util.time;

public class Stopwatch {
	private String message;
	private long startTime;
	private long stopTime;

	public Stopwatch(String message) {
		this.message = message;
	}
	
	public void start(){
		this.startTime = System.currentTimeMillis();
	}
	
	public void stop(){
		this.stopTime = System.currentTimeMillis();
	}
	
	public long duration() {
		return this.stopTime - this.startTime;
	}

	@Override
	public String toString() {
		return message+" : "+duration()+"ms";
	}
}
