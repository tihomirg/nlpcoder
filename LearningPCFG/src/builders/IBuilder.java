package builders;

import java.io.PrintStream;

import org.eclipse.jdt.core.dom.ASTVisitor;

public abstract class IBuilder extends ASTVisitor {

	public abstract void print(PrintStream out);
	
}
