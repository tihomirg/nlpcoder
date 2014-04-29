package builders;

import java.io.PrintStream;
import org.eclipse.jdt.core.dom.CompilationUnit;

public interface IBuilder {

	void print(PrintStream out);
	
    void releaseUnder(int percent);
	
    void build(CompilationUnit node);
}
