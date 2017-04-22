package com.copetti.threeD.math;

import org.joml.Vector3f;


public class Matrix2D3f
{

	Vector3f[][] grid;

	public Matrix2D3f(int width, int height)
	{
		grid = new Vector3f[height][width];
	}

	public int height()
	{
		return grid.length;
	}

	public int width()
	{
		return grid[0].length;
	}

	public void set(int i, int j, Vector3f value)
	{
		grid[i][j] = value;
	}

	public Vector3f get(int i, int j)
	{
		return grid[i][j];
	}

	public float[] flatten()
	{
		float[] floats = new float[width() * height() * 3];

		for( int i = 0; i < height(); i++ )
			for( int j = 0; j < width(); j++ )
			{
				floats[3 * (width() * i + j)] = grid[i][j].x;
				floats[3 * (width() * i + j) + 1] = grid[i][j].y;
				floats[3 * (width() * i + j) + 2] = grid[i][j].z;
			}
		return floats;
	}
}
