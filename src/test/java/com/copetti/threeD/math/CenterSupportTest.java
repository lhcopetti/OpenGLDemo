package com.copetti.threeD.math;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.junit.Assert;
import org.junit.Test;

import com.copetti.threeD.math.grid.Grid2D;


public class CenterSupportTest
{

	@Test
	public void testCenterGrid()
	{
		Grid2D<Vector2f> grid = Grid2D.fromArray(new Vector2f[]
		{ new Vector2f(0f, 0f), //
				new Vector2f(1f, 0f), //
				new Vector2f(0f, -1f), //
				new Vector2f(1f, -1f) });

		Vector2f[] exp = new Vector2f[]
		{ new Vector2f(-.5f, .5f), new Vector2f(.5f, .5f),
				new Vector2f(-.5f, -.5f), new Vector2f(.5f, -.5f) };
		Assert.assertEquals(Grid2D.fromArray(exp), CenterSupport.center(grid));
	}

	@Test
	public void testCenterWithTranslation()
	{
		Grid2D<Vector2f> grid = Grid2D.fromArray(new Vector2f[]
		{ new Vector2f(-5f, -1f), //
				new Vector2f(-2f, -1f), //
				new Vector2f(-5f, -10f), //
				new Vector2f(-2f, -10f) });

		Vector2f[] exp = new Vector2f[]
		{ new Vector2f(-1.5f, 4.5f), //
				new Vector2f(1.5f, 4.5f), //
				new Vector2f(-1.5f, -4.5f), //
				new Vector2f(1.5f, -4.5f) };
		Assert.assertEquals(Grid2D.fromArray(exp), CenterSupport.center(grid));
	}

	@Test
	public void testCenterOneElement()
	{
		Grid2D<Vector2f> grid = Grid2D.fromArray(new Vector2f[]
		{ new Vector2f(5.f, -15.f) });
		Assert.assertEquals(new Vector2f(0.f, 0.f),
				CenterSupport.center(grid).get(0, 0));
	}

	@Test
	public void testCenterOneElement3D()
	{
		Grid2D<Vector3f> grid = Grid2D.fromArray(new Vector3f[]
		{ new Vector3f(5.f, -15.f, 3.f) });
		Assert.assertEquals(new Vector3f(0.f, 0.f, 0.f),
				CenterSupport.centerVector3f(grid).get(0, 0));
	}

	@Test
	public void testMidPoint()
	{
		Assert.assertEquals(4.f, CenterSupport.midPoint(2.f, 6.f), 0.0001f);
		Assert.assertEquals(3.5f, CenterSupport.midPoint(0.f, 7.f), 0.0001f);
		Assert.assertEquals(-4.f, CenterSupport.midPoint(-6.f, -2.f), 0.0001f);
		Assert.assertEquals(-4.f, CenterSupport.midPoint(-2.f, -6.f), 0.0001f);
	}

	@Test
	public void getVectorCenter()
	{
		Grid2D<Vector2f> grid = Grid2D.fromArray(new Vector2f[]
		{ new Vector2f(-4.f, 4.f), new Vector2f(-2.f, 4.f),
				new Vector2f(-4.f, 2.f), new Vector2f(-2.f, 2.f) });
		Assert.assertEquals(new Vector2f(-3.f, 3.f),
				CenterSupport.getMidVector(grid));
	}

}
