package com.copetti.threeD.math;

import java.util.function.BiFunction;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.copetti.threeD.math.grid.Grid2D;


public class CenterSupport
{

	public static Grid2D<Vector2f> center(Grid2D<Vector2f> grid)
	{
		Vector2f mid = getMidVector(grid).mul(-1f);
		for( Vector2f v : grid )
			v.add(mid);
		return grid;
	}

	public static Grid2D<Vector3f> centerVector3f(Grid2D<Vector3f> grid)
	{
		Vector3f mid = getMidVector3D(grid).mul(-1);
		for( Vector3f v : grid )
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
			lowest = applyFunc(lowest, v, Math::min);
			highest = applyFunc(highest, v, Math::max);
		}
		return applyFunc(lowest, highest, CenterSupport::midPoint);
	}

	private static Vector2f applyFunc(Vector2f first, Vector2f second,
			BiFunction<Float, Float, Float> func)
	{
		return new Vector2f(func.apply(first.x, second.x),
				func.apply(first.y, second.y));
	}

	protected static Vector3f getMidVector3D(Grid2D<Vector3f> grid)
	{
		Vector3f lowest = new Vector3f(grid.iterator().next());
		Vector3f highest = new Vector3f(lowest);
		for( Vector3f v : grid )
		{
			lowest = applyFunc3D(lowest, v, Math::min);
			highest = applyFunc3D(highest, v, Math::max);
		}
		return applyFunc3D(lowest, highest, CenterSupport::midPoint);
	}

	private static Vector3f applyFunc3D(Vector3f first, Vector3f second,
			BiFunction<Float, Float, Float> func)
	{
		return new Vector3f(func.apply(first.x, second.x),
				func.apply(first.y, second.y), func.apply(first.z, second.z));
	}

}
