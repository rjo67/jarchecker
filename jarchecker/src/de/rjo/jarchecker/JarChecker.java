package de.rjo.jarchecker;

import java.io.IOException;
import java.util.Iterator;
import java.util.jar.JarFile;

import de.rjo.jarchecker.Reporter.Prio;
import de.rjo.jarchecker.checker.Checker;
import de.rjo.jarchecker.config.Config;

public class JarChecker {

	private JarFile jar;
	private Config config;
	private JarStructure jarStructure;
	private Reporter reporter;

	public JarChecker(String jarname) throws IOException,
			IllegalAccessException {
		this.jar = new JarFile(jarname);
		jarStructure = JarStructure.readInJar(jar);
		this.reporter = new Reporter();
	}

	public Result check() {
		if (this.config == null) {
			throw new IllegalStateException("configuration not specified");
		}

		doChecks(this.config.checks());

		Result res = new Result(jar.getName(), reporter.numberErrors());
		res.setMessages(reporter.getMessagesOfPrio(Prio.ERROR));
		return res;
	}

	private void doChecks(Iterator<Checker> checkIterator) {
		while (checkIterator.hasNext()) {
			Checker check = checkIterator.next();
			check.check(jarStructure, reporter);
		}
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public static class Result {

		private int nbrErrors;
		private String[] messages; // Info-Priority ignored
		private String jarname;

		public Result(String jarname, int nbrErrors) {
			this.jarname = jarname;
			this.nbrErrors = nbrErrors;
		}

		public int getNbrErrors() {
			return nbrErrors;
		}

		public void setMessages(String[] messages) {
			this.messages = messages;
		}

		public String[] getMessages() {
			return messages;
		}

		public String listErrors() {
			String NEWLINE = System.getProperty("line.separator");
			StringBuilder sb = new StringBuilder(200);
			sb.append("file '").append(jarname).append("' ");
			if (messages.length == 0) {
				sb.append("has no errors");
			} else {
				sb.append("has ").append(nbrErrors)
						.append(nbrErrors == 1 ? " error:" : " errors: ")
						.append(NEWLINE);
				int cnt = 0;
				for (String msg : messages) {
					sb.append((++cnt)).append(": ").append(msg).append(NEWLINE);
				}
			}
			return sb.toString();
		}

	}

}
