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

	// @Test
	// public void testFlattenSquaredGridVector2f()
	// {
	// float[] flattened = new float[]
	// { 0.f, 0.f, 0.f, 1.f, 1.f, 0.f, 1.f, 1.f };
	//
	// Assert.assertArrayEquals(flattened, square2Grid.flatten(), 0.001f);
	// }

}
