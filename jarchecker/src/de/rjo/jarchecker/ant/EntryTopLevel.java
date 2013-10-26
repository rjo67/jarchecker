package de.rjo.jarchecker.ant;

import org.apache.tools.ant.BuildException;

import de.rjo.jarchecker.checker.Checker;
import de.rjo.jarchecker.checker.EntryChecker;
import de.rjo.jarchecker.checker.comparator.NoOpComparator;

/**
 * The 'entry' check can be used either within a 'withDir' element or at the
 * toplevel, directly unter 'checker'.
 * 
 * In the latter case this class gets instantiated and requires a 'path'
 * attribute. The 'name' attribute of Entry does not get used at all.
 * 
 * @author rich
 */
public class EntryTopLevel extends Entry {

	private String path;

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public Checker buildChecker(String unusedDirname) {
		if (path == null) {
			throw new BuildException(
					"Attribute 'path' must be set when using 'entry' at top-level");
		}
		if (name != null) {
			project.log("Warning: Attribute 'name' is ignored when using 'entry' at top-level");
		}
		if (op == null) {
			return new EntryChecker(path, new NoOpComparator<Integer>());
		} else {
			return new EntryChecker(path, buildComparator());
		}
	}

}
