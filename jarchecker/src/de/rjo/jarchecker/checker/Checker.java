package de.rjo.jarchecker.checker;

import de.rjo.jarchecker.JarStructure;
import de.rjo.jarchecker.Reporter;

public interface Checker {

	void check(JarStructure jarStructure, Reporter reporter);
}
