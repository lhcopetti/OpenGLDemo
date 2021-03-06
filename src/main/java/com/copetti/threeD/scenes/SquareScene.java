package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.copetti.threeD.classpath.Resource;
import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.game.scene.SceneConfigurationBuilder;
import com.copetti.threeD.opengl.array.ArrayBuffer;
import com.copetti.threeD.opengl.array.ArrayBufferFactory;
import com.copetti.threeD.opengl.array.IndexBuffer;

public class SquareScene extends GameScene {

	private String vertexShader;
	private String fragmentShader;

	private int vao;
	private int shaderProgram;

	private ArrayBuffer positions;
	private IndexBuffer indexes;

	private float[] vertexData;
	private int[] indexData;

	public SquareScene() {
		super(SceneConfigurationBuilder.newBuilder() //
				.enable3D() //
				.enableBackwardDrawing() //
				.build());

		vertexShader = Resource.readAllText("square_shader.vert");
		fragmentShader = Resource.readAllText("square_shader.frag");
	}

	private int compileShader(int shaderType, String code) {
		int shader = glCreateShader(shaderType);
		glShaderSource(shader, code);
		glCompileShader(shader);

		if (glGetShaderi(shaderType, GL_COMPILE_STATUS) == GL_FALSE) {
			throw new RuntimeException("Failed to compile Shader!");
		}

		return shader;
	}

	private int linkProgram(int... shaders) {
		int program = glCreateProgram();
		for (int shader : shaders)
			glAttachShader(program, shader);

		glLinkProgram(program);
		if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE)
			throw new RuntimeException("Failed to link shaders: " + glGetProgramInfoLog(program));

		for (int shader : shaders) {
			glDetachShader(program, shader);
			glDeleteShader(shader);
		}

		return program;
	}

	@Override
	public void doOnEnter() {
		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		vertexData = new float[] { //
				-.5f, -.5f, //
				.5f, .5f, //
				-.5f, +.5f, //
				0.5f, -.5f //
		};

		indexData = new int[] { //
				0, 1, 2, //
				1, 0, 3 //
		};

		indexes = ArrayBufferFactory.newIndexBuffer(indexData);
		positions = ArrayBufferFactory.newArrayBuffer(2, vertexData);

		int sVertex = compileShader(GL_VERTEX_SHADER, vertexShader);
		int sFragment = compileShader(GL_FRAGMENT_SHADER, fragmentShader);

		shaderProgram = linkProgram(sVertex, sFragment);

		glBindVertexArray(0);
		// glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}

	@Override
	public void doOnExit() {
		glDeleteShader(shaderProgram);
		glDeleteVertexArrays(vao);
	}

	@Override
	public void draw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.3f, 0.3f, 0.7f, 1.0f);

		glUseProgram(shaderProgram);
		glBindVertexArray(vao);

		int aPosition = glGetAttribLocation(shaderProgram, "aPosition");
		glEnableVertexAttribArray(aPosition);

		positions.bind();
		glVertexAttribPointer(aPosition, 2, GL_FLOAT, false, 0, 0);

		indexes.draw();

		glDisableVertexAttribArray(aPosition);
		positions.unbind();
		glBindVertexArray(0);
		glUseProgram(0);
	}

	@Override
	public void doUpdate(float deltaTime) {
		// TODO Auto-generated method stub

	}

}
