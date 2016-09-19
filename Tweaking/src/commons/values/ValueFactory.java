package commons.values;

public class ValueFactory<T extends Ordered<T>> {
	
	private static final ValueFactory factory = new ValueFactory();
	
	public T create(Class<?> clazz){
		if(clazz == Value.class) return (T) new Value();
		else if(clazz == RankedValue.class) return (T) new RankedValue();
		else return null;
	}
	
	public static ValueFactory getInstance(){
		return factory;
	}
}
