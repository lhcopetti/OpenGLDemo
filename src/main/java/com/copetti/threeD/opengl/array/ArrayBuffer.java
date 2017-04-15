package com.copetti.threeD.opengl.array;

import lombok.Getter;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class ArrayBuffer
{
	
	private @Getter int bufferId;
	private @Getter int vertexCount;

	protected ArrayBuffer(int bufferId, int vertexCount) 
	{
		this.bufferId = bufferId;
		this.vertexCount = vertexCount;
	}
	
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
