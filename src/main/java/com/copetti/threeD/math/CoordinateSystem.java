package com.copetti.threeD.math;

import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector3f;


public class CoordinateSystem
{

	public static PolarCoordinate toPolar(Vector2f f)
	{
		return null;
	}

	public static Vector2f toCartesian(PolarCoordinate polar)
	{
		float x = (float) (polar.getRadius()
				* Math.cos(polar.getAngle().asRadians()));
		float y = (float) (polar.getRadius()
				* Math.sin(polar.getAngle().asRadians()));
		return new Vector2f(x, y);
	}
	
	public static Vector3f toCartesian(SphericalCoordinate spherical)
	{
		PolarCoordinate polar = new PolarCoordinate(spherical.getRadius(), spherical.getPolarAngle());
		Vector2f polarCartesian = toCartesian(polar);
		Vector3f vec = new Vector3f(polarCartesian, 0.0f);
		Matrix3f m = new Matrix3f().rotateY(spherical.getAzimuthAngle().asRadians());
		return m.transform(vec);
	}

}
