package de.rjo.jarchecker.checker.comparator;

public class BetweenSizeComparator<T extends Comparable<T>> implements
		Comparator<T> {

	private T minRequiredSize;
	private T maxRequiredSize;

	BetweenSizeComparator(T minRequiredSize, T maxRequiredSize) {
		if (minRequiredSize.compareTo(maxRequiredSize) > 0) {
			throw new IllegalArgumentException("min (" + minRequiredSize
					+ ") > max (" + maxRequiredSize + ")");
		}
		this.minRequiredSize = minRequiredSize;
		this.maxRequiredSize = maxRequiredSize;
	}

	@Override
	public boolean compare(T actualSize) {
		return (actualSize.compareTo(maxRequiredSize) <= 0)
				&& (actualSize.compareTo(minRequiredSize) >= 0);
	}

	@Override
	public String describe(T actualSize) {
		return "size " + actualSize + " not in range [" + minRequiredSize + ","
				+ maxRequiredSize + "]";
	}
}
