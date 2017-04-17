package com.copetti.threeD.math;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;


public class IndexUtilsTest
{

	@Test
	public void testConnectMatrixTwoByTwo()
	{
		int[] indexBuffer = IndexUtils.connectAsGrid(2, 2);
		assertArrayEquals(new int[]
		{ 0, 1, 2, 1, 2, 3 }, indexBuffer);
	}

	@Test
	public void testConnectMatrixThreeByThree()
	{
		int[] indexBuffer = IndexUtils.connectAsGrid(3, 3);
		assertArrayEquals(new int[]
		{ 0, 1, 3, 1, 3, 4, //
				1, 2, 4, 2, 4, 5, //
				3, 4, 6, 4, 6, 7, //
				4, 5, 7, 5, 7, 8 //
		}, indexBuffer);
	}

}
