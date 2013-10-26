package de.rjo.jarchecker;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarStructure {

	// each directory in jar is stored in a hashmap (dirname, Set<String);
	// Each map entry is itself a map (filename, JarEntry)
	private Map<String, Map<String, JarEntry>> directories;

	public JarStructure() {
		this.directories = new HashMap<>();
	}

	public void addDirectory(String dirName) throws IllegalAccessException {
		addDirectory(dirName, false);
	}

	public void addDirectory(String dirName, boolean ignoreIfAlreadyPresent)
			throws IllegalAccessException {
		dirName = Util.removeTrailingSlash(dirName);
		if (this.directories.containsKey(dirName)) {
			if (!ignoreIfAlreadyPresent) {
				throw new IllegalAccessException("dirname '" + dirName
						+ "' already stored");
			}
		} else {
			this.directories.put(dirName, new HashMap<String, JarEntry>());
		}
	}

	private void addFile(JarEntry je) throws IllegalAccessException {
		addFile(Util.dirname(je.getName()), Util.basename(je.getName()), je);
	}

	private void addFile(String dirName, String fileName, JarEntry jarEntry)
			throws IllegalAccessException {
		dirName = Util.removeTrailingSlash(dirName);
		addDirectory(dirName, true);
		JarEntry ret = this.directories.get(dirName).put(fileName, jarEntry);
		if (ret != null) {
			throw new IllegalAccessException("could not add filename '"
					+ fileName + "' with dirname '" + dirName + "'");
		}
	}

	public String show() {
		StringBuilder sb = new StringBuilder(100);
		boolean first = true;
		for (String dirname : directories.keySet()) {
			if (!first) {
				sb.append(", ");
			}
			first = false;
			sb.append(dirname).append(": ")
					.append(directories.get(dirname).size());
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public Map<String, JarEntry> getEntriesInDir(String dirname) {
		Map<String, JarEntry> map = directories.get(dirname);
		if (map == null) {
			return Collections.EMPTY_MAP;
		} else {
			return Collections.unmodifiableMap(map);
		}
	}

	public int getSizeOfDir(String dirname) {
		Map<String, JarEntry> map = getEntriesInDir(dirname);
		int size = 0;
		for (JarEntry je : map.values()) {
			size += je.getSize();
		}
		return size;
	}

	public static JarStructure readInJar(JarFile jar)
			throws IllegalAccessException {
		JarStructure jarStructure = new JarStructure();
		Enumeration<JarEntry> entries = jar.entries();
		while (entries.hasMoreElements()) {
			JarEntry je = entries.nextElement();
			if (je.isDirectory()) {
				jarStructure.addDirectory(je.getName());
				// System.out.println(je.getName());
			} else {
				jarStructure.addFile(je);
				// System.out.println(dirname + "," + basename);
			}
		}
		// System.out.println(jarStructure.show());
		return jarStructure;
	}

}
