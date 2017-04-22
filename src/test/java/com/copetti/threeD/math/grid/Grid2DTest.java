package com.copetti.threeD.math.grid;

import org.joml.Vector2f;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class Grid2DTest
{

	Grid2D<Vector2f> grid;
	private Grid2D<Vector2f> square2Grid;

	@Before
	public void setup()
	{
		this.grid = new Grid2D<>(3, 2);
		this.square2Grid = new Grid2D<>(2, 2);
		for( int i = 0; i < square2Grid.height(); i++ )
			for( int j = 0; j < square2Grid.width(); j++ )
				square2Grid.set(i, j, new Vector2f(i, j));
	}

	@Test
	public void testGrid2DDimensions()
	{
		Assert.assertEquals(3, grid.width());
		Assert.assertEquals(2, grid.height());
	}

	@Test
	public void testGridIsNullBeforeInitialization()
	{
		Assert.assertNull(grid.get(0, 0));
	}

	@Test
	public void testAssertObjectRetrieval()
	{
		Vector2f v = new Vector2f(5.f, 3.f);
		grid.set(1, 2, v);
		Assert.assertEquals(v, grid.get(1, 2));
	}

	@Test
	public void deepVector2fEquals()
	{
		Grid2D<Vector2f> grid = new Grid2D<>(1, 1);
		grid.set(0, 0, new Vector2f(5.f, 3.f));
		Grid2D<Vector2f> grid2 = new Grid2D<>(1, 1);
		grid2.set(0, 0, new Vector2f(5.f, 3.f));
		Assert.assertEquals(grid, grid2);
	}

	@Test
	public void testGridBuildFromArray()
	{
		Grid2D<Integer> g = Grid2D.fromArray(new Integer[]
		{ 5 });
		Assert.assertEquals((int) g.get(0, 0), 5);
		Assert.assertEquals(1, g.width());
		Assert.assertEquals(1, g.height());
	}

	@Test
	public void testGridBuildSquaredArray()
	{
		Grid2D<Integer> g = Grid2D.fromArray(new Integer[]
		{ 5, 7, 9, 11 });
		Assert.assertEquals((int) g.get(0, 0), 5);
		Assert.assertEquals((int) g.get(0, 1), 7);
		Assert.assertEquals((int) g.get(1, 0), 9);
		Assert.assertEquals((int) g.get(1, 1), 11);
	}

	@Test
	public void testGridBuildNotSquaredArray()
	{
		Grid2D<Integer> g = Grid2D.fromArray(new Integer[]
		{ 5, 7, 9, 11, 15, 21 }, 3);
		Assert.assertEquals((int) g.get(0, 0), 5);
		Assert.assertEquals((int) g.get(0, 1), 7);
		Assert.assertEquals((int) g.get(0, 2), 9);
		Assert.assertEquals((int) g.get(1, 0), 11);
		Assert.assertEquals((int) g.get(1, 1), 15);
		Assert.assertEquals((int) g.get(1, 2), 21);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGridBuildNotSquaredArrayException()
	{
		Grid2D.fromArray(new Integer[]
		{ 5, 7, 9, 11, 15, 21 });
	}

}
