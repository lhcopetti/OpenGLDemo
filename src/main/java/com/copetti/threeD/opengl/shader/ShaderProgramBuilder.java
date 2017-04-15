package com.copetti.threeD.opengl.shader;

import com.copetti.threeD.opengl.shader.Shader.ShaderType;


public class ShaderProgramBuilder
{

	private Shader vertexShader;
	private Shader fragmentShader;

	public static ShaderProgramBuilder newBuilder()
	{
		return new ShaderProgramBuilder();
	}

	public ShaderProgram build()
	{
		if (vertexShader == null && fragmentShader == null)
			throw new IllegalStateException("No shader source is attached");

		if (vertexShader == null)
			throw new IllegalStateException("Vertex Shader is missing");
		
		if (fragmentShader == null)
			throw new IllegalStateException("Fragment Shader is missing");

		return doBuild();
	}
	
	public ShaderProgramBuilder attachVertexShader(String shaderSource)
	{
		vertexShader = new Shader(ShaderType.VERTEX_SHADER, shaderSource);
		return this;
	}

	public ShaderProgramBuilder attachFragmentShader(String shaderSource)
	{
		fragmentShader = new Shader(ShaderType.FRAGMENT_SHADER, shaderSource);
		return this;
	}

	private ShaderProgram doBuild()
	{
		int fragmentShaderId = ShaderToolchainHelper
				.compileShader(vertexShader);
		int vertexShaderId = ShaderToolchainHelper
				.compileShader(fragmentShader);

		int shaderProgramId = ShaderToolchainHelper
				.createShaderProgram(vertexShaderId, fragmentShaderId);
		return new ShaderProgram(shaderProgramId);
	}

}
