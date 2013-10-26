package de.rjo.jarchecker.checker;

import static de.rjo.jarchecker.checker.comparator.ComparatorMethods.eq;
import static de.rjo.jarchecker.checker.comparator.ComparatorMethods.gt;
import static de.rjo.jarchecker.checker.comparator.ComparatorMethods.lt;
import static de.rjo.jarchecker.checker.comparator.ComparatorMethods.not;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.rjo.jarchecker.JarChecker;
import de.rjo.jarchecker.JarChecker.Result;
import de.rjo.jarchecker.config.Config;

public class EntryCheckerTest {

	private JarChecker jc;

	@Before
	public void setup() throws IllegalAccessException, IOException {
		jc = new JarChecker("ant.jar");
	}

	@Test
	public void simpleCheckForPresence() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder("org/apache/tools/ant").entry(
						"antlib.xml").build());
				return al.iterator();
			}

		});
		checkResultOk(jc.check());
	}

	@Test
	public void failingCheckForPresence() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder("org/apache/tools/ant").entry(
						"antlib2.xml").build());
				return al.iterator();
			}

		});
		checkResultNotOk(1, jc.check());
	}

	@Test
	public void simpleCheckForNonPresence() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder("org/apache/tools/ant")
						.missingEntry("antlib2.xml").build());
				return al.iterator();
			}

		});
		checkResultOk(jc.check());
	}

	@Test
	public void failingCheckForNonPresence() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder("org/apache/tools/ant/")
						.missingEntry("antlib.xml").build());
				return al.iterator();
			}

		});
		checkResultNotOk(1, jc.check());
	}

	@Test
	public void entryPresent() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder()
						.entryFQ(
								"org/apache/tools/ant/util/optional/JavaxScriptRunner.class",
								eq(4513)).forDirectory("org/apache/tools/ant")
						.entry("antlib.xml", gt(7500)).build());
				return al.iterator();
			}

		});
		checkResultOk(jc.check());
	}

	@Test
	public void entryNotPresent() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder()
						.entryFQ(
								"org/apache/tools/ant/util/optional/NotThere.class",
								eq(4513))
						.entryFQ("org/apache/tools/notthere/NotThere.class",
								eq(2313)).build());
				return al.iterator();
			}

		});
		checkResultNotOk(2, jc.check());
	}

	@Test
	public void entryWithFailingComparator() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder().entryFQ(
						"org/apache/tools/ant/antlib.xml", eq(4514)).build());
				return al.iterator();
			}

		});
		checkResultNotOk(1, jc.check());
	}

	@Test
	public void entryPresentWithNot() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder("org/apache/tools/ant").entry(
						"antlib.xml", not(lt(7500))).build());
				return al.iterator();
			}

		});
		checkResultOk(jc.check());
	}

	private void checkResultOk(Result result) {
		if (result.getNbrErrors() != 0) {
			System.out.println(result.listErrors());
			fail("expected no errors, got " + result.getNbrErrors());
		}
	}

	private void checkResultNotOk(int expectedMessages, Result result) {
		if (result.getNbrErrors() != expectedMessages) {
			System.out.println(result.listErrors());
			fail("expected " + expectedMessages + " errors, got "
					+ result.getNbrErrors());
		}
	}
}
