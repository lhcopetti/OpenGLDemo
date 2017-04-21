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

	public static Matrix2D newGridWithSideSize(float distance, int width,
			int height)
	{
		Matrix2D m = new Matrix2D(width, height);

		for( int i = 0; i < height; i++ )
			for( int j = 0; j < width; j++ )
				m.set(i, j, new Vector2f(distance * j, -distance * i));
		return m;
	}

	public static Matrix2D newSphericalGrid(float radius, int angleDivisions)
	{
		Matrix2D m = new Matrix2D(angleDivisions, angleDivisions);
		float angle = 360 / angleDivisions;

		for( int i = 0; i < angleDivisions; i++ )
			for( int j = 0; j < angleDivisions; j++ )
			{
//				m.set(i, j, Cnew PolarCoordinate(radius, angle));
			}

		return m;
	}

	public Matrix2D fill(Vector2f vector2f)
	{
		Vector2f copy = new Vector2f(vector2f.x, vector2f.y);
		for( int i = 0; i < height(); i++ )
			for( int j = 0; j < width(); j++ )
				grid[i][j] = copy;
		return this;
	}

	public Matrix2D transform(Function<Vector2f, Void> t)
	{
		for( int i = 0; i < height(); i++ )
			for( int j = 0; j < width(); j++ )
				t.apply(grid[i][j]);

		return this;
	}
}