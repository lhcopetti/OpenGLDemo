package com.copetti.threeD.math.grid;

import static org.junit.Assert.assertArrayEquals;

import org.joml.Vector2f;
import org.junit.Before;
import org.junit.Test;


public class Vector2fGridFlattenerTest
{

	private Grid2D<Vector2f> eightElements;

	@Before
	public void setup()
	{
		eightElements = new Grid2D<>(2, 2);
		float currentIndex = 1.0f;
		for( int i = 0; i < eightElements.height(); i++ )
			for( int j = 0; j < eightElements.width(); j++ )
				eightElements.set(i, j,
						new Vector2f(currentIndex++, currentIndex++));
	}

	@Test
	public void testFlattenVector2f()
	{
		Grid2D<Vector2f> g = new Grid2D<>(1, 1);
		g.set(0, 0, new Vector2f(1.f, 2.f));
		assertArrayEquals(new float[]
		{ 1.0f, 2.0f }, new Vector2fGridFlattener().flatten(g), 0.001f);
	}

	@Test
	public void testMatrixFlattenWithFourElements()
	{
		float[] expectedFlattened = new float[]
		{ 1.0f, 2.f, 3f, 4f, 5f, 6f, 7f, 8f };
		assertArrayEquals(expectedFlattened,
				new Vector2fGridFlattener().flatten(eightElements), 0.001f);
	}
}
