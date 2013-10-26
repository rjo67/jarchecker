package de.rjo.jarchecker.ant;

import de.rjo.jarchecker.checker.Checker;
import de.rjo.jarchecker.checker.SizeOfDirChecker;

public class DirSize extends AbstractELementContainer {

	@Override
	public Checker buildChecker(String dirname) {
		return new SizeOfDirChecker(dirname, buildComparator());
	}
}
