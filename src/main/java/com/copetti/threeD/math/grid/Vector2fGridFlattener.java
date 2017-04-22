package com.copetti.threeD.math.grid;

import org.joml.Vector2f;


public class Vector2fGridFlattener
{

	public float[] flatten(Grid2D<Vector2f> grid)
	{
		float[] f = new float[grid.width() * grid.height() * 2];
		int currentIndex = 0;

		for( Vector2f v : grid )
		{
			f[currentIndex++] = v.x;
			f[currentIndex++] = v.y;
		}

		return f;
	}
}
