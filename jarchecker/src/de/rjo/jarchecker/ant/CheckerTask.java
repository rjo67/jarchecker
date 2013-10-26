package de.rjo.jarchecker.ant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import de.rjo.jarchecker.JarChecker;
import de.rjo.jarchecker.JarChecker.Result;
import de.rjo.jarchecker.checker.Checker;
import de.rjo.jarchecker.config.Config;

public class CheckerTask extends Task {

	private String filename;
	private List<WithDir> dirResources;
	private List<AbstractELementContainer> topLevelCheckers;

	public CheckerTask() {
		this.dirResources = new ArrayList<>();
		this.topLevelCheckers = new ArrayList<>();
	}

	public void setFile(String filename) {
		this.filename = filename;
	}

	public void addConfiguredWithDir(WithDir withDir) {
		this.dirResources.add(withDir);
	}

	public void addConfiguredDirSize(DirSizeTopLevel ds) {
		ds.setProject(getProject());
		this.topLevelCheckers.add(ds);
	}

	public void addConfiguredNbrEntries(NbrEntriesTopLevel nbrEnt) {
		nbrEnt.setProject(getProject());
		this.topLevelCheckers.add(nbrEnt);
	}

	public void addConfiguredEntry(EntryTopLevel entry) {
		entry.setProject(getProject());
		this.topLevelCheckers.add(entry);
	}

	@Override
	public void execute() throws BuildException {
		final List<Checker> checks = new ArrayList<>();

		for (AbstractELementContainer checkerTask : topLevelCheckers) {
			checks.add(checkerTask.buildChecker("notused"));
		}

		for (WithDir dirResource : dirResources) {
			for (AbstractELementContainer checkerTask : dirResource
					.getCheckerTasks()) {
				checks.add(checkerTask.buildChecker(dirResource.getName()));
			}
		}

		try {
			JarChecker jc = new JarChecker(filename);
			jc.setConfig(new Config() {

				@Override
				public Iterator<Checker> checks() {
					return checks.iterator();
				}

			});
			Result result = jc.check();
			if (result.getNbrErrors() != 0) {
				log(result.listErrors());
				throw new BuildException("jarcheck failed");
			}

		} catch (IllegalAccessException | IOException e) {
			throw new BuildException(e);
		}
	}
}
