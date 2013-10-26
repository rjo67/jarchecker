package de.rjo.jarchecker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Reporter {

	public static enum Prio {
		INFO, WARNING, ERROR;
	}

	public static class Info {

		private Prio prio;
		private String text;

		public Info(String text, Prio prio) {
			this.text = text;
			this.prio = prio;
		}

		public String getText() {
			return text;
		}

		public Prio getPrio() {
			return prio;
		}

	}

	private List<Info> infos;

	public Reporter() {
		this.infos = new ArrayList<>();
	}

	public void addMessage(Info info) {
		this.infos.add(info);
	}

	public void addMessage(String msg, Prio prio) {
		this.addMessage(new Info(msg, prio));
	}

	public void addErrorMessage(String msg) {
		this.addMessage(new Info(msg, Prio.ERROR));
	}

	public String[] getMessagesOfPrio(Prio reqdPrio) {
		List<String> newList = new ArrayList<>();
		Iterator<Info> iter = infos.iterator();
		while (iter.hasNext()) {
			Info info = iter.next();
			if (info.prio == reqdPrio) {
				newList.add(info.getText());
			}
		}
		return newList.toArray(new String[newList.size()]);
	}

	public int numberErrors() {
		return getMessagesOfPrio(Prio.ERROR).length;
	}

}
