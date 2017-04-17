package com.copetti.threeD.math;

import static org.junit.Assert.assertEquals;

import org.joml.Vector2f;
import org.junit.Test;


public class CoordinateSystemTest
{

	@Test
	public void testPolarConversion90Degree()
	{
		PolarCoordinate p = new PolarCoordinate(1.0f, 90f);
		assertVector2fEquals(new Vector2f(0f, 1.f),
				CoordinateSystem.toCartesian(p));
	}

	@Test
	public void testPolarConversion180Degree()
	{
		PolarCoordinate p = new PolarCoordinate(1.0f, 180f);
		assertVector2fEquals(new Vector2f(-1f, 0.f),
				CoordinateSystem.toCartesian(p));
	}

	@Test
	public void testPolarConversion60Degree()
	{
		PolarCoordinate p = new PolarCoordinate(5.0f, 60);
		assertVector2fEquals(
				new Vector2f(2.5f, 4.3301270189221932338186158537647f),
				CoordinateSystem.toCartesian(p));
	}

	public void assertVector2fEquals(Vector2f f, Vector2f s)
	{
		assertEquals(f.x, s.x, 0.0001);
		assertEquals(f.y, s.y, 0.0001);
	}

}
