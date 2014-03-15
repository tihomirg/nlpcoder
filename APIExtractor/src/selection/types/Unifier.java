package selection.types;

import java.util.LinkedList;
import java.util.List;

public class Unifier {
	private static final Unifier emptyFalse = new Unifier(false);
	private static final Unifier emptyTrue = new Unifier(true);
	
	private final List<Substitution> subs;
	private final boolean success;

	public Unifier(boolean success) {
		this(success, new LinkedList<Substitution>());
	}	
	
	public Unifier(boolean success, List<Substitution> subs) {
		this.success = success;
		this.subs = subs;
	}

	public static Unifier False() {
		return emptyFalse;
	}

	public static Unifier True() {
		return emptyTrue;
	}

	public boolean isSuccess() {
		return success;
	}

	public List<Substitution> getSubs() {
		return subs;
	}
}
