package de.rjo.jarchecker.ant;

import java.util.ArrayList;
import java.util.List;

public class WithDir {

	private String name;
	private List<AbstractELementContainer> checkerTasks;

	public WithDir() {
		this.checkerTasks = new ArrayList<>();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addConfiguredDirSize(DirSize dir) {
		this.checkerTasks.add(dir);
	}

	public void addConfiguredNbrEntries(NbrEntries nbrEntries) {
		this.checkerTasks.add(nbrEntries);
	}

	public void addConfiguredEntry(Entry entry) {
		this.checkerTasks.add(entry);
	}

	public String getName() {
		return name;
	}

	public List<AbstractELementContainer> getCheckerTasks() {
		return checkerTasks;
	}

}
