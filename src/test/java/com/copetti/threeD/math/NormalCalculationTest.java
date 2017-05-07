package com.copetti.threeD.math;

import org.joml.Vector3f;
import org.junit.Assert;
import org.junit.Test;

import com.copetti.threeD.math.normal.NormalVertexMap;


public class NormalCalculationTest
{

	@Test
	public void testNormalWithThreeVertex()
	{
		Vector3f p1 = new Vector3f().zero();
		Vector3f p2 = new Vector3f(0f, -5f, 0.f);
		Vector3f p3 = new Vector3f(10f, 0f, 0.f);

		Vector3f result = new Vector3f(0f, 0f, 50.f);
		assertEquals(result, NormalCalculation.calculateNormal(p1, p2, p3),
				0.001f);
	}

	public void assertEquals(Vector3f f, Vector3f s, float delta)
	{
		Assert.assertEquals(f.x, s.x, delta);
		Assert.assertEquals(f.y, s.y, delta);
		Assert.assertEquals(f.z, s.z, delta);
	}

	public Vector3f getVector3f(float[] data, int index)
	{
		return new Vector3f(data[index], data[index + 1], data[index + 2]);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThrowsExceptionIfArrayIsNotDivisibleByNine()
	{
		NormalCalculation.calculateNormal(new float[]
		{ 1.f, 1.f, 1.f, 2.f, 2.f });
	}

	@Test
	public void testCalculateNormalWithFloatArray()
	{
		float[] arg = NormalCalculation.calculateNormal(new float[]
		{ 1.f, 1.f, 1.f, 2.f, 2.f, 2.f, 3.f, 3.f, 3.f });

		float[] resultV3f = NormalCalculation.calculateNormal(arg);

		Vector3f expected = NormalCalculation.calculateNormal(
				getVector3f(arg, 0), getVector3f(arg, 3), getVector3f(arg, 6));
		Vector3f result = getVector3f(resultV3f, 0);
		assertEquals(expected, result, 0.1f);
	}

	@Test
	public void testCalculateNormalForCube()
	{
		float[] vertexData = new float[]
		{ //
				-0.5f, 0.5f, 0.5f, // 0
				0.5f, 0.5f, 0.5f, // 1
				-0.5f, -0.5f, 0.5f, // 2
				0.5f, -0.5f, 0.5f, // 3
				// Face afastada
				-0.5f, 0.5f, -0.5f, // 4
				0.5f, 0.5f, -0.5f, // 5
				-0.5f, -0.5f, -0.5f, // 6
				0.5f, -0.5f, -0.5f, // 7
				// Face superior
				-0.5f, 0.5f, 0.5f, // 8
				0.5f, 0.5f, 0.5f, // 9
				-0.5f, 0.5f, -0.5f, // 10
				0.5f, 0.5f, -0.5f, // 11
				// Face inferior
				-0.5f, -0.5f, 0.5f, // 12
				0.5f, -0.5f, 0.5f, // 13
				-0.5f, -0.5f, -0.5f, // 14
				0.5f, -0.5f, -0.5f, // 15
				// Face direita
				0.5f, -0.5f, 0.5f, // 16
				0.5f, 0.5f, 0.5f, // 17
				0.5f, -0.5f, -0.5f, // 18
				0.5f, 0.5f, -0.5f, // 19
				// Face esquerda
				-0.5f, -0.5f, 0.5f, // 20
				-0.5f, 0.5f, 0.5f, // 21
				-0.5f, -0.5f, -0.5f, // 22
				-0.5f, 0.5f, -0.5f // 23
		};

		int[] indexes = new int[]
		{
				// Face prÃ³xima
				0, 2, 3, 0, 3, 1,
				// Face afastada
				4, 7, 6, 4, 5, 7,
				// Face superior
				8, 11, 10, 8, 9, 11,
				// Face inferior
				12, 14, 15, 12, 15, 13,
				// Face direita
				16, 18, 19, 16, 19, 17,
				// Face esquerda
				20, 23, 22, 20, 21, 23 };

		float[] normalResult = new float[]
		{ 0.0f, 0.0f, 1.0f, //
				0.0f, 0.0f, 1.0f, //
				0.0f, 0.0f, 1.0f, //
				0.0f, 0.0f, 1.0f, //
				0.0f, 0.0f, -1.0f, //
				0.0f, 0.0f, -1.0f, //
				0.0f, 0.0f, -1.0f, //
				0.0f, 0.0f, -1.0f, //
				0.0f, 1.0f, 0.0f, //
				0.0f, 1.0f, 0.0f, //
				0.0f, 1.0f, 0.0f, //
				0.0f, 1.0f, 0.0f, //
				0.0f, -1.0f, 0.0f, //
				0.0f, -1.0f, 0.0f, //
				0.0f, -1.0f, 0.0f, //
				0.0f, -1.0f, 0.0f, //
				1.0f, 0.0f, 0.0f, //
				1.0f, 0.0f, 0.0f, //
				1.0f, 0.0f, 0.0f, //
				1.0f, 0.0f, 0.0f, //
				-1.0f, 0.0f, 0.0f, //
				-1.0f, 0.0f, 0.0f, //
				-1.0f, 0.0f, 0.0f, //
				-1.0f, 0.0f, 0.0f//
		};

		Assert.assertArrayEquals(normalResult,
				new NormalVertexMap().computeNormal(vertexData, indexes),
				0.001f);
	}

}
