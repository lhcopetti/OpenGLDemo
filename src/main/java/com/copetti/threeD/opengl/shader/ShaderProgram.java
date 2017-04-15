package com.copetti.threeD.opengl.shader;

import static org.lwjgl.opengl.GL20.glUseProgram;

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
}
