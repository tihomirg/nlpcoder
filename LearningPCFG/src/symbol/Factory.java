package symbol;

public class Factory {
    public static Symbol HOLE = new Hole();
    
    public Symbol createHole(){
    	return HOLE;
    }

	public Symbol createMethod(String name, Symbol receiver, Symbol[] arguments) {
		// TODO Auto-generated method stub
		return null;
	}

	public Symbol createField(String name, Symbol receiver) {
		// TODO Auto-generated method stub
		return null;
	}

	public Symbol createConstructor(String type, Symbol receiver, Symbol[] arguments) {
		// TODO Auto-generated method stub
		return null;
	}
}
