package com.copetti.threeD.math;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Pentagon
{

	private Pentagon()
	{
	}

//	public static float[] createPentagon(Vector2f v, float size)
//	{
//		return new float[]
//		{ 0.0f, 0.5f, //
//				-.5f, -.5f, //
//				.5f, -.5f };
//	}

	public static float[] createPentagon(Vector2f v, float circleRadius)
	{
		List<Vector2f> list = new ArrayList<>();
		Vector2f first = new Vector2f(0.f, circleRadius);
		list.add(first);

		Matrix3f rotation = new Matrix3f().rotateZ((float) Math.toRadians(72));
		
		for (int i = 0; i < 4; i++){
			Vector3f transform = rotation.transform(new Vector3f(first, 0.f));
			Vector2f newVertex = new Vector2f(transform.x, transform.y); 
			list.add(newVertex);
			first = newVertex;
		}

		int[] index = new int[] { 0, 1, 4, //
				1, 2, 4, //
				2, 3, 4};
		
		List<Float> flots = new ArrayList<>();
		
		for (int i : index) {
			flots.add(list.get(i).x);
			flots.add(list.get(i).y);
		}
		
		float[] arrayFloats = new float[flots.size()];
		for (int i = 0; i < flots.size(); i++)
			arrayFloats[i] = flots.get(i);
		
		return arrayFloats;
	}
}
