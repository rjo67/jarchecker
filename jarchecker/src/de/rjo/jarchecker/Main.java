package de.rjo.jarchecker;

import java.io.IOException;

import de.rjo.jarchecker.JarChecker.Result;

public class Main {

	public static void main(String[] args) throws IOException,
			IllegalAccessException {
		JarChecker jc = new JarChecker("ant.jar"/* args[0] */);
		Result res = jc.check();
		System.out.println(res.listErrors());
	}
}