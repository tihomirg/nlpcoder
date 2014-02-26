package selection.shares;

import java.util.HashMap;
import java.util.Map;

public class ShareFactory {

	private static Map<Double, UnitShare> unitShares = new HashMap<Double, UnitShare>();
	
	public static UnitShare getUnitShare(double prob) {
		return tryGetUnitShare(prob);
	}
	
	private static UnitShare tryGetUnitShare(double prob){
		if (unitShares.containsKey(prob)){
			return unitShares.get(prob);
		} else {
			UnitShare share = new UnitShare(prob);
			unitShares.put(prob, share);
			return share;
		}
	}

}
