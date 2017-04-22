package com.copetti.threeD.shapes;

import org.joml.Vector3f;

import com.copetti.threeD.math.Angle;
import com.copetti.threeD.math.CoordinateSystem;
import com.copetti.threeD.math.SphericalCoordinate;
import com.copetti.threeD.math.grid.Grid2D;


public class SphericalMeshVertices
{

	public static Grid2D<Vector3f> newSphericalGrid(int azimuthDivisions,
			int polarDivisions)
	{
		Grid2D<Vector3f> m = new Grid2D<>(azimuthDivisions + 1,
				polarDivisions + 1);

		float azimuthStep = 360 / azimuthDivisions;
		float polarStep = 180 / polarDivisions;

		for( int i = 0; i < m.height(); i++ )
		{
			for( int j = 0; j < m.width(); j++ )
			{
				float polarAngle = i * polarStep + 90;
				float azimuthAngle = j * azimuthStep;
				SphericalCoordinate sphericalCoord = new SphericalCoordinate(
						1.f, Angle.fromDegree(polarAngle),
						Angle.fromDegree(azimuthAngle));
				m.set(i, j, CoordinateSystem.toCartesian(sphericalCoord));
			}
		}

		return m;
	}
}
