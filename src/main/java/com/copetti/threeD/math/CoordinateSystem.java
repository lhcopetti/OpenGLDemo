package com.copetti.threeD.math;

import org.joml.Vector2f;


public class CoordinateSystem
{

	public static PolarCoordinate toPolar(Vector2f f)
	{
		return null;
	}

	public static Vector2f toCartesian(PolarCoordinate polar)
	{
		float x = (float) (polar.getRadius()
				* Math.cos(Math.toRadians(polar.getDegreeAngle())));
		float y = (float) (polar.getRadius()
				* Math.sin(Math.toRadians(polar.getDegreeAngle())));
		return new Vector2f(x, y);
	}

}
