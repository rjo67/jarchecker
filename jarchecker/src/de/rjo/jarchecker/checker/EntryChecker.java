package de.rjo.jarchecker.checker;

import java.util.Map;
import java.util.jar.JarEntry;

import de.rjo.jarchecker.JarStructure;
import de.rjo.jarchecker.Reporter;
import de.rjo.jarchecker.Util;
import de.rjo.jarchecker.checker.comparator.Comparator;
import de.rjo.jarchecker.checker.comparator.NoOpComparator;

public class EntryChecker extends AbstractChecker {

	private String filename;
	private boolean checkIfFileIsPresent;

	/**
	 * Constructor for checking an entry specified by a path.
	 * 
	 * @param path
	 *            the path of the entry
	 */
	public EntryChecker(String path) {
		this(path, new NoOpComparator<Integer>(), true);
	}

	/**
	 * Constructor for checking an entry specified by a path.
	 * 
	 * @param path
	 *            the path of the entry
	 * @param checkIfFileIsPresent
	 *            if true, the entry must be present. If false, the entry must
	 *            not be present.
	 */
	public EntryChecker(String path, boolean checkIfFileIsPresent) {
		this(path, new NoOpComparator<Integer>(), checkIfFileIsPresent);
	}

	/**
	 * Constructor for checking an entry specified by a path. The entry must be
	 * present AND the comparator must succeed.
	 * 
	 * @param path
	 *            the path of the entry
	 * @param check
	 *            the comparator to use
	 */
	public EntryChecker(String path, Comparator<Integer> check) {
		this(path, check, true);
	}

	/**
	 * Constructor for checking an entry specified by a path. The entry must be
	 * present AND the comparator must succeed.
	 * 
	 * @param path
	 *            the path of the entry
	 * @param check
	 *            the comparator to use
	 * @param checkIfFileIsPresent
	 *            if true, the entry must be present. If false, the entry must
	 *            not be present.
	 */
	public EntryChecker(String path, Comparator<Integer> check,
			boolean checkIfFileIsPresent) {
		this(Util.dirname(path), Util.basename(path), check,
				checkIfFileIsPresent);
	}

	/**
	 * Constructor for checking an entry specified by a dir and a name.
	 * 
	 * @param path
	 *            the directory path
	 * @param filename
	 *            filename
	 */
	public EntryChecker(String dirname, String filename) {
		this(dirname, filename, new NoOpComparator<Integer>(), true);
	}

	/**
	 * Constructor for checking an entry specified by a dir and a name.
	 * 
	 * @param dirname
	 *            the directory path
	 * @param filename
	 *            filename
	 * @param checkIfFileIsPresent
	 *            if true, the entry must be present. If false, the entry must
	 *            not be present.
	 */
	public EntryChecker(String dirname, String filename,
			boolean checkIfFileIsPresent) {
		this(dirname, filename, new NoOpComparator<Integer>(),
				checkIfFileIsPresent);
	}

	/**
	 * Constructor for checking an entry specified by a dir and a name. The
	 * entry must be present AND the comparator must succeed.
	 * 
	 * @param dirname
	 *            the directory path
	 * @param filename
	 *            filename
	 * @param check
	 *            the comparator to use
	 */
	public EntryChecker(String dirname, String filename,
			Comparator<Integer> check) {
		this(dirname, filename, check, true);
	}

	/**
	 * Constructor for checking an entry specified by a dir and a name. The
	 * entry must be present AND the comparator must succeed.
	 * 
	 * @param path
	 *            the directory path
	 * @param filename
	 *            filename
	 * @param check
	 *            the comparator to use
	 * @param checkIfFileIsPresent
	 *            if true, the entry must be present. If false, the entry must
	 *            not be present.
	 */
	public EntryChecker(String dirname, String filename,
			Comparator<Integer> check, boolean checkIfFileIsPresent) {
		super(dirname, check);
		this.filename = filename;
		this.checkIfFileIsPresent = checkIfFileIsPresent;
	}

	private String getEntryname() {
		return getDirname() + "/" + filename;
	}

	@Override
	public void check(JarStructure jarStructure, Reporter reporter) {
		Map<String, JarEntry> entriesInDir = jarStructure
				.getEntriesInDir(getDirname());
		if (entriesInDir.size() == 0) {
			reporter.addErrorMessage("Entry '" + getEntryname()
					+ "' not present (directory not present)");
			return;
		}
		JarEntry fileEntry = entriesInDir.get(filename);
		if (checkIfFileIsPresent && (fileEntry == null)) {
			reporter.addErrorMessage("Entry '" + getEntryname()
					+ "' not present");
			return;
		}
		if (!checkIfFileIsPresent && (fileEntry != null)) {
			reporter.addErrorMessage("Entry '" + getEntryname()
					+ "' unexpectedly present");
			return;
		}
		if (!checkIfFileIsPresent && (fileEntry == null)) {
			// alles gut, aber wir duerfen nicht weiter pruefen
			return;
		}

		int actualSize = (int) fileEntry.getSize();
		if (!getComparator().compare(actualSize)) {
			reporter.addErrorMessage("Entry '" + getEntryname() + "': "
					+ getComparator().describe(actualSize));
		}
	}

}
