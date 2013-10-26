package de.rjo.jarchecker.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.rjo.jarchecker.checker.comparator.Comparator;

public class CheckerBuilder {

	private String dirname;
	private List<Checker> checkerList;

	public CheckerBuilder() {
		checkerList = new ArrayList<>();
	}

	public CheckerBuilder(String dirname) {
		this();
		forDirectory(dirname);
	}

	public CheckerBuilder forDirectory(String dirname) {
		if (dirname.endsWith("/")) {
			this.dirname = dirname.substring(0, dirname.length() - 1);
		} else {
			this.dirname = dirname;
		}
		return this;
	}

	public Collection<Checker> build() {
		return this.checkerList;
	}

	public CheckerBuilder nbrEntries(Comparator<Integer> comparator) {
		checkerList.add(new NbrEntriesInDirChecker(dirname, comparator));
		return this;
	}

	public CheckerBuilder size(Comparator<Integer> comparator) {
		checkerList.add(new SizeOfDirChecker(dirname, comparator));
		return this;
	}

	// entry check using fully qualified name
	public CheckerBuilder entryFQ(String path, Comparator<Integer> comparator) {
		checkerList.add(new EntryChecker(path, comparator));
		return this;
	}

	// entry check using previously specified dirname plus basename
	public CheckerBuilder entry(String basename, Comparator<Integer> comparator) {
		checkerList.add(new EntryChecker(dirname, basename, comparator));
		return this;
	}

	public CheckerBuilder entry(String basename) {
		checkerList.add(new EntryChecker(dirname, basename, true));
		return this;
	}

	public CheckerBuilder missingEntry(String basename) {
		checkerList.add(new EntryChecker(dirname, basename, false));
		return this;
	}

}
