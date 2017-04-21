package com.copetti.threeD.math;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SphericalCoordinate
{
	private @Getter float radius;
	private @Getter Angle polarAngle;
	private @Getter Angle azimuthAngle;
}
