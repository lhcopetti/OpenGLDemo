package com.copetti.threeD.opengl.vertexarray;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.lwjgl.opengl.GL30.*;


@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class VertexArrayObject
{

	private @Getter int vaoId;

	public void bind()
	{
		glBindVertexArray(vaoId);
	}

	public void unbind()
	{
		glBindVertexArray(0);
	}
}
