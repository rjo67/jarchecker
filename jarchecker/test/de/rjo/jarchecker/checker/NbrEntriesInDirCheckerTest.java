package de.rjo.jarchecker.checker;

import static de.rjo.jarchecker.checker.comparator.ComparatorMethods.between;
import static de.rjo.jarchecker.checker.comparator.ComparatorMethods.eq;
import static de.rjo.jarchecker.checker.comparator.ComparatorMethods.gt;
import static de.rjo.jarchecker.checker.comparator.ComparatorMethods.lt;
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

public class NbrEntriesInDirCheckerTest {

	private JarChecker jc;

	@Before
	public void setup() throws IllegalAccessException, IOException {
		jc = new JarChecker("ant.jar");
	}

	@Test
	public void nbrEntriesBadCheck() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder(
						"org/apache/tools/ant/util/optional").nbrEntries(eq(2))
						.build());
				return al.iterator();
			}

		});
		checkResultNotOk(1, jc.check());
	}

	@Test
	public void nbrEntriesGoodCheck() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder(
						"org/apache/tools/ant/util/optional").nbrEntries(eq(3))
						.build());
				return al.iterator();
			}

		});
		checkResultOk(jc.check());
	}

	@Test
	public void greaterthanbadCheck() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder(
						"org/apache/tools/ant/types/optional")
						.nbrEntries(gt(9)).build());
				return al.iterator();
			}

		});
		checkResultNotOk(1, jc.check());
	}

	@Test
	public void greaterthangoodCheck() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder(
						"org/apache/tools/ant/types/optional")
						.nbrEntries(gt(5)).build());
				return al.iterator();
			}

		});
		checkResultOk(jc.check());
	}

	@Test
	public void lessthanBadCheck() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder("org/apache/tools/bzip2")
						.nbrEntries(lt(4)).build());
				return al.iterator();
			}

		});
		checkResultNotOk(1, jc.check());
	}

	@Test
	public void lessthanGoodCheck() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder("org/apache/tools/bzip2")
						.nbrEntries(lt(6)).build());
				return al.iterator();
			}

		});
		checkResultOk(jc.check());
	}

	@Test
	public void betweenBadCheck() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder(
						"org/apache/tools/ant/taskdefs/optional/depend")
						.nbrEntries(between(2, 5)).build());
				return al.iterator();
			}

		});
		checkResultNotOk(1, jc.check());
	}

	@Test
	public void betweenGoodCheck() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder(
						"org/apache/tools/ant/taskdefs/optional/depend")
						.nbrEntries(between(8, 9)).build());
				return al.iterator();
			}

		});
		checkResultOk(jc.check());
	}

	@Test
	public void multipleChecks() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder(
						"org/apache/tools/ant/util/optional")
						.nbrEntries(eq(2))
						.forDirectory("org/apache/tools/ant/types/optional")
						.nbrEntries(gt(4))
						.forDirectory("org/apache/tools/bzip2")
						.nbrEntries(lt(4))
						.forDirectory(
								"org/apache/tools/ant/taskdefs/optional/depend")
						.nbrEntries(between(2, 5)).build());
				return al.iterator();
			}

		});
		checkResultNotOk(3, jc.check());
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
