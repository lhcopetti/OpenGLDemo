package com.copetti.threeD.math.grid;

import org.joml.Vector3f;


public class Vector3fGridFlattener
{

	public float[] flatten(Grid2D<Vector3f> grid)
	{
		float[] f = new float[grid.width() * grid.height() * 3];
		int currentIndex = 0;

		for( Vector3f v : grid )
		{
			f[currentIndex++] = v.x;
			f[currentIndex++] = v.y;
			f[currentIndex++] = v.z;
		}

		return f;
	}

}
