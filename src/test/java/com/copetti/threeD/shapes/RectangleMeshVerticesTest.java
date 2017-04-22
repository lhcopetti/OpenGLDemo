package com.copetti.threeD.shapes;

import static org.junit.Assert.assertEquals;

import org.joml.Vector2f;
import org.junit.Test;

import com.copetti.threeD.math.grid.Grid2D;


public class RectangleMeshVerticesTest
{

	@Test
	public void testFourElementsCenteredAtZero()
	{
		Grid2D<Vector2f> grid = RectangleMeshVertices.newGrid(2, 2);
		assertEquals(new Vector2f(-0.5f, .5f), grid.get(0, 0));
		assertEquals(new Vector2f(.5f, .5f), grid.get(0, 1));
		assertEquals(new Vector2f(-.5f, -.5f), grid.get(1, 0));
		assertEquals(new Vector2f(.5f, -.5f), grid.get(1, 1));
	}
	
	@Test
	public void testNineElementsCenteredAtZero()
	{
		Grid2D<Vector2f> grid = RectangleMeshVertices.newGrid(3, 3);
		assertEquals(new Vector2f().zero(), grid.get(1, 1));
		assertEquals(new Vector2f(-1.f, 1.f), grid.get(0, 0));
	}
	
	@Test
	public void testNonSquaredGridCenteredAtZero()
	{
		Grid2D<Vector2f> grid = RectangleMeshVertices.newGrid(3, 2);
		assertEquals(new Vector2f(-1.f, .5f), grid.get(0, 0));
//		assertEquals(new Vector2f().zero(), grid.get(1, 1));
	}
	
}
