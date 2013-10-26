package de.rjo.jarchecker.checker;

import de.rjo.jarchecker.JarStructure;
import de.rjo.jarchecker.Reporter;
import de.rjo.jarchecker.checker.comparator.Comparator;

public class SizeOfDirChecker extends AbstractChecker {

	public SizeOfDirChecker(String dirname, Comparator<Integer> check) {
		super(dirname, check);
	}

	@Override
	public void check(JarStructure jarStructure, Reporter reporter) {
		Integer actualSize = jarStructure.getSizeOfDir(getDirname());
		if (!getComparator().compare(actualSize)) {
			reporter.addErrorMessage("Dir '" + getDirname() + "': "
					+ getComparator().describe(actualSize));
		}
	}

}
