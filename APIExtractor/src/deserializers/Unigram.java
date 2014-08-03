package deserializers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Unigram {

	private Map<Integer, Integer> freq;
	
	private double minimum;
	private double total;

	public Unigram(String fileName) {
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
			
			this.minimum = Math.min(minimum, freq);
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
	
	public double getScore(int declID) {
		return (1 + Math.log10(baseProbability(declID))/10);
	}

	private double baseProbability(int declID) {
		Integer integer = freq.get(declID);	
		if(integer != null){
			return integer / total;
		} else return minimum / total;
	}
	
	@Override
	public String toString() {
		return "min = "+ minimum+", total = "+total +", smooth = "+(minimum/total) +"  score = "+(1 + Math.log10(minimum/total)/10);
	}
}
