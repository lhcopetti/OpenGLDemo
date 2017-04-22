package com.copetti.threeD.shapes;

import com.copetti.threeD.math.grid.Grid2D;


public class Grid2DCompliantBuilder<T>
{

	public static <T> Grid2D<T> build(Grid2DCompliant<T> value)
	{
		Grid2D<T> g = new Grid2D<>(value.width(), value.height());

		for( int i = 0; i < value.height(); i++ )
			for( int j = 0; j < value.width(); j++ )
				g.set(i, j, value.get(i, j));
		return g;
	}
}
