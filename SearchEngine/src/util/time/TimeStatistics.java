package util.time;

import java.util.LinkedList;
import java.util.List;

public class TimeStatistics {
	
	private List<Stopwatch> stopwatches;
	private Stopwatch lastStopwatch = null;
	
	public TimeStatistics() {
		this.stopwatches = new LinkedList<Stopwatch>();
	}
	
	private void clear() {
		this.stopwatches.clear();
		this.lastStopwatch = null;
	}
	
	public void startMeasuringTime(String message){
		storeLastStopwatch();
		lastStopwatch = new Stopwatch(message);
		lastStopwatch.start();
	}

	private void storeLastStopwatch() {
		if(lastStopwatch != null){
			lastStopwatch.stop();
			this.stopwatches.add(lastStopwatch);
			lastStopwatch = null;
		}
	}
	
	public void stopMeasuringTime() {
		storeLastStopwatch();
	}

	public String toString() {
		stopMeasuringTime();
		StringBuffer sb = new StringBuffer("Time Statistics:\n");
		for (Stopwatch stopwatch: stopwatches) {
			sb.append(stopwatch +"\n");
		}
		sb.append("\n");
		clear();
		return sb.toString();
	}

}
