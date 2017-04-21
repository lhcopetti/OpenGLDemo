package com.copetti.threeD.math;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Angle
{

	private float angle;

	public static Angle fromDegree(float degrees)
	{
		return new Angle(degrees);
	}

	public static Angle fromRadians(float radians)
	{
		return new Angle((float) Math.toDegrees(radians));
	}

	public float asDegree()
	{
		return angle;
	}

	public float asRadians()
	{
		return (float) Math.toRadians(angle);
	}
}
