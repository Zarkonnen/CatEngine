package com.zarkonnen.catengine.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class LnTest {
	@Test
	public void testEquals() {
		Ln a = new Ln(17.8, -99.0/7, 12.4, 32);
		assertEquals(a, a);
		Ln a2 = new Ln(17.8, -99.0/7, 12.4, 32);
		assertEquals(a, a2);
		Ln b = new Ln(17.8, -99.0/7, 12.4, 32.01);
		assertFalse(a.equals(b));
		Ln c = new Ln(10, 10, 15, 15);
		Ln d = new Ln(10, 10, 20, 20);
		assertFalse(c.equals(d));
		assertFalse(d.equals(c));
		Ln e = new Ln(15, 15, 10, 10);
		assertFalse(c.equals(e));
		assertFalse(e.equals(c));
	}

	@Test
	public void testHashCode() {
		Ln a = new Ln(17.8, -99.0/7, 12.4, 32);
		assertEquals(a.hashCode(), a.hashCode());
		Ln a2 = new Ln(17.8, -99.0/7, 12.4, 32);
		assertEquals(a.hashCode(), a2.hashCode());
		Ln b = new Ln(17.8, -99.0/7, 12.4, 32.01);
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void testCenter() {
		Ln a = new Ln(10, 10, 16, 15);
		Pt b = new Pt(13, 12.5);
		assertEquals(a.center(), b);
	}

	@Test
	public void testBounds() {
		Ln a = new Ln(10, 10, 16, 15);
		Rect b = new Rect(10, 10, 6, 5);
		Rect bounds = a.bounds();
		assertEquals(b, bounds);
	}

	@Test
	public void testIntersects() {
		Ln a = new Ln(10, 10, 20, 20);
		assertTrue(a.intersects(a));
		Ln b = new Ln(10, 20, 20, 10);
		assertTrue(a.intersects(b));
		assertTrue(b.intersects(a));
		Ln c = new Ln(11, 10, 21, 20);
		assertFalse(a.intersects(c));
		assertFalse(c.intersects(a));
		Ln d = new Ln(-20, -20, 0, 0);
		assertFalse(a.intersects(d));
		assertFalse(d.intersects(a));
		Ln e = new Ln(20, 20, 15, 30);
		Ln f = new Ln(30, 15, 20, 20);
		Ln g = new Ln(10, 10, 5, 0);
		Ln h = new Ln(5, 0, 10, 10);
		assertTrue(a.intersects(e));
		assertTrue(e.intersects(a));
		assertTrue(a.intersects(f));
		assertTrue(f.intersects(a));
		assertTrue(a.intersects(g));
		assertTrue(g.intersects(a));
		assertTrue(a.intersects(h));
		assertTrue(h.intersects(a));
	}

	@Test
	public void testContains() {
		Ln a = new Ln(10, 10, 20, 20);
		assertTrue(a.contains(a));
		assertTrue(a.reversed().contains(a));
		assertTrue(a.contains(a.reversed()));
		Ln b = new Ln(0, 0, 30, 30);
		assertTrue(b.contains(a));
		assertFalse(a.contains(b));
		assertTrue(b.reversed().contains(a));
		assertFalse(a.reversed().contains(b));
		Ln c = new Ln(15, 15, 30, 30);
		assertFalse(a.contains(c));
		assertFalse(c.contains(a));
		assertFalse(a.reversed().contains(c));
		assertFalse(c.reversed().contains(a));
		Ln d = new Ln(10, 11, 20, 21);
		assertFalse(a.contains(d));
		assertFalse(d.contains(a));
		assertFalse(a.reversed().contains(d));
		assertFalse(d.reversed().contains(a));
		Ln e = new Ln(10, 20, 20, 10);
		assertFalse(a.contains(e));
		assertFalse(e.contains(a));
		assertFalse(a.reversed().contains(e));
		assertFalse(e.reversed().contains(a));
	}
	
	@Test
	public void testCenterDist() {
		Ln a = new Ln(10, 10, 20, 20);
		assertTrue(a.centerDist(a) == 0);
		assertTrue(a.reversed().centerDist(a) == 0);
		Ln b = new Ln(12, 10, 22, 20);
		assertTrue(a.centerDist(b) == 2);
		assertTrue(b.centerDist(a) == 2);
		assertTrue(a.reversed().centerDist(b) == 2);
		assertTrue(b.reversed().centerDist(a) == 2);
	}
	
	@Test
	public void testCenterDistSq() {
		Ln a = new Ln(10, 10, 20, 20);
		assertTrue(a.centerDist(a) == 0);
		assertTrue(a.reversed().centerDist(a) == 0);
		Ln b = new Ln(12, 10, 22, 20);
		assertTrue(a.centerDist(b) == 4);
		assertTrue(b.centerDist(a) == 4);
		assertTrue(a.reversed().centerDist(b) == 4);
		assertTrue(b.reversed().centerDist(a) == 4);
	}
	
	/*@Test
	public void testDist() {
		Ln a = new Ln(10, 10, 20, 20);
		assertTrue(a.dist(a) == 0);
		assertTrue(a.reversed().dist(a) == 0);
		Ln b = new Ln(20, 10, 10, 20);
		assertTrue(a.dist(b) == 0);
		assertTrue(b.dist(a) == 0);
		assertTrue(a.reversed().dist(b) == 0);
		assertTrue(b.reversed().dist(a) == 0);
		Ln c = new Ln(20, 20, 11, -5);
		assertTrue(a.dist(c) == 0);
		assertTrue(c.dist(a) == 0);
		assertTrue(a.reversed().dist(c) == 0);
		assertTrue(c.reversed().dist(a) == 0);
		Ln d = new Ln(10, 10, 10, 20);
		Ln e = new Ln(15, 10, 15, 20);
		assertTrue(d.dist(e) == 5);
		assertTrue(e.dist(d) == 5);
		assertTrue(d.reversed().dist(e) == 5);
		assertTrue(e.reversed().dist(d) == 5);
		Ln f = new Ln(10, 20, 20, 20);
		Ln g = new Ln(10, 25, 20, 25);
		assertTrue(f.dist(g) == 5);
		assertTrue(g.dist(f) == 5);
		assertTrue(f.reversed().dist(g) == 5);
		assertTrue(g.reversed().dist(f) == 5);
		
	}
	
	@Test
	public void testDistSq() {
		Pt a = new Pt(10, 10);
		Pt b = new Pt(13, 6);
		assertTrue(a.distSq(b) == 25);
		assertTrue(b.distSq(a) == 25);
	}*/
	
	@Test
	public void testReversed() {
		Ln a = new Ln(10, 10, 20, 20);
		assertFalse(a.equals(a.reversed()));
		assertEquals(a, a.reversed().reversed());
		Ln b = new Ln(20, 20, 10, 10);
		assertEquals(a.reversed(), b);
	}
}
