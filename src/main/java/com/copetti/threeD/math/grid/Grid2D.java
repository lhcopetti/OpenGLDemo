package com.copetti.threeD.math.grid;

import java.util.Iterator;

import org.joml.Vector2f;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Grid2D<T> implements Iterable<T>
{

	Object[][] grid;

	public Grid2D(int width, int height)
	{
		grid = new Object[height][width];
	}

	public int width()
	{
		return grid[0].length;
	}

	public int height()
	{
		return grid.length;
	}

	public void set(int i, int j, T value)
	{
		grid[i][j] = value;
	}

	@SuppressWarnings("unchecked")
	public T get(int i, int j)
	{
		return (T) grid[i][j];
	}

	// public float[] flatten()
	// {
	// float[] floats = new float[width() * height() * 2];
	//
	// for( int i = 0; i < height(); i++ )
	// for( int j = 0; j < width(); j++ )
	// {
	// floats[2 * (width() * i + j)] = grid[i][j].x;
	// floats[2 * (width() * i + j) + 1] = grid[i][j].y;
	// }
	// return floats;
	// }

	// public Matrix2D fill(Vector2f vector2f)
	// {
	// Vector2f copy = new Vector2f(vector2f.x, vector2f.y);
	// for( int i = 0; i < height(); i++ )
	// for( int j = 0; j < width(); j++ )
	// grid[i][j] = copy;
	// return this;
	// }

	// public Matrix2D transform(Function<Vector2f, Void> t)
	// {
	// for( int i = 0; i < height(); i++ )
	// for( int j = 0; j < width(); j++ )
	// t.apply(grid[i][j]);
	//
	// return this;
	// }

	// public float[] flatten()
	// {
	//
	// }

	@Override
	public Iterator<T> iterator()
	{
		return new Grid2DIterator<T>(this);
	}
	
	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		for (T v : this)
			str.append(v.toString() + " | ");
		return str.toString();
	}
}
