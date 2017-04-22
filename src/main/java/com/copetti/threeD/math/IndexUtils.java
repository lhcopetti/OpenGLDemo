package com.copetti.threeD.math;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import com.copetti.threeD.math.grid.Grid2D;


public class IndexUtils
{

	public static int[] connectAsGrid(Grid2D<Vector3f> matrix)
	{
		return connectAsGrid(matrix.width(), matrix.height());
	}
	
	public static int[] connectAsGrid(int width, int height)
	{
		List<Integer> indexes = new ArrayList<>();
		for( int i = 0; i < height -1; i++ )
		{
			for( int j = 0; j < width - 1; j++ )
			{
				indexes.add(ijToArrayIndex(width, i, j));
				indexes.add(ijToArrayIndex(width, i + 1, j)); // Down
				indexes.add(ijToArrayIndex(width, i, j + 1)); // Right

				indexes.add(ijToArrayIndex(width, i, j + 1)); // Right
				indexes.add(ijToArrayIndex(width, i + 1, j)); // Down
				indexes.add(ijToArrayIndex(width, i + 1, j + 1)); // Opposite
			}
		}

		int[] indexArray = new int[indexes.size()];
		for (int i = 0; i < indexes.size(); i++)
			indexArray[i] = indexes.get(i);
		return indexArray;
	}

	private static int ijToArrayIndex(int width, int i, int j)
	{
		return i * width + j;
	}

}
