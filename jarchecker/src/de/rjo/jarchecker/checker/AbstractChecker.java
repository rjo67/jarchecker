package de.rjo.jarchecker.checker;

import de.rjo.jarchecker.JarStructure;
import de.rjo.jarchecker.Reporter;
import de.rjo.jarchecker.Util;
import de.rjo.jarchecker.checker.comparator.Comparator;

public abstract class AbstractChecker implements Checker {

	private String dirname;
	private Comparator<Integer> comparator;

	@Override
	public abstract void check(JarStructure jarStructure, Reporter reporter);

	public AbstractChecker(String dirname, Comparator<Integer> comparator) {
		if (dirname == null) {
			throw new IllegalArgumentException("dirname cannot be null");
		}
		this.dirname = Util.removeTrailingSlash(dirname);
		this.comparator = comparator;
	}

	public String getDirname() {
		return dirname;
	}

	public Comparator<Integer> getComparator() {
		return comparator;
	}
}