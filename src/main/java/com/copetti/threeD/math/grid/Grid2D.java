package com.copetti.threeD.math.grid;

import java.util.Iterator;

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

	@Override
	public Iterator<T> iterator()
	{
		return new Grid2DIterator<T>(this);
	}

	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		for( T v : this )
			str.append(v.toString() + " | ");
		return str.toString();
	}
}
