package com.copetti.threeD.math;

import java.util.Arrays;
import java.util.List;

import org.joml.Vector3f;
import org.junit.Assert;
import org.junit.Test;


public class VectorMathTest
{

	private Vector3f v0 = new Vector3f(0.f, 0.f, 0.f);
	private Vector3f v1 = new Vector3f(5.f, 0.f, 0.f);
	private Vector3f v2 = new Vector3f(0.f, -5.f, 0.f);
	private Vector3f v3 = new Vector3f(5.f, -5.f, 0.f);

	private float[] squareVertexData = new float[]
	{ //
			0.f, 0.f, 0.f, //
			5.f, 0.f, 0.f, //
			0.f, -5.f, 0.f, //
			5.f, -5.f, 0.f, //
	};

	private int[] indexes = new int[]
	{ //
			0, 1, 2, //
			2, 1, 3, //
	};

	@Test
	public void testGetVertexAt()
	{
		assertEquals(v0, VectorMath.getVertexAt(squareVertexData, 0), 0.001f);
		assertEquals(v1, VectorMath.getVertexAt(squareVertexData, 1), 0.001f);
		assertEquals(v2, VectorMath.getVertexAt(squareVertexData, 2), 0.001f);
		assertEquals(v3, VectorMath.getVertexAt(squareVertexData, 3), 0.001f);
	}

	@Test
	public void testGetTriangles()
	{
		assertEquals(new Vector3f[]
		{ v0, v1, v2 }, VectorMath.getVector3f(squareVertexData, indexes, 0),
				0.0001f);
		assertEquals(new Vector3f[]
		{ v2, v1, v3 }, VectorMath.getVector3f(squareVertexData, indexes, 1),
				0.0001f);
	}

	public void assertEquals(Vector3f[] f, Vector3f[] s, float delta)
	{
		for( int i = 0; i < f.length; i++ )
			assertEquals(f[i], s[i], delta);
	}

	public void assertEquals(Vector3f f, Vector3f s, float delta)
	{
		Assert.assertEquals(f.x, s.x, delta);
		Assert.assertEquals(f.y, s.y, delta);
		Assert.assertEquals(f.z, s.z, delta);
	}

	@Test
	public void testVector3fFlatten()
	{
		List<Vector3f> list = Arrays.asList(new Vector3f(1.f, 2.f, 3.f),
				new Vector3f(4.f, 5.f, 6.f), new Vector3f(7.f, 8.f, 9.f));
		float f[] = VectorMath.flatten(list);
		Assert.assertArrayEquals(new float[]
		{ 1.f, 2.f, 3.f, 4.f, 5.f, 6.f, 7.f, 8.f, 9.f }, f, 0.0001f);
	}
}
