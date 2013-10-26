package de.rjo.jarchecker.checker;

import static de.rjo.jarchecker.checker.comparator.ComparatorMethods.between;
import static de.rjo.jarchecker.checker.comparator.ComparatorMethods.eq;
import static de.rjo.jarchecker.checker.comparator.ComparatorMethods.gt;
import static de.rjo.jarchecker.checker.comparator.ComparatorMethods.lt;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.rjo.jarchecker.JarChecker;
import de.rjo.jarchecker.JarChecker.Result;
import de.rjo.jarchecker.checker.comparator.Comparator;
import de.rjo.jarchecker.checker.comparator.ComparatorMethods;
import de.rjo.jarchecker.checker.comparator.NoOpComparator;
import de.rjo.jarchecker.config.Config;

public class SizeOfDirCheckerTest {

	private JarChecker jc;

	@Before
	public void setup() throws IllegalAccessException, IOException {
		jc = new JarChecker("ant.jar");
	}

	@Test
	public void equalSizeGoodCheck() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder(
						"org/apache/tools/ant/util/optional").size(eq(5759))
						.build());
				return al.iterator();
			}

		});
		checkResultOk(jc.check());
	}

	@Test
	public void equalSizeBadCheck() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder(
						"org/apache/tools/ant/util/optional").size(eq(5758))
						.build());
				return al.iterator();
			}

		});
		checkResultNotOk(1, jc.check());
	}

	@Test
	public void greaterthanGoodCheck() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder(
						"org/apache/tools/ant/types/optional").size(gt(9000))
						.build());
				return al.iterator();
			}

		});
		checkResultOk(jc.check());
	}

	@Test
	public void greaterthanBadCheck() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder(
						"org/apache/tools/ant/types/optional").size(gt(12000))
						.build());
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
				al.addAll(new CheckerBuilder("org/apache/tools/bzip2").size(
						lt(50000)).build());
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
				al.addAll(new CheckerBuilder("org/apache/tools/bzip2").size(
						lt(45000)).build());
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
						"org/apache/tools/ant/taskdefs/optional/depend").size(
						between(27000, 32000)).build());
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
						"org/apache/tools/ant/taskdefs/optional/depend").size(
						between(17000, 22000)).build());
				return al.iterator();
			}

		});
		checkResultNotOk(1, jc.check());
	}

	@Test
	public void multipleChecks() throws Exception {
		jc.setConfig(new Config() {

			@Override
			public Iterator<Checker> checks() {
				List<Checker> al = new ArrayList<>();
				al.addAll(new CheckerBuilder(
						"org/apache/tools/ant/util/optional")
						.size(eq(5758))
						.forDirectory("org/apache/tools/ant/types/optional")
						.size(gt(12000))
						.forDirectory("org/apache/tools/bzip2")
						.size(lt(45000))
						.forDirectory(
								"org/apache/tools/ant/taskdefs/optional/depend")
						.size(between(17000, 22000)).build());
				return al.iterator();
			}

		});
		checkResultNotOk(4, jc.check());
	}

	@Test(expected = IllegalArgumentException.class)
	public void badConstructor() {
		new SizeOfDirChecker(null, new NoOpComparator<Integer>());
	}

	@Test
	public void noopTest() {
		Comparator<Integer> comp = ComparatorMethods.noop();
		assertTrue(comp instanceof NoOpComparator);
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
