package com.copetti.threeD.math;

import java.util.List;

import org.joml.Vector3f;


public class VectorMath
{

	public static Vector3f[] getVector3f(float[] vertexData, int[] indexData,
			int triangleIndex)
	{
		Vector3f[] result = new Vector3f[3];
		int index = triangleIndex * 3;

		result[0] = getVertexAt(vertexData, indexData[index]);
		result[1] = getVertexAt(vertexData, indexData[index + 1]);
		result[2] = getVertexAt(vertexData, indexData[index + 2]);
		return result;
	}

	public static Vector3f getVertexAt(float[] vertexData, int vertexIndex)
	{
		int index = vertexIndex * 3;
		float x = vertexData[index];
		float y = vertexData[index + 1];
		float z = vertexData[index + 2];
		return new Vector3f(x, y, z);
	}

	public static float[] flatten(List<Vector3f> list)
	{
		float[] f = new float[list.size() * 3];
		for( int i = 0; i < f.length; i += 3 )
		{
			Vector3f v = list.get(i / 3);
			f[i] = v.x;
			f[i + 1] = v.y;
			f[i + 2] = v.z;
		}

		return f;
	}

}
