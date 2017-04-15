package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.function.BinaryOperator;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import com.copetti.threeD.classpath.Resource;
import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.input.InputEvent;
import com.copetti.threeD.opengl.array.ArrayBuffer;
import com.copetti.threeD.opengl.array.ArrayBufferFactory;
import com.copetti.threeD.opengl.shader.ShaderProgram;
import com.copetti.threeD.opengl.shader.ShaderProgramBuilder;


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
	private ShaderProgram shaderProgram;

	private ArrayBuffer positions;
	private ArrayBuffer colors;

	private float xAngle;
	private float yAngle;

	public CubeScene()
	{
		vertexShader = Resource.readAllText("cube_shader.vert");
		fragmentShader = Resource.readAllText("cube_shader.frag");
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

		float[] vertexData = new float[]
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
				-.25f, .25f, .25f, //
				.25f, .25f, .25f, //
				-.25f, .25f, -.25f, //
				-.25f, .25f, -.25f, //
				.25f, .25f, .25f, //
				.25f, .25f, -.25f, //

				/* Down */
				.25f, -.25f, -.25f, //
				.25f, -.25f, .25f, //
				-.25f, -.25f, -.25f, //
				-.25f, -.25f, -.25f, //
				.25f, -.25f, .25f, //
				-.25f, -.25f, .25f, };

		float[] colorsData = new float[]
		{ //
				1.0f, 1.0f, 1.0f, //
				0.0f, 0.0f, 0.0f, //
				1.f, 0.0f, 0.0f, //
				0f, 1f, 0.f, //
				0.0f, 0.f, 1.f, //
				0.5f, .5f, 0.5f, //

		}; //

		positions = ArrayBufferFactory.newArrayBuffer(3, vertexData);

		/* Colors */
		float[] arrayColors = new float[vertexData.length];
		int gIndex = 0;

		for( int i = 0; i < colorsData.length; i += 3 )
			for( int j = 0; j < 6; j++ )
				for( int k = 0; k < 3; k++ )
					arrayColors[gIndex++] = colorsData[i + k];

		colors = ArrayBufferFactory.newArrayBuffer(3, arrayColors);

		shaderProgram = ShaderProgramBuilder //
				.newBuilder() //
				.attachVertexShader(vertexShader) //
				.attachFragmentShader(fragmentShader) //
				.build();

		glBindVertexArray(0);
		// glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}

	@Override
	public void onExit()
	{
		glDeleteVertexArrays(vao);

		glDisable(GL_CULL_FACE);
		glDisable(GL_DEPTH_TEST);
	}

	@Override
	public void update(float deltaTime)
	{
		xAngle = xRotation.transform(xAngle, deltaTime);
		yAngle = yRotation.transform(yAngle, deltaTime);
	}

	@Override
	public void draw()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.3f, 0.3f, 0.7f, 1.0f);

		glBindVertexArray(vao);
		shaderProgram.bind();
		/* Uniform: uWorld */
		shaderProgram.setUniform("uWorld", //
				new Matrix4f() //
						.rotateX(xAngle) //
						.rotateY(yAngle));

		positions.bind();
		shaderProgram.setAttribute("aPosition", positions);

		colors.bind();
		shaderProgram.setAttribute("aColor", colors);

		positions.draw();

		
		positions.unbind();
		colors.unbind();
		shaderProgram.clearAttribute("aPosition");
		shaderProgram.clearAttribute("aColor");
		shaderProgram.clearUniform("uWorld");
		shaderProgram.unbind();
		glBindVertexArray(0);
	}

}
