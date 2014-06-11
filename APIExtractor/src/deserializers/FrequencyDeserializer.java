package deserializers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class FrequencyDeserializer {

	private Map<Integer, Integer> freq;
	
	private double minimum;
	private double total;

	public FrequencyDeserializer(String fileName) {
		this.minimum = Integer.MAX_VALUE;
		this.total = 0;
		this.freq = loadMap(fileName);
	}

	private Map<Integer, Integer> loadMap(String fileName){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		BufferedReader reader = null;
		
		try{
		
		  reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
		
		  String line = null;
		  while ((line = reader.readLine()) != null) {
		    String[] splits = line.split(" : ");
		    int declID = Integer.parseInt(splits[1]);
			int freq = Integer.parseInt(splits[0]);
			map.put(declID, freq);
			
			trySetMinimum(freq);
			total += freq;
		  }
		
		} catch(IOException e){
		} finally{
			
		  if (reader != null)
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
		
		return map;
	}

	private void trySetMinimum(int freq) {
		this.minimum = Math.min(minimum, freq);
	}

	public Map<Integer, Integer> getFreq() {
		return freq;
	}
	
	public double getMinimumLog() {
		return Math.log(minimum);
	}

	public double getSmoothLog(){
		return Math.log(minimum) - Math.log(total);
	}
	
	public double getTotalLog(){
		return Math.log(total);
	}
	
	public double getFrequency(int id) {
		Integer integer = freq.get(id);
		
		if(integer != null){
			return Math.log(integer) - Math.log(total);
		} else return Math.log(minimum) - Math.log(total);
	}		
	
	@Override
	public String toString() {
		return "min = "+this.getMinimumLog() +", total = "+this.getTotalLog() +", smooth = "+getSmoothLog();
	}	
}
