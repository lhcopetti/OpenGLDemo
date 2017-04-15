package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import com.copetti.threeD.classpath.Resource;
import com.copetti.threeD.game.GameScene;


public class SquareScene implements GameScene
{

	private String vertexShader;
	private String fragmentShader;

	private int vao;
	private int shaderProgram;

	private int positions;
	private float[] vertexData;
	private int[] indexData;
	private int indexes;

	public SquareScene()
	{
		vertexShader = Resource.readAllText("square_shader.vert");
		fragmentShader = Resource.readAllText("square_shader.frag");
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

	@Override
	public void onEnter()
	{
		 glEnable(GL_DEPTH_TEST);
		 glEnable(GL_CULL_FACE);
		
		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		vertexData = new float[]
		{ //
				-.5f, -.5f, //
				.5f, .5f,
				-.5f, +.5f, //
				0.5f, -.5f //
		};

		indexData = new int[]
		{ //
				0, 1, 2, //
				1, 0 , 3
		};

		/* Index Buffer */
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(indexData.length);
		indexBuffer.put(indexData).flip();
		indexes = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexes);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		FloatBuffer vertexes = BufferUtils.createFloatBuffer(vertexData.length);
		vertexes.put(vertexData).flip();

		positions = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, positions);
		glBufferData(GL_ARRAY_BUFFER, vertexes, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		int sVertex = compileShader(GL_VERTEX_SHADER, vertexShader);
		int sFragment = compileShader(GL_FRAGMENT_SHADER, fragmentShader);

		shaderProgram = linkProgram(sVertex, sFragment);

		glBindVertexArray(0);
		// glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}

	@Override
	public void onExit()
	{
	}

	@Override
	public void update(float deltaTime)
	{
	}

	@Override
	public void draw()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.3f, 0.3f, 0.7f, 1.0f);

		glUseProgram(shaderProgram);
		glBindVertexArray(vao);

		int aPosition = glGetAttribLocation(shaderProgram, "aPosition");
		glEnableVertexAttribArray(aPosition);

		glBindBuffer(GL_ARRAY_BUFFER, positions);
		glVertexAttribPointer(aPosition, 2, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexes);
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(aPosition);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		glUseProgram(0);
	}

}
