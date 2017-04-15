package com.copetti.threeD.opengl.shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import com.copetti.threeD.opengl.array.ArrayBuffer;

import lombok.Getter;


public class ShaderProgram
{

	private @Getter int shaderId;

	public ShaderProgram(int shaderProgramId)
	{
		this.shaderId = shaderProgramId;
	}

	public void bind()
	{
		glUseProgram(shaderId);
	}

	public void unbind()
	{
		glUseProgram(0);
	}

	public void setAttribute(String attributeName, ArrayBuffer array)
	{
		if (array == null) throw new IllegalArgumentException(
				"Array is null when setting the attribute: " + attributeName);

		int positionLocation = glGetAttribLocation(shaderId, attributeName);

		if (positionLocation == GL_INVALID_OPERATION)
			throw new IllegalArgumentException(
					"Failed to locate attribute: " + attributeName);

		glEnableVertexAttribArray(positionLocation);
		glVertexAttribPointer(positionLocation, array.getElementsSize(),
				GL_FLOAT, false, 0, 0);
	}

	public void clearAttribute(String attributeName)
	{
		int positionLocation = glGetAttribLocation(shaderId, attributeName);

		if (positionLocation == GL_INVALID_OPERATION)
			throw new IllegalArgumentException(
					"Failed to locate attribute: " + attributeName);

		glDisableVertexAttribArray(positionLocation);
	}

	public void setUniform(String uniformName, Matrix4f matrix4f)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		matrix4f.get(buffer);

		int uniformId = glGetUniformLocation(shaderId, uniformName);
		glEnableVertexAttribArray(uniformId);
		glUniformMatrix4fv(uniformId, false, buffer);
	}

	public void clearUniform(String uniformName)
	{
		int uniformId = glGetUniformLocation(shaderId, uniformName);
		glDisableVertexAttribArray(uniformId);
	}
}
