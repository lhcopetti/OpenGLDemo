package com.copetti.threeD.opengl.mesh;

import java.util.HashMap;
import java.util.Map;

import com.copetti.threeD.opengl.array.ArrayBuffer;
import com.copetti.threeD.opengl.array.IndexBuffer;
import com.copetti.threeD.opengl.shader.ShaderProgram;
import com.copetti.threeD.opengl.uniform.Uniform;
import com.copetti.threeD.opengl.vertexarray.VertexArrayObject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;


@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Mesh
{

	private VertexArrayObject vao;
	private ShaderProgram shaderProgram;

	private IndexBuffer indexBuffer;

	private Map<String, ArrayBuffer> attributes = new HashMap<>();
	private Map<String, Uniform> uniforms = new HashMap<>();

	public void draw()
	{
		vao.bind();
		shaderProgram.bind();

		setAttributes();
		setUniforms();

		if (null == indexBuffer)
			attributes.values().iterator().next().draw();
		else
			indexBuffer.draw();

		clearUniforms();
		clearAttributes();

		shaderProgram.unbind();
		vao.unbind();
	}

	private void setAttributes()
	{
		attributes.entrySet().stream().forEach(entry -> {
			ArrayBuffer buffer = entry.getValue();
			buffer.bind();
			shaderProgram.setAttribute(entry.getKey(), buffer);
			buffer.unbind();
		});
	}

	private void setUniforms()
	{
		uniforms.entrySet().stream().forEach(entry -> {
			Uniform uniform = entry.getValue();
			shaderProgram.setUniform(entry.getKey(), uniform);
		});
	}

	private void clearUniforms()
	{
		uniforms.entrySet().stream().forEach(entry -> {
			shaderProgram.clearUniform(entry.getKey());
		});
	}

	private void clearAttributes()
	{
		attributes.entrySet().stream().forEach(entry -> {
			shaderProgram.clearAttribute(entry.getKey());
		});
	}

	public void setUniform(String key, Object uniform)
	{
		uniforms.put(key, new Uniform(uniform));
	}
}
