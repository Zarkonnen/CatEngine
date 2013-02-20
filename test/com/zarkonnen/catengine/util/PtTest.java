package com.zarkonnen.catengine.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class PtTest {
	@Test
	public void testEquals() {
		Pt a = new Pt(17.8, -99.0/7);
		assertEquals(a, a);
		Pt a2 = new Pt(17.8, -99.0/7);
		assertEquals(a, a2);
		Pt b = new Pt(17.8, -98.0/7);
		assertFalse(a.equals(b));
	}

	@Test
	public void testHashCode() {
		Pt a = new Pt(17.8, -99.0/7);
		assertEquals(a.hashCode(), a.hashCode());
		Pt a2 = new Pt(17.8, -99.0/7);
		assertEquals(a.hashCode(), a2.hashCode());
		Pt b = new Pt(17.8, -98.0/7);
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void testCenter() {
		Pt a = new Pt(17.8, -99.0/7);
		assertEquals(a.center(), a);
	}

	@Test
	public void testBounds() {
		Pt a = new Pt(17.8, -99.0/7);
		Rect bounds = a.bounds();
		assertTrue(a.x == bounds.x);
		assertTrue(a.y == bounds.y);
		assertTrue(bounds.width == 0);
		assertTrue(bounds.height == 0);
	}

	@Test
	public void testIntersects() {
		Pt a = new Pt(17.8, -99.0/7);
		assertTrue(a.intersects(a));
		Pt b = new Pt(17.8, -98.0/7);
		assertFalse(a.intersects(b));
		Rect r1 = new Rect(15.2, -107, 19, 205);
		assertTrue(a.intersects(r1));
		Rect r2 = new Rect(-40, -12, 1, 1);
		assertFalse(a.intersects(r2));
		Ln l1 = new Ln(16.8, -99.0/7, 18.8, -99.0/7);
		assertTrue(a.intersects(l1));
		Ln l2 = new Ln(16.8, -99.0/7, 18.8, -98.0/7);
		assertFalse(a.intersects(l2));
		Ln l3 = new Ln(a, b);
		assertTrue(a.intersects(l3));
	}

	@Test
	public void testContains() {
		Pt a = new Pt(17.8, -99.0/7);
		assertTrue(a.contains(a));
		Pt b = new Pt(17.8, -98.0/7);
		assertFalse(a.contains(b));
		Rect r1 = new Rect(15.2, -107, 19, 205);
		assertFalse(a.contains(r1));
		Rect r2 = new Rect(a, a);
		assertTrue(a.contains(r2));
		Ln l1 = new Ln(16.8, -99.0/7, 18.8, -99.0/7);
		assertFalse(a.contains(l1));
		Ln l2 = new Ln(16.8, -99.0/7, 18.8, -98.0/7);
		assertFalse(a.contains(l2));
		Ln l3 = new Ln(a, b);
		assertFalse(a.contains(l3));
		Ln l4 = new Ln(a, a);
		assertTrue(a.contains(l4));
	}
	
	@Test
	public void testCenterDist() {
		Pt a = new Pt(10, 10);
		Pt b = new Pt(13, 6);
		assertTrue(a.centerDist(b) == 5);
		assertTrue(b.centerDist(a) == 5);
	}
	
	@Test
	public void testCenterDistSq() {
		Pt a = new Pt(10, 10);
		Pt b = new Pt(13, 6);
		assertTrue(a.centerDistSq(b) == 25);
		assertTrue(b.centerDistSq(a) == 25);
	}
}
