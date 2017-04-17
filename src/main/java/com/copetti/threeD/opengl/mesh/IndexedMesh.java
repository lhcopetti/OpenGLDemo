package com.copetti.threeD.opengl.mesh;

import java.util.Map;

import com.copetti.threeD.opengl.array.ArrayBuffer;
import com.copetti.threeD.opengl.array.IndexBuffer;
import com.copetti.threeD.opengl.shader.ShaderProgram;
import com.copetti.threeD.opengl.uniform.Uniform;
import com.copetti.threeD.opengl.vertexarray.VertexArrayObject;


public class IndexedMesh extends Mesh
{

	private IndexBuffer indexBuffer;

	public IndexedMesh(VertexArrayObject vao, ShaderProgram shaderProgram,
			Map<String, ArrayBuffer> attributes, Map<String, Uniform> uniforms,
			IndexBuffer indexBuffer)
	{
		super(vao, shaderProgram, attributes, uniforms);
		this.indexBuffer = indexBuffer;
	}

	public void setIndexBuffer(IndexBuffer buffer)
	{
		this.indexBuffer = buffer;
	}

	@Override
	protected void drawBuffer()
	{
		indexBuffer.draw();
	}
}
