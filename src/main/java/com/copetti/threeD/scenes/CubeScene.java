package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
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
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import com.copetti.threeD.classpath.Resource;
import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.input.InputEvent;


public class CubeScene implements GameScene
{

	enum Rotation
	{
		ROTATION_INCREMENT((x, y) -> {
			return x + y;
		}), ROTATION_DECREMENT((x, y) -> {
			return x - y;
		}), ROTATION_NONE((x, y) -> {
			return x;
		});

		private Rotation(BinaryOperator<Float> op)
		{
			this.operator = op;
		}

		private BinaryOperator<Float> operator;

		public float transform(float current, float differential)
		{
			return operator.apply(current, differential);
		}
	}

	private Rotation xRotation = Rotation.ROTATION_NONE;
	private Rotation yRotation = Rotation.ROTATION_NONE;

	private String vertexShader;
	private String fragmentShader;

	private int vao;
	private int shaderProgram;

	private int positions;
	private float[] vertexData;

	private float xAngle;
	private float yAngle;
	private float[] colorsData;
	private int colors;

	public CubeScene()
	{
		vertexShader = Resource.readAllText("cube_shader.vert");
		fragmentShader = Resource.readAllText("cube_shader.frag");
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
	public void handleInput(InputEvent input)
	{
		if (input.getAction() == GLFW.GLFW_PRESS)
			handleKeyPress(input);
		else
			if (input.getAction() == GLFW.GLFW_RELEASE) //
				handleKeyRelease(input);
	}

	private void handleKeyPress(InputEvent input)
	{
		switch (input.getKey())
		{
		case GLFW.GLFW_KEY_A:
			yRotation = Rotation.ROTATION_INCREMENT;
			break;
		case GLFW.GLFW_KEY_D:
			yRotation = Rotation.ROTATION_DECREMENT;
			break;
		case GLFW.GLFW_KEY_W:
			xRotation = Rotation.ROTATION_INCREMENT;
			break;
		case GLFW.GLFW_KEY_S:
			xRotation = Rotation.ROTATION_DECREMENT;
			break;
		}
	}

	private void handleKeyRelease(InputEvent input)
	{
		switch (input.getKey())
		{
		case GLFW.GLFW_KEY_A:
		case GLFW.GLFW_KEY_D:
			yRotation = Rotation.ROTATION_NONE;
		case GLFW.GLFW_KEY_W:
		case GLFW.GLFW_KEY_S:
			xRotation = Rotation.ROTATION_NONE;
			break;
		}
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
				/* Front */
				-.25f, .25f, 0.25f, //
				-.25f, -.25f, 0.25f, //
				.25f, .25f, 0.25f, //
				.25f, .25f, 0.25f, //
				-.25f, -.25f, 0.25f, //
				.25f, -.25f, 0.25f, //

				/* Right */
				.25f, -.25f, 0.25f, //
				.25f, -.25f, -0.25f, //
				.25f, .25f, .25f, //
				.25f, .25f, .25f, //
				.25f, -.25f, -0.25f, //
				.25f, .25f, -.25f, //

				/* Back */
				.25f, -.25f, -0.25f, //
				-.25f, -.25f, -0.25f, //
				.25f, .25f, -0.25f, //
				.25f, .25f, -0.25f, //
				-.25f, -.25f, -0.25f, //
				-.25f, .25f, -0.25f, //

				/* Left */
				-.25f, .25f, -.25f, //
				-.25f, -.25f, -0.25f, //
				-.25f, .25f, .25f, //
				-.25f, .25f, .25f, //
				-.25f, -.25f, -0.25f, //
				-.25f, -.25f, 0.25f, //

				/* Up */
				-.25f, .25f, .25f, .25f, .25f, .25f, -.25f, .25f, -.25f, -.25f,
				.25f, -.25f, .25f, .25f, .25f, .25f, .25f, -.25f,

				/* Down */
				.25f, -.25f, -.25f, .25f, -.25f, .25f, -.25f, -.25f, -.25f,
				-.25f, -.25f, -.25f, .25f, -.25f, .25f, -.25f, -.25f, .25f, };

		colorsData = new float[]
		{ //
				1.0f, 0.8f, 0.6f, //
				0.8f, 0.6f, 0.4f, //
				0.6f, 0.4f, 0.2f, //
				0.4f, 0.2f, 0.f, //
				0.2f, 0.f, 1.f, //
				0.f, 1.0f, 0.8f, //

		}; //

		/* Vertexes Buffer */
		FloatBuffer vertexes = BufferUtils.createFloatBuffer(vertexData.length);
		vertexes.put(vertexData).flip();

		positions = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, positions);
		glBufferData(GL_ARRAY_BUFFER, vertexes, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		/* Colors */
		FloatBuffer colorData = BufferUtils
				.createFloatBuffer(colorsData.length * 6);
		for( int i = 0; i < colorsData.length; i++ )
		{
			final int index = i;
			IntStream.rangeClosed(0, 5).forEach((v) -> {
				colorData.put(colorsData, index, 1);
			});
		}
		colorData.flip();

		colors = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, colors);
		glBufferData(GL_ARRAY_BUFFER, colorData, GL_STATIC_DRAW);
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
		xAngle = xRotation.transform(xAngle, deltaTime);
		yAngle = yRotation.transform(yAngle, deltaTime);

		// if (xRotation) angle += deltaTime;
		// angle = (float) Math.toRadians(45);
	}

	@Override
	public void draw()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.3f, 0.3f, 0.7f, 1.0f);

		glUseProgram(shaderProgram);
		glBindVertexArray(vao);

		/* Uniform: uWorld */
		FloatBuffer angleBuffer = BufferUtils.createFloatBuffer(16);
		getTransformationMatrix(angleBuffer);
		int uWorld = glGetUniformLocation(shaderProgram, "uWorld");
		glEnableVertexAttribArray(uWorld);
		glUniformMatrix4fv(uWorld, false, angleBuffer);

		/* Input: aPosition */
		int aPosition = glGetAttribLocation(shaderProgram, "aPosition");
		glEnableVertexAttribArray(aPosition);
		glBindBuffer(GL_ARRAY_BUFFER, positions);
		glVertexAttribPointer(aPosition, 3, GL_FLOAT, false, 0, 0);

		/* Input: Color */
		int aColor = glGetAttribLocation(shaderProgram, "aColor");
		glEnableVertexAttribArray(aColor);
		glBindBuffer(GL_ARRAY_BUFFER, colors);
		glVertexAttribPointer(aColor, 3, GL_FLOAT, false, 0, 0);

		/* Draw */
		glDrawArrays(GL_TRIANGLES, 0, vertexData.length / 3);

		glDisableVertexAttribArray(uWorld);
		glDisableVertexAttribArray(aPosition);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		glUseProgram(0);
	}

	private void getTransformationMatrix(FloatBuffer angleBuffer)
	{
		new Matrix4f() //
				.rotateX(xAngle) //
				.rotateY(yAngle) //
				.get(angleBuffer); //
	}

}
