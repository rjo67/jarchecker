package de.rjo.jarchecker.checker.comparator;

public class ComparatorMethods {

	private ComparatorMethods() {
	}

	public static Comparator<Integer> between(Integer min, Integer max) {
		return new BetweenSizeComparator<Integer>(min, max);
	}

	public static Comparator<Integer> eq(Integer requiredSize) {
		return new EqualsSizeComparator<Integer>(requiredSize);
	}

	public static Comparator<Integer> gt(Integer requiredSize) {
		return new GreaterThanSizeComparator<Integer>(requiredSize);
	}

	public static Comparator<Integer> lt(Integer requiredSize) {
		return new LessThanSizeComparator<Integer>(requiredSize);
	}

	public static Comparator<Integer> not(Comparator<Integer> comparator) {
		return new NotComparator<Integer>(comparator);
	}

	public static Comparator<Integer> noop() {
		return new NoOpComparator<Integer>();
	}

}
