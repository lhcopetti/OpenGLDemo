package com.copetti.threeD.opengl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.lang.model.util.Elements;

import org.lwjgl.BufferUtils;


public class ArrayBufferFactory
{

	public static ArrayBuffer newArrayBuffer(int elementSize, float[] data)
	{
		if (elementSize < 1) throw new IllegalArgumentException(
				"Invalid elementSize: " + elementSize);

		if (elementSize > data.length)
			throw new IllegalArgumentException("Element size (" + elementSize
					+ ") is greater than array count (" + data.length + ")");

		if (data.length % elementSize != 0)
			throw new IllegalArgumentException("Array count (" + data.length
					+ ") is not divisible by ElementSize (" + elementSize
					+ ")");

		return createNewArrayBuffer(elementSize, data);
	}

	private static ArrayBuffer createNewArrayBuffer(int elementSize,
			float[] data)
	{
		int bufferId = glGenBuffers();
		
		ArrayBuffer array = new ArrayBuffer(bufferId, data.length / elementSize);
		array.bind();
		glBufferData(GL_ARRAY_BUFFER, wrap(data), GL_STATIC_DRAW);
		array.unbind();
		return array;
	}

	private static FloatBuffer wrap(float[] data)
	{
		FloatBuffer buf = BufferUtils.createFloatBuffer(data.length);
		buf.put(data).flip();
		return buf;
	}
}
