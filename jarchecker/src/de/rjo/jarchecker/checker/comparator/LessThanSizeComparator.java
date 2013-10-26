package de.rjo.jarchecker.checker.comparator;

public class LessThanSizeComparator<T extends Comparable<T>> implements
		Comparator<T> {

	private T requiredSize;

	LessThanSizeComparator(T requiredSize) {
		this.requiredSize = requiredSize;
	}

	@Override
	public boolean compare(T actualSize) {
		return actualSize.compareTo(requiredSize) <= 0;
	}

	@Override
	public String describe(T actualSize) {
		return "size " + actualSize + " ! <= " + requiredSize;
	}
}
