package de.rjo.jarchecker;

public class Util {
	private Util() {
	}

	public static String basename(String path) {
		int index = path.lastIndexOf('/');
		if (index == -1) {
			throw new IllegalArgumentException("path does not contain '/'");
		}
		return path.substring(index + 1);
	}

	public static String dirname(String path) {
		int index = path.lastIndexOf('/');
		if (index == -1) {
			throw new IllegalArgumentException("path does not contain '/'");
		}
		// want the trailing /
		return path.substring(0, index + 1);
	}

	public static String removeTrailingSlash(String path) {
		if (path.endsWith("/")) {
			return path.substring(0, path.length() - 1);
		} else {
			return path;
		}
	}

}
