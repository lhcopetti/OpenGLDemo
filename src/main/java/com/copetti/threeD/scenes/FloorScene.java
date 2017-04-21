package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector2f;

import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.math.IndexUtils;
import com.copetti.threeD.math.Matrix2D;
import com.copetti.threeD.opengl.mesh.Mesh;
import com.copetti.threeD.opengl.mesh.MeshBuilder;


public class FloorScene implements GameScene
{

	private static final int NUM_VERTICES_WIDTH = 2;
	private static final int NUM_VERTICES_HEIGHT = 2;
	private static final float DISTANCE = .15f;

	private Mesh mesh;

	@Override
	public void onEnter()
	{
		Vector2f center = new Vector2f(NUM_VERTICES_WIDTH, NUM_VERTICES_HEIGHT)
				.mul(0.5f).mul(DISTANCE);

		float[] squares = Matrix2D
				.newGridWithSideSize(DISTANCE, NUM_VERTICES_WIDTH,
						NUM_VERTICES_HEIGHT) //
				.transform((t) -> {
					t.sub(center);
					return null;
				}) //
				.flatten();
		int[] indexes = IndexUtils.connectAsGrid(NUM_VERTICES_WIDTH,
				NUM_VERTICES_HEIGHT);

		mesh = MeshBuilder.newBuilder() //
				.addVector2fAttribute("aPosition", squares) //
				.setIndexBuffer(indexes) //
				.loadShaderFromResource("floor_shader") //
				.build();
		// mesh.setUniform("aTranslation", new Matrix3f().trans);
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}

	@Override
	public void onExit()
	{
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}

	@Override
	public void update(float deltaTime)
	{
	}

	@Override
	public void draw()
	{
		glClear(GL_COLOR_BUFFER_BIT);
		glClearColor(0.0f, 0f, 0.4f, 1.0f);

		mesh.draw();
	}

}
