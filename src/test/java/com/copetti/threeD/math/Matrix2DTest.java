package com.copetti.threeD.math;

import static org.junit.Assert.*;

import org.joml.Vector2f;
import org.junit.Before;
import org.junit.Test;


public class Matrix2DTest
{

	private Matrix2D oneMatrixElement;
	private Matrix2D fourElements;

	@Before
	public void setUpMatrix()
	{
		this.oneMatrixElement = new Matrix2D(1, 1);
		this.oneMatrixElement.set(0, 0, new Vector2f(1.f, 2.f));

		this.fourElements = new Matrix2D(2, 2);
		this.fourElements.set(0, 0, new Vector2f(1.0f, 2.0f));
		this.fourElements.set(0, 1, new Vector2f(3.0f, 4.0f));
		this.fourElements.set(1, 0, new Vector2f(5.0f, 6.0f));
		this.fourElements.set(1, 1, new Vector2f(7.0f, 8.0f));
	}

	@Test
	public void testMatrixDimensions()
	{
		Matrix2D matrix = new Matrix2D(8, 4);
		assertEquals(8, matrix.width());
		assertEquals(4, matrix.height());
	}

	@Test
	public void testNullBeforeInitialization()
	{
		Matrix2D matrix = new Matrix2D(1, 1);
		assertNull(matrix.get(0, 0));
	}

	@Test
	public void testObjectRetrieval()
	{
		Vector2f v = new Vector2f(3.f, 4.5f);
		Matrix2D matrix = new Matrix2D(1, 1);
		matrix.set(0, 0, v);
		assertEquals(v, matrix.get(0, 0));
	}

	@Test
	public void testMatrixFlattenWithOneElement()
	{
		float[] flattened = oneMatrixElement.flatten();
		assertArrayEquals(new float[]
		{ 1.0f, 2.0f }, flattened, 0.001f);
	}

	@Test
	public void testFlattenWithMatrixNotSquare()
	{
		Matrix2D matrix2d = new Matrix2D(3, 2).fill(new Vector2f(0, 0));
		assertArrayEquals(new float[]
		{ 0.f, 0.f, .0f, .0f, .0f, .0f, 0.f, 0.f, .0f, .0f, .0f, .0f }, matrix2d.flatten(), 0.001f);
	}

	@Test
	public void testMatrixFlattenWithFourElements()
	{
		float[] flattened = fourElements.flatten();
		float[] expectedFlattened = new float[]
		{ 1.0f, 2.f, 3f, 4f, 5f, 6f, 7f, 8f };
		assertArrayEquals(expectedFlattened, flattened, 0.001f);
	}

	public void testMatrixWithDistance()
	{
		Matrix2D grid = Matrix2D.newGridWithSideSize(0.25f, 2, 2);
		assertArrayEquals(new float[]
		{ 0.f, 0.f, .25f, 0.f, 0.f, -.25f, .25f, -.25f }, grid.flatten(),
				0.001f);
	}

}
