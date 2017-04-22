package com.copetti.threeD.math.grid;

import java.util.Iterator;


public class Grid2DIterator<T> implements Iterator<T>
{

	private Grid2D<T> grid;
	private int currentIndex;

	public Grid2DIterator(Grid2D<T> grid2d)
	{
		this.grid = grid2d;
		this.currentIndex = 0;
	}

	@Override
	public boolean hasNext()
	{
		return currentIndex < grid.width() * grid.height();
	}

	@Override
	public T next()
	{
		T value = grid.get(currentIndex / grid.width(), currentIndex % grid.width());
		currentIndex++;
		return value;
	}

}
