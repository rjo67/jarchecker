package de.rjo.jarchecker.checker.comparator;

public interface Comparator<T extends Comparable<T>> {
	boolean compare(T actualSize);

	String describe(T actualSize);

}
