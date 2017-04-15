package com.copetti.threeD.opengl.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;


public class ShaderToolchainHelper
{

	protected static int compileShader(Shader shader)
	{
		int shaderId = glCreateShader(shader.getType().getOpenGLType());
		glShaderSource(shaderId, shader.getSource());
		glCompileShader(shaderId);

		if (glGetShaderi(shaderId,
				GL_COMPILE_STATUS) == GL_FALSE) { throw new RuntimeException(
						"Failed to compile Shader!"); }

		return shaderId;
	}

	public static int createShaderProgram(int... shaders)
	{
		int program = glCreateProgram();

		attachSources(program, shaders);
		linkProgram(program);
		cleanUpShaderObjects(program, shaders);

		return program;
	}

	private static void attachSources(int program, int... shaders)
	{
		for( int s : shaders )
			glAttachShader(program, s);
	}

	private static void linkProgram(int program)
	{
		glLinkProgram(program);

		if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE)
			throw new RuntimeException(
					"Failed to link shaders: " + glGetProgramInfoLog(program));
	}

	private static void cleanUpShaderObjects(int program, int... shaders)
	{
		for( int s : shaders )
		{
			glDetachShader(program, s);
			glDeleteShader(s);
		}
	}
}
