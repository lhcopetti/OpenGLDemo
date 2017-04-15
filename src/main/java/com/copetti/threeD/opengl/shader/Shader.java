package com.copetti.threeD.opengl.shader;

import org.lwjgl.opengl.GL20;

import lombok.Getter;


public class Shader
{

	enum ShaderType
	{
		VERTEX_SHADER(GL20.GL_VERTEX_SHADER), //
		FRAGMENT_SHADER(GL20.GL_FRAGMENT_SHADER);

		private ShaderType(int openGlShaderType)
		{
			this.openGLType = openGlShaderType;
		}

		private @Getter int openGLType;
	}

	private @Getter ShaderType type;
	private @Getter String source;

	public Shader(ShaderType shaderType, String source)
	{
		if (source == null)
			throw new NullPointerException("The shader source is null");
		if (source.isEmpty()) throw new IllegalArgumentException(
				"The shader source content is empty");

		this.type = shaderType;
		this.source = source;
	}

}
