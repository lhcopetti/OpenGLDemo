package com.copetti.threeD.opengl.array;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import lombok.Getter;


public class IndexBuffer
{

	private @Getter int indexId;
	private @Getter int count;

	protected IndexBuffer(int indexId, int count)
	{
		this.indexId = indexId;
		this.count = count;
	}

	public void bind()
	{
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexId);
	}

	public void unbind()
	{
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexId);
	}

	public void draw()
	{
		bind();
		glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
		unbind();
	}

}
