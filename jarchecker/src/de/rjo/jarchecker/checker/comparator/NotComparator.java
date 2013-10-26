package de.rjo.jarchecker.checker.comparator;

public class NotComparator<T extends Comparable<T>> implements Comparator<T> {

	private Comparator<T> comparator;

	NotComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	@Override
	public boolean compare(T actualSize) {
		return !comparator.compare(actualSize);
	}

	@Override
	public String describe(T actualSize) {
		return "NOT " + comparator.describe(actualSize);
	}
}
