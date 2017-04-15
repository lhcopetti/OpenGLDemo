package com.copetti.threeD.scenes;


import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Scanner;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.math.Pentagon;
import com.copetti.threeD.opengl.ArrayBuffer;
import com.copetti.threeD.opengl.ArrayBufferFactory;


public class PentagonScene implements GameScene
{

	private String vertexShaderContent;
	private String fragmentShaderContent;

	private int vao;
	private int shader;
	ArrayBuffer positions;
	
	private float angle;

	private float[] vertexData;

	public PentagonScene() throws IOException
	{
		vertexShaderContent = readAllText("pentagon_shader.vert");
		fragmentShaderContent = readAllText("pentagon_shader.frag");
	}

	private String readAllText(String resourcePath) throws IOException
	{
		Scanner scanner = new Scanner(
				getClass().getClassLoader().getResourceAsStream(resourcePath),
				"UTF-8");
		String allText = scanner.useDelimiter("\\A").next();
		scanner.close();
		return allText;
	}

	private int compileShader(int shaderType, String code)
	{
		int shader = glCreateShader(shaderType);
		glShaderSource(shader, code);
		glCompileShader(shader);

		if (glGetShaderi(shaderType,
				GL_COMPILE_STATUS) == GL_FALSE) { throw new RuntimeException(
						"Failed to compile Shader!"); }

		return shader;
	}

	private int linkProgram(int... shaders)
	{
		int program = glCreateProgram();
		for( int shader : shaders )
			glAttachShader(program, shader);

		glLinkProgram(program);
		if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE)
			throw new RuntimeException(
					"Failed to link shaders: " + glGetProgramInfoLog(program));

		for( int shader : shaders )
		{
			glDetachShader(program, shader);
			glDeleteShader(shader);
		}

		return program;
	}

	public void onEnter()
	{
		System.out.println("On enter!");

		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		vertexData = Pentagon.createPentagon(new Vector2f(0f, .5f), 0.5f);


		positions = ArrayBufferFactory.newArrayBuffer(2, vertexData);
		
		int vertex = compileShader(GL_VERTEX_SHADER, vertexShaderContent);
		int fragment = compileShader(GL_FRAGMENT_SHADER, fragmentShaderContent);

		shader = linkProgram(vertex, fragment);
		glBindVertexArray(0);
		// glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}

	public void onExit()
	{
		glDeleteProgram(shader);
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

		glUseProgram(shader);
		glBindVertexArray(vao);

		// uWorld
		FloatBuffer transform = BufferUtils.createFloatBuffer(16);
		new Matrix4f().rotateZ(angle).get(transform);
		int uWorld = glGetUniformLocation(shader, "uWorld");
		glEnableVertexAttribArray(uWorld);
		glUniformMatrix4fv(uWorld, false, transform);

		// aPosition
		int aPosition = glGetAttribLocation(shader, "aPosition");
		glEnableVertexAttribArray(aPosition);
		
		positions.bind();
		glVertexAttribPointer(aPosition, 2, GL_FLOAT, false, 0, 0);
		positions.draw();

		glDisableVertexAttribArray(aPosition);
		positions.unbind();
		glBindVertexArray(0);
		glUseProgram(0);
	}

}
