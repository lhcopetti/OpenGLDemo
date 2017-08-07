package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.math.Pentagon;
import com.copetti.threeD.opengl.mesh.Mesh;
import com.copetti.threeD.opengl.mesh.MeshBuilder;


public class PentagonScene extends GameScene
{

	private float angle;
	private Mesh mesh;

	public PentagonScene() throws IOException
	{
	}

	public void doOnEnter()
	{
		float[] vertexData = Pentagon.createPentagon(new Vector2f(0f, .5f),
				0.5f);

		mesh = MeshBuilder.newBuilder() //
				.addVector2fAttribute("aPosition", vertexData)
				.loadShaderFromResource("pentagon_shader").build();
	}

	@Override
	public void draw()
	{
		glClear(GL_COLOR_BUFFER_BIT);
		glClearColor(0.0f, 0f, 0.4f, 1.0f);

		mesh.setUniform("uWorld", new Matrix4f().rotateZ(angle));
		mesh.draw();
	}

	@Override
	public void doUpdate(float deltaTime)
	{
		angle += deltaTime / 2;
	}
}
