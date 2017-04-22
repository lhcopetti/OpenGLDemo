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
		{ 0, 2, 1, 1, 2, 3 }, indexBuffer);
	}

	@Test
	public void testConnectMatrixThreeByThree()
	{
		int[] indexBuffer = IndexUtils.connectAsGrid(3, 3);
		assertArrayEquals(new int[]
		{ 0, 3, 1, 1, 3, 4, //
				1, 4, 2, 2, 4, 5, //
				3, 6, 4, 4, 6, 7, //
				4, 7, 5, 5, 7, 8 //
		}, indexBuffer);
	}

}
