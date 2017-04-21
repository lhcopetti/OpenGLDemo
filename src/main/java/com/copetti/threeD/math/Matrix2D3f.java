package com.copetti.threeD.math;

import java.util.function.Function;

import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector3f;


public class Matrix2D3f
{

	Vector3f[][] grid;

	public Matrix2D3f(int width, int height)
	{
		grid = new Vector3f[height][width];
	}

	public int height()
	{
		return grid.length;
	}

	public int width()
	{
		return grid[0].length;
	}

	public void set(int i, int j, Vector3f value)
	{
		grid[i][j] = value;
	}

	public Vector3f get(int i, int j)
	{
		return grid[i][j];
	}

	public float[] flatten()
	{
		float[] floats = new float[width() * height() * 3];

		for( int i = 0; i < height(); i++ )
			for( int j = 0; j < width(); j++ )
			{
				floats[3 * (width() * i + j)] = grid[i][j].x;
				floats[3 * (width() * i + j) + 1] = grid[i][j].y;
				floats[3 * (width() * i + j) + 2] = grid[i][j].z;
			}
		return floats;
	}

	public static Matrix2D newGridWithSideSize(float distance, int width,
			int height)
	{
		Matrix2D m = new Matrix2D(width, height);

		for( int i = 0; i < height; i++ )
			for( int j = 0; j < width; j++ )
				m.set(i, j, new Vector2f(distance * j, distance * i));
		return m;
	}

	public static Matrix2D3f newSphericalGrid(float radius, int azimuthDivisions, int polarDivisions)
	{
		Matrix2D3f m = new Matrix2D3f(polarDivisions, azimuthDivisions);
		
		float azimuthStep = 360 / azimuthDivisions;
		float polarStep = 360 / polarDivisions;

		for( int j = 0; j < polarDivisions; j++ )
		{
			for( int i = 0; i < azimuthDivisions; i++ )
			{
				Matrix3f azimuthMatrix = new Matrix3f()
						.rotateY((float) Math.toRadians(i * azimuthStep));
				PolarCoordinate p = new PolarCoordinate(radius, Angle.fromDegree(j * polarStep));
				Vector2f cartesian = CoordinateSystem.toCartesian(p);
				Vector3f v = new Vector3f(cartesian, 0.f);
				v = azimuthMatrix.transform(v);
				m.set(i, j, v);
			}
		}

		return m;
	}

	public Matrix2D3f fill(Vector3f v)
	{
		Vector3f copy = new Vector3f(v.x, v.y, v.z);
		for( int i = 0; i < height(); i++ )
			for( int j = 0; j < width(); j++ )
				grid[i][j] = copy;
		return this;
	}

	public Matrix2D3f transform(Function<Vector3f, Void> t)
	{
		for( int i = 0; i < height(); i++ )
			for( int j = 0; j < width(); j++ )
				t.apply(grid[i][j]);

		return this;
	}

	public static Matrix2D3f newSphericalGridCorrect(float radius,
			int azimuthDivisions, int polarDivisions)
	{
		int numAzimuth = 45;
		int numPolar = 45;
		
		
		Matrix2D3f m = new Matrix2D3f(numAzimuth + 1, numPolar + 1);
		
		float azimuthStep = 360 / numAzimuth;
		float polarStep = 180 / numPolar;

		for( int i = 0; i < m.height(); i++ )
		{
			for( int j = 0; j < m.width(); j++ )
			{
				float polarAngle = i * polarStep + 90;
				float azimuthAngle = j * azimuthStep;
				SphericalCoordinate sphericalCoord = new SphericalCoordinate(radius, Angle.fromDegree(polarAngle), Angle.fromDegree(azimuthAngle));
				m.set(i, j, CoordinateSystem.toCartesian(sphericalCoord));
			}
		}

		return m;
	}
}
