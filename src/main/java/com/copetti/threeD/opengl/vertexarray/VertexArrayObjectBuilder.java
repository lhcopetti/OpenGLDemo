package com.copetti.threeD.opengl.vertexarray;

import static org.lwjgl.opengl.GL30.*;


public class VertexArrayObjectBuilder
{

	public static VertexArrayObject newVertexArrayObject()
	{
		return new VertexArrayObject(glGenVertexArrays());
	}
}
