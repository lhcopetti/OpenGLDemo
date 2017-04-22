package com.copetti.threeD.math;

import java.util.function.Function;

import org.joml.Vector2f;


public class Matrix2D
{

	Vector2f[][] grid;

	public Matrix2D(int width, int height)
	{
		grid = new Vector2f[height][width];
	}

	public int width()
	{
		return grid[0].length;
	}

	public int height()
	{
		return grid.length;
	}

	public void set(int i, int j, Vector2f value)
	{
		grid[i][j] = value;
	}

	public Vector2f get(int i, int j)
	{
		return grid[i][j];
	}

	public float[] flatten()
	{
		float[] floats = new float[width() * height() * 2];

		for( int i = 0; i < height(); i++ )
			for( int j = 0; j < width(); j++ )
			{
				floats[2 * (width() * i + j)] = grid[i][j].x;
				floats[2 * (width() * i + j) + 1] = grid[i][j].y;
			}
		return floats;
	}

	public Matrix2D transform(Function<Vector2f, Void> t)
	{
		for( int i = 0; i < height(); i++ )
			for( int j = 0; j < width(); j++ )
				t.apply(grid[i][j]);

		return this;
	}
}