package com.copetti.threeD.shapes;

import org.joml.Vector2f;

import com.copetti.threeD.math.CenterSupport;
import com.copetti.threeD.math.grid.Grid2D;


public class RectangleMeshVertices
{

	public static Grid2D<Vector2f> newGrid(int width, int height)
	{
		Grid2D<Vector2f> g = new Grid2D<>(width, height);

		for( int i = 0; i < g.height(); i++ )
			for( int j = 0; j < g.width(); j++ )
				g.set(i, j, new Vector2f(j, -i));

		return CenterSupport.center(g);
	}

}
