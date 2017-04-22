package com.copetti.threeD.math.grid;

import java.util.Iterator;

import com.copetti.threeD.shapes.Grid2DCompliant;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode
public class Grid2D<T> implements Iterable<T>, Grid2DCompliant<T>
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

	public static <T> Grid2D<T> fromArray(T[] arr)
	{
		int size = (int) Math.sqrt(arr.length);
		if (size * size != arr.length)
			throw new IllegalArgumentException("This array will not generate a squared matrix");
		
		return fromArray(arr, size, size);
	}

	public static <T> Grid2D<T> fromArray(T[] arr, int width)
	{
		return fromArray(arr, width, arr.length / width);
	}

	private static <T> Grid2D<T> fromArray(T[] arr, int w, int h)
	{
		Grid2D<T> g = new Grid2D<>(w, h);
		for( int i = 0; i < h; i++ )
			for( int j = 0; j < w; j++ )
				g.set(i, j, arr[w * i + j]);
		return g;
	}
}
