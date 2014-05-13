package definitions;

import java.util.List;

import statistics.posttrees.Expr;
import types.Substitution;

public class Param {
	private int id;
	private Expr exp;
	private List<Substitution> subs;
}