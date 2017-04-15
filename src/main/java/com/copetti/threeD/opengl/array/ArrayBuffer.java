package com.copetti.threeD.opengl.array;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ArrayBuffer
{

	private @Getter int bufferId;
	private @Getter int vertexCount;
	private @Getter int elementsSize;

	public ArrayBuffer bind()
	{
		glBindBuffer(GL_ARRAY_BUFFER, bufferId);
		return this;
	}

	public ArrayBuffer unbind()
	{
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		return this;
	}

	public void draw()
	{
		glDrawArrays(GL_TRIANGLES, 0, vertexCount);
	}
}
