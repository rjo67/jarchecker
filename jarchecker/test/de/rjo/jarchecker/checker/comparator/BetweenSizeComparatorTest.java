package de.rjo.jarchecker.checker.comparator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BetweenSizeComparatorTest {

	@Test
	public void good() {
		BetweenSizeComparator<Integer> bsc = new BetweenSizeComparator<Integer>(
				1, 5);
		assertTrue(bsc.compare(3));
	}

	@Test
	public void goodBottomRange() {
		BetweenSizeComparator<Integer> bsc = new BetweenSizeComparator<Integer>(
				1, 5);
		assertTrue(bsc.compare(1));
	}

	@Test
	public void goodTopRange() {
		BetweenSizeComparator<Integer> bsc = new BetweenSizeComparator<Integer>(
				1, 5);
		assertTrue(bsc.compare(5));
	}

	@Test
	public void bad() {
		BetweenSizeComparator<Integer> bsc = new BetweenSizeComparator<Integer>(
				1, 5);
		assertFalse(bsc.compare(6));
	}

	@Test(expected = IllegalArgumentException.class)
	public void badSetup() {
		new BetweenSizeComparator<Integer>(4, 3);
	}

}
