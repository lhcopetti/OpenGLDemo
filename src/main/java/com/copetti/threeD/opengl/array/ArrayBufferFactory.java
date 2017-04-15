package com.copetti.threeD.opengl.array;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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

		ArrayBuffer array = new ArrayBuffer(bufferId,
				data.length / elementSize);
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

	public static IndexBuffer newIndexBuffer(int[] data)
	{
		if (data == null) throw new NullPointerException("data is null");
		if (data.length == 0)
			throw new IllegalArgumentException("The data array is empty");

		return createNewIndexBuffer(data);
	}

	private static IndexBuffer createNewIndexBuffer(int[] data)
	{
		IndexBuffer buffer = new IndexBuffer(glGenBuffers(), data.length);
		buffer.bind();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, wrap(data), GL_STATIC_DRAW);
		buffer.unbind();

		return buffer;
	}

	private static IntBuffer wrap(int[] data)
	{
		IntBuffer buff = BufferUtils.createIntBuffer(data.length);
		buff.put(data).flip();
		return buff;
	}
}
