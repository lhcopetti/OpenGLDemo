package com.copetti.threeD.opengl.shader;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.copetti.threeD.opengl.shader.Shader.ShaderType;


public class ShaderTest
{

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Test
	public void nullShaderSourceThrows()
	{
		expected.expect(NullPointerException.class);
		expected.expectMessage("The shader source is null");
		new Shader(ShaderType.FRAGMENT_SHADER, null);
	}

	@Test
	public void emptyShaderSourceThrows()
	{
		expected.expect(IllegalArgumentException.class);
		expected.expectMessage("The shader source content is empty");
		new Shader(ShaderType.FRAGMENT_SHADER, "");
	}
}
