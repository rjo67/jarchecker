package de.rjo.jarchecker.ant;

import org.apache.tools.ant.BuildException;

import de.rjo.jarchecker.checker.Checker;

/**
 * The 'dirSize' check can be used either within a 'withDir' element or at the
 * toplevel, directly unter 'checker'.
 * 
 * In the latter case this class gets instantiated and requires a 'dir'
 * attribute.
 * 
 * @author rich
 * 
 */
public class DirSizeTopLevel extends DirSize {

	private String dir;

	public void setDir(String dirname) {
		this.dir = dirname;
	}

	@Override
	public Checker buildChecker(String unusedDirname) {
		if (dir == null) {
			throw new BuildException(
					"Attribute 'dir' must be set when using 'dirSize' at top-level");
		}

		return super.buildChecker(dir);
	}

}
