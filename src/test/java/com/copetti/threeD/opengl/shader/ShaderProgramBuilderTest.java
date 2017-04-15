package com.copetti.threeD.opengl.shader;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class ShaderProgramBuilderTest
{

	@Rule
	public ExpectedException expected = ExpectedException.none();
	
	@Test
	public void buildWithoutAnyShaderAttached()
	{
		expected.expect(IllegalStateException.class);
		expected.expectMessage("No shader source is attached");
		ShaderProgramBuilder.newBuilder().build();
	}
	
	@Test
	public void buildWithMissingVertexShader()
	{
		expected.expect(IllegalStateException.class);
		expected.expectMessage("Vertex Shader is missing");
		ShaderProgramBuilder.newBuilder().build();
	}
	
}
