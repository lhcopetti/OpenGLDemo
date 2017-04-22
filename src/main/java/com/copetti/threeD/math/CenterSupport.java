package com.copetti.threeD.math;

import java.util.Arrays;

import org.joml.Vector2f;

import com.copetti.threeD.math.grid.Grid2D;


public class CenterSupport
{

	public static Grid2D<Vector2f> center(Grid2D<Vector2f> grid)
	{
		Vector2f mid = getMidVector(grid);
		mid.set(-mid.x, -mid.y);
		for( Vector2f v : grid )
			v.add(mid);
		return grid;
	}

	protected static float midPoint(float low, float high)
	{
		return (low + high) / 2.f;
	}

	protected static Vector2f getMidVector(Grid2D<Vector2f> grid)
	{
		Vector2f lowest = new Vector2f(grid.iterator().next());
		Vector2f highest = new Vector2f(lowest);
		for( Vector2f v : grid )
		{
			lowest.set(Math.min(lowest.x, v.x), Math.min(lowest.y, v.y));
			highest.set(Math.max(highest.x, v.x), Math.max(highest.y, v.y));
		}
		return new Vector2f(midPoint(lowest.x, highest.x),
				midPoint(lowest.y, highest.y));
	}
}
