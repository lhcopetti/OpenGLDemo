package com.copetti.threeD.opengl.mesh;

import java.util.HashMap;
import java.util.Map;

import com.copetti.threeD.classpath.Resource;
import com.copetti.threeD.opengl.array.ArrayBuffer;
import com.copetti.threeD.opengl.array.ArrayBufferFactory;
import com.copetti.threeD.opengl.array.IndexBuffer;
import com.copetti.threeD.opengl.shader.ShaderProgram;
import com.copetti.threeD.opengl.shader.ShaderProgramBuilder;
import com.copetti.threeD.opengl.uniform.Uniform;
import com.copetti.threeD.opengl.vertexarray.VertexArrayObject;
import com.copetti.threeD.opengl.vertexarray.VertexArrayObjectBuilder;


public class MeshBuilder
{

	private VertexArrayObject vertexArray;
	private ShaderProgram shaderProgram;

	private IndexBuffer indexBuffer;

	private Map<String, ArrayBuffer> attributes = new HashMap<>();
	private Map<String, Uniform> uniforms = new HashMap<>();

	public static MeshBuilder newBuilder()
	{
		return new MeshBuilder();
	}

	private MeshBuilder()
	{
		vertexArray = VertexArrayObjectBuilder.newVertexArrayObject();
		vertexArray.bind();
	}

	public MeshBuilder addVector3fAttribute(String key, float... data)
	{
		insertIntoAttributesArray(key, 3, data);
		return this;
	}

	public MeshBuilder addVector2fAttribute(String key, float[] data)
	{
		insertIntoAttributesArray(key, 2, data);
		return this;
	}

	private ArrayBuffer insertIntoAttributesArray(String attributeKey,
			int elementsSize, float[] data)
	{
		if (attributes.containsKey(attributeKey))
			throw new IllegalArgumentException(
					"Duplicated attribute key: " + attributeKey);

		ArrayBuffer buffer = ArrayBufferFactory.newArrayBuffer(elementsSize,
				data);
		attributes.put(attributeKey, buffer);
		return buffer;
	}

	public MeshBuilder loadShaderFromResource(String pathWithoutExtension)
	{
		final String vertexShaderPath = pathWithoutExtension + ".vert";
		final String fragShaderPath = pathWithoutExtension + ".frag";

		shaderProgram = ShaderProgramBuilder //
				.newBuilder() //
				.attachVertexShader(Resource.readAllText(vertexShaderPath)) //
				.attachFragmentShader(Resource.readAllText(fragShaderPath)) //
				.build();
		return this;
	}

	public Mesh build()
	{
		Mesh mesh = new Mesh(vertexArray, shaderProgram, indexBuffer,
				attributes, uniforms);
		vertexArray.unbind();
		return mesh;
	}

}
