package com.copetti.threeD.shapes;

import static org.junit.Assert.*;

import org.junit.Test;

import com.copetti.threeD.math.grid.Grid2D;


public class Grid2DCompliantBuilderTest
{

	@Test
	public void testBuilderEquals()
	{
		Grid2D<Integer> grid = Grid2D.fromArray(new Integer[]
		{ 1, 2, 3, 4 });
		assertEquals(grid, Grid2DCompliantBuilder.build(grid));
	}
}
