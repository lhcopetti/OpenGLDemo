package com.copetti.threeD.scenes;

import com.copetti.threeD.game.GameScene;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;


public class TriangleScene implements GameScene
{

	private static final String VERTEX_SHADER = "#version 330\n"
			+ "in vec2 aPosition;\n" + "void main() {\n"
			+ "    gl_Position = vec4(aPosition, 0.0, 1.0);\n" + "}";

	private static final String FRAGMENT_SHADER = "#version 330\n"
			+ "out vec4 out_color;\n" + "void main() {\n"
			+ "    out_color = vec4(1.0, 1.0, 0.0, 1.0);\n" + "}";

	private int vao;
	private int positions;
	private int shader;

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

		float[] vertexData =
		{ 0.0f, 0.5f, //
				-.5f, -.5f, //
				.5f, -.5f };

		FloatBuffer floatBuffer = BufferUtils
				.createFloatBuffer(vertexData.length);
		floatBuffer.put(vertexData).flip();

		positions = glGenBuffers();

		glBindBuffer(GL_ARRAY_BUFFER, positions);
		glBufferData(GL_ARRAY_BUFFER, floatBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		int vertex = compileShader(GL_VERTEX_SHADER, VERTEX_SHADER);
		int fragment = compileShader(GL_FRAGMENT_SHADER, FRAGMENT_SHADER);

		shader = linkProgram(vertex, fragment);
		glBindVertexArray(0);
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}

	public void onExit()
	{
		glDeleteBuffers(positions);
		glDeleteProgram(shader);
		glDeleteVertexArrays(vao);
		
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}

	public void update(float deltaTime)
	{
	}

	public void draw()
	{
		glClear(GL_COLOR_BUFFER_BIT);
		glClearColor(0.0f, 0f, 0.4f, 1.0f);

		glUseProgram(shader);
		glBindVertexArray(vao);

		int aPosition = glGetAttribLocation(shader, "aPosition");
		glEnableVertexAttribArray(aPosition);

		glBindBuffer(GL_ARRAY_BUFFER, positions);
		glVertexAttribPointer(aPosition, 2, GL_FLOAT, false, 0, 0);

		glDrawArrays(GL_TRIANGLES, 0, 3);

		glDisableVertexAttribArray(aPosition);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		glUseProgram(0);

		System.out.println("Draw");
	}

}
