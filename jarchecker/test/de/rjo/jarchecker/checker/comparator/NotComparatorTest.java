package de.rjo.jarchecker.checker.comparator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NotComparatorTest {

	@Test
	public void success() {
		NotComparator<Integer> comp = new NotComparator<Integer>(
				new EqualsSizeComparator<Integer>(5));
		assertTrue(comp.compare(3));
		// for coverage
		comp.describe(3);
	}

	@Test
	public void failure() {
		NotComparator<Integer> comp = new NotComparator<Integer>(
				new EqualsSizeComparator<Integer>(5));
		assertFalse(comp.compare(5));
	}

}
