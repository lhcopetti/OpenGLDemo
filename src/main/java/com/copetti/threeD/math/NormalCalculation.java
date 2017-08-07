package com.copetti.threeD.math;

import java.util.List;

import org.joml.Vector3f;

import com.copetti.threeD.math.normal.NormalVertexMap;


public class NormalCalculation
{

	public static float[] calculateNormal(float[] vertexData, int[] indexes)
	{
		return new NormalVertexMap().computeNormal(vertexData);
	}

	public static Vector3f calculateNormal(List<Vector3f> vector, int[] indexes,
			int index)
	{
		Vector3f p1 = vector.get(indexes[index]);
		Vector3f p2 = vector.get(indexes[index + 1]);
		Vector3f p3 = vector.get(indexes[index + 2]);
		return calculateNormal(p1, p2, p3);
	}

	public static Vector3f calculateNormal(Vector3f p1, Vector3f p2,
			Vector3f p3)
	{
		Vector3f f = new Vector3f(p2).sub(p1);
		Vector3f s = new Vector3f(p3).sub(p1);
		return f.cross(s);
	}

	public static float[] calculateNormal(float[] vertexData)
	{
		if (vertexData.length % 9 != 0) throw new IllegalArgumentException(
				"The vertexData length must be divisible by nine. Length: "
						+ vertexData.length);

		return new NormalVertexMap().computeNormal(vertexData);
	}

	public static Vector3f asVector3f(float[] data, int index)
	{
		return new Vector3f(data[index], data[index + 1], data[index + 2]);
	}

	public static void push(List<Float> list, Vector3f v)
	{
		list.add(v.x);
		list.add(v.y);
		list.add(v.z);
	}

	public static float[] toArray(List<Float> f)
	{
		float[] array = new float[f.size()];

		for( int i = 0; i < f.size(); i++ )
			array[i] = f.get(i);

		return array;
	}

}
