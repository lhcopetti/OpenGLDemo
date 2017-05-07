package com.copetti.threeD.math.normal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;

import com.copetti.threeD.math.NormalCalculation;
import com.copetti.threeD.math.VectorMath;

import lombok.AllArgsConstructor;


public class NormalVertexMap
{

	@AllArgsConstructor
	private class VertexIndex
	{

		protected Vector3f v;
		protected Integer i;
	}

	Map<Integer, List<Vector3f>> map;

	public NormalVertexMap()
	{
		map = new HashMap<>();
	}

	public float[] computeNormal(float[] vertexData, int[] indexes)
	{
		return computeNormal(toList(vertexData), indexes);
	}

	public float[] computeNormal(List<Vector3f> vertex, int[] indexes)
	{
		for( int i = 0; i < indexes.length; i += 3 )
		{
			VertexIndex v0 = getVertexIndex(vertex, indexes, i);
			VertexIndex v1 = getVertexIndex(vertex, indexes, i + 1);
			VertexIndex v2 = getVertexIndex(vertex, indexes, i + 2);
			computeNormalsAndAddToMap(v0, v1, v2);
		}

		return computeNormalForEachVertex(vertex, indexes);
	}

	private void computeNormalsAndAddToMap(VertexIndex v0, VertexIndex v1,
			VertexIndex v2)
	{
		Vector3f normal = NormalCalculation.calculateNormal(v0.v, v1.v, v2.v);
		put(v0.i, normal);
		put(v1.i, normal);
		put(v2.i, normal);
	}

	private VertexIndex getVertexIndex(List<Vector3f> vertex, int[] indexes,
			int i)
	{
		int index = indexes[i];
		return new VertexIndex(vertex.get(index), index);
	}

	private void put(Integer i, Vector3f v)
	{
		List<Vector3f> list = map.getOrDefault(i, new ArrayList<>());
		list.add(v);
		map.put(i, list);
	}

	private float[] computeNormalForEachVertex(List<Vector3f> vertex,
			int[] indexes)
	{
		List<Vector3f> normals = new ArrayList<Vector3f>();

		for( int i = 0; i < vertex.size(); i++ )
			normals.add(average(map.get(i)));

		return VectorMath.flatten(normals);
	}

	private Vector3f average(List<Vector3f> list)
	{
		Vector3f v = new Vector3f().zero();
		list.stream().forEach(x -> v.add(x));
		return v.normalize();
	}

	public float[] computeNormal(float[] vertexData)
	{
		List<Vector3f> list = toList(vertexData);
		List<Vector3f> normal = new ArrayList<>();

		for( int i = 0; i < list.size(); i += 3 )
		{
			Vector3f normalV = NormalCalculation.calculateNormal(list.get(i),
					list.get(i + 1), list.get(i + 2));

			normal.add(normalV);
			normal.add(normalV);
			normal.add(normalV);
		}

		return VectorMath.flatten(normal);
	}

	public List<Vector3f> toList(float[] data)
	{
		List<Vector3f> list = new ArrayList<>(data.length / 3);
		for( int i = 0; i < data.length; i += 3 )
		{
			Vector3f v = new Vector3f( //
					data[i], //
					data[i + 1], //
					data[i + 2]);
			list.add(v);
		}
		return list;
	}
}
