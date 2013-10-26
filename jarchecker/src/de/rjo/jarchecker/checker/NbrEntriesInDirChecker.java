package de.rjo.jarchecker.checker;

import de.rjo.jarchecker.JarStructure;
import de.rjo.jarchecker.Reporter;
import de.rjo.jarchecker.checker.comparator.Comparator;

public class NbrEntriesInDirChecker extends AbstractChecker {

	public NbrEntriesInDirChecker(String dirname, Comparator<Integer> check) {
		super(dirname, check);
	}

	@Override
	public void check(JarStructure jarStructure, Reporter reporter) {
		int actualSize = jarStructure.getEntriesInDir(getDirname()).size();
		if (!getComparator().compare(actualSize)) {
			reporter.addErrorMessage("Entries in Dir '" + getDirname() + "': "
					+ getComparator().describe(actualSize));
		}
	}

}
