package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.IOException;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import com.copetti.threeD.classpath.Resource;
import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.math.Pentagon;
import com.copetti.threeD.opengl.array.ArrayBuffer;
import com.copetti.threeD.opengl.array.ArrayBufferFactory;
import com.copetti.threeD.opengl.shader.ShaderProgram;
import com.copetti.threeD.opengl.shader.ShaderProgramBuilder;


public class PentagonScene implements GameScene
{

	private String vertexShaderContent;
	private String fragmentShaderContent;

	private int vao;
	private ShaderProgram shaderProgram;
	ArrayBuffer positions;

	private float angle;

	public PentagonScene() throws IOException
	{
		vertexShaderContent = Resource.readAllText("pentagon_shader.vert");
		fragmentShaderContent = Resource.readAllText("pentagon_shader.frag");
	}

	public void onEnter()
	{
		System.out.println("On enter!");

		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		float[] vertexData = Pentagon.createPentagon(new Vector2f(0f, .5f),
				0.5f);
		positions = ArrayBufferFactory.newArrayBuffer(2, vertexData);

		shaderProgram = ShaderProgramBuilder //
				.newBuilder() //
				.attachVertexShader(vertexShaderContent) //
				.attachFragmentShader(fragmentShaderContent) //
				.build();

		glBindVertexArray(0);
		// glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}

	public void onExit()
	{
		glDeleteProgram(shaderProgram.getShaderId());
		glDeleteVertexArrays(vao);
	}

	public void update(float deltaTime)
	{
		angle += deltaTime / 2;
	}

	public void draw()
	{
		glClear(GL_COLOR_BUFFER_BIT);
		glClearColor(0.0f, 0f, 0.4f, 1.0f);

		glBindVertexArray(vao);
		shaderProgram.bind();

		// uWorld
		shaderProgram.setUniform("uWorld", new Matrix4f().rotateZ(angle));

		// aPosition
		positions.bind();
		shaderProgram.setAttribute("aPosition", positions);
		positions.draw();

		shaderProgram.clearAttribute("aPosition");
		shaderProgram.clearUniform("uWorld");
		shaderProgram.unbind();
		positions.unbind();
		glBindVertexArray(0);
	}

}
