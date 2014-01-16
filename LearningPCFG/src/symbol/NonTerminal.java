package symbol;

import config.Config;

import org.eclipse.jdt.core.dom.ASTNode;

public class NonTerminal extends Symbol {

	private ASTNode node;

	public NonTerminal(ASTNode node) {
		assert node != null;
		this.node = node;
	}

	private ASTNode getGrandad() {
		ASTNode parent = getParent();
		return parent != null ? parent.getParent() : null;
	}

	public String toString() {
		switch (Config.getType()) {
		case Config.NAIVE:
			return toStringNaive();
		case Config.WITH_PARENT:
			return toStringWithParent();
		case Config.WITH_GRANDAD:
			return toStringWithGrandad();
		default:
			return toStringNaive();
		}
	}

	private String toStringNaive() {
		return node.getClass().getSimpleName();
	}

	private ASTNode getParent() {
		return node.getParent();
	}

	private String toStringWithParent() {
		ASTNode parent = getParent();
		return toStringNaive() + (parent != null ? "^" + parent.getClass().getSimpleName() : "^NULL");
	}

	private String toStringWithGrandad() {
		ASTNode grandad = getGrandad();
		return toStringWithParent() + (grandad != null ? "^" + grandad.getClass().getSimpleName() : "^NULL");
	}

	private int naiveHashCode() {
		return toStringNaive().hashCode() ^ 348274982;
	}

	private int withParentHashCode() {
		ASTNode parent = getParent();
		if (parent == null)
			return naiveHashCode();
		else
			return (naiveHashCode() + parent.getClass().getSimpleName().hashCode()) ^ 487535932;
	}

	private int withGrandadHashCode() {
		ASTNode grandad = getGrandad();
		if (grandad == null)
			return withParentHashCode();
		else
			return (withParentHashCode() + grandad.getClass().getSimpleName().hashCode()) ^ 95493943;
	}

	public int hashCode() {
		switch (Config.getType()) {
		case Config.NAIVE:
			return naiveHashCode();
		case Config.WITH_PARENT:
			return withParentHashCode();
		case Config.WITH_GRANDAD:
			return withGrandadHashCode();
		default:
			return naiveHashCode();
		}
	}

}
