package de.rjo.jarchecker.checker.comparator;

public class NoOpComparator<T extends Comparable<T>> implements Comparator<T> {

	public NoOpComparator() {
	}

	@Override
	public boolean compare(T actualSize) {
		return true;
	}

	@Override
	public String describe(T actualSize) {
		return "NOOP";
	}
}
