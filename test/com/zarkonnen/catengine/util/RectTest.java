package com.zarkonnen.catengine.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class RectTest {
	@Test
	public void testEquals() {
		Rect a = new Rect(17.8, -99.0/7, 5, 7);
		assertEquals(a, a);
		Rect a2 = new Rect(17.8, -99.0/7, 5, 7);
		assertEquals(a, a2);
		Rect b = new Rect(17.8, -99.0/7, 12, 2);
		assertFalse(a.equals(b));
	}

	@Test
	public void testHashCode() {
		Rect a = new Rect(17.8, -99.0/7, 5, 7);
		assertEquals(a.hashCode(), a.hashCode());
		Rect a2 = new Rect(17.8, -99.0/7, 5, 7);
		assertEquals(a.hashCode(), a2.hashCode());
		Rect b = new Rect(17.8, -99.0/7, 12, 2);
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void testCenter() {
		Rect a = new Rect(20, 20, 11, 12);
		Pt a2 = new Pt(25.5, 26);
		assertEquals(a.center(), a2);
	}

	@Test
	public void testBounds() {
		Rect a = new Rect(17.8, -99.0/7, 17.2, 43);
		Rect bounds = a.bounds();
		assertEquals(a, bounds);
	}

	@Test
	public void testIntersects() {
		Rect a = new Rect(20, 20, 20, 20);
		assertTrue(a.intersects(a));
		Rect b = new Rect(21, 21, 2, 2);
		assertTrue(a.intersects(b));
		assertTrue(b.intersects(a));
		Rect c = new Rect(30, 10, 2, 15);
		assertTrue(a.intersects(c));
		assertTrue(c.intersects(a));
		Rect d = new Rect(43, 43, 5, 12);
		assertFalse(a.intersects(d));
		assertFalse(d.intersects(a));
		Rect e = new Rect(5, 5, 15, 25);
		assertTrue(a.intersects(e));
		assertTrue(e.intersects(a));
		Rect f = new Rect(40, 40, 5, 10);
		assertTrue(a.intersects(f));
		assertTrue(f.intersects(a));
	}

	@Test
	public void testContains() {
		Rect a = new Rect(20, 20, 20, 20);
		assertTrue(a.contains(a));
		Rect b = new Rect(21, 21, 2, 2);
		assertTrue(a.contains(b));
		assertFalse(b.contains(a));
		Rect c = new Rect(30, 10, 2, 15);
		assertFalse(a.contains(c));
		assertFalse(c.contains(a));
		Rect d = new Rect(43, 43, 5, 12);
		assertFalse(a.contains(d));
		assertFalse(d.contains(a));
		Rect e = new Rect(5, 5, 15, 25);
		assertFalse(a.contains(e));
		assertFalse(e.contains(a));
		Rect f = new Rect(40, 40, 5, 10);
		assertFalse(a.contains(f));
		assertFalse(f.contains(a));
	}
	
	@Test
	public void testCenterDist() {
		Rect a = new Rect(20, 20, 20, 20);
		Rect b = new Rect(23, 24, 20, 20);
		assertTrue(a.centerDist(b) == 5);
		assertTrue(b.centerDist(a) == 5);
	}
	
	@Test
	public void testCenterDistSq() {
		Rect a = new Rect(20, 20, 20, 20);
		Rect b = new Rect(23, 24, 20, 20);
		assertTrue(a.centerDistSq(b) == 25);
		assertTrue(b.centerDistSq(a) == 25);
	}
}
