package de.rjo.jarchecker.ant;

import de.rjo.jarchecker.checker.Checker;
import de.rjo.jarchecker.checker.NbrEntriesInDirChecker;

public class NbrEntries extends AbstractELementContainer {

	@Override
	public Checker buildChecker(String dirname) {
		return new NbrEntriesInDirChecker(dirname, buildComparator());
	}

}
