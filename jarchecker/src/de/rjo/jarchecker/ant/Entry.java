package de.rjo.jarchecker.ant;

import org.apache.tools.ant.BuildException;

import de.rjo.jarchecker.checker.Checker;
import de.rjo.jarchecker.checker.EntryChecker;
import de.rjo.jarchecker.checker.comparator.NoOpComparator;

public class Entry extends AbstractELementContainer {

	protected String name;

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Checker buildChecker(String dirname) {
		if (name == null) {
			throw new BuildException(
					"Attribute 'name' must be set when using 'entry'");
		}
		if (op == null) {
			return new EntryChecker(dirname, name,
					new NoOpComparator<Integer>());
		} else {
			return new EntryChecker(dirname, name, buildComparator());
		}
	}

}
