package de.rjo.jarchecker.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

import de.rjo.jarchecker.checker.Checker;
import de.rjo.jarchecker.checker.comparator.Comparator;
import de.rjo.jarchecker.checker.comparator.ComparatorMethods;

public abstract class AbstractELementContainer {

	public enum ComparatorOps {
		eq, lt, gt, between;
	}

	protected ComparatorOps op;
	protected int nbr = Integer.MIN_VALUE;
	protected int nbr2 = Integer.MIN_VALUE;
	protected Project project;// set by CheckerTask i/c of requirement to log

	public void setNbr(int nbr) {
		this.nbr = nbr;
	}

	public void setNbr2(int nbr2) {
		this.nbr2 = nbr2;
	}

	public void setOp(ComparatorOps op) {
		this.op = op;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	protected Comparator<Integer> buildComparator() {
		Comparator<Integer> check;
		switch (op) {
		case eq:
			checkParameterSet("nbr", nbr);
			checkParameterNotSet("nbr2", nbr2);
			check = ComparatorMethods.eq(nbr);
			break;
		case lt:
			checkParameterSet("nbr", nbr);
			checkParameterNotSet("nbr2", nbr2);
			check = ComparatorMethods.lt(nbr);
			break;
		case gt:
			checkParameterSet("nbr", nbr);
			checkParameterNotSet("nbr2", nbr2);
			check = ComparatorMethods.gt(nbr);
			break;
		case between:
			checkParameterSet("nbr", nbr);
			checkParameterSet("nbr2", nbr2);
			check = ComparatorMethods.between(nbr, nbr2);
			break;
		default:
			throw new BuildException("unrecognised value for op: '" + op + "'");
		}
		return check;
	}

	private void checkParameterSet(String id, int nbr) {
		if (nbr == Integer.MIN_VALUE) {
			throw new BuildException("Attribute '" + id
					+ "' must be set for op '" + op + "'");
		}
	}

	private void checkParameterNotSet(String id, int nbr) {
		if (nbr != Integer.MIN_VALUE) {
			throw new BuildException("Attribute '" + id
					+ "' must not be set for op '" + op + "'");
		}
	}

	public abstract Checker buildChecker(String dirname);
}
