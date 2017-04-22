package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector2f;

import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.math.IndexUtils;
import com.copetti.threeD.math.grid.Grid2D;
import com.copetti.threeD.math.grid.Vector2fGridFlattener;
import com.copetti.threeD.opengl.mesh.Mesh;
import com.copetti.threeD.opengl.mesh.MeshBuilder;
import com.copetti.threeD.shapes.RectangleMeshVertices;


public class FloorScene implements GameScene
{

	private static final int NUM_VERTICES_WIDTH = 20;
	private static final int NUM_VERTICES_HEIGHT = 20;
	private static final float WINDOW_FILL_PROPOTION = .8f;

	private Mesh mesh;

	@Override
	public void onEnter()
	{
		Grid2D<Vector2f> grid = RectangleMeshVertices
				.newGrid(NUM_VERTICES_WIDTH, NUM_VERTICES_HEIGHT);

		float widthDistance = grid.width() - 1;
		float heightDistance = grid.height() - 1;

		for( Vector2f v : grid )
			v.set(v.x * (1 + WINDOW_FILL_PROPOTION) / widthDistance,
					v.y * (1 + WINDOW_FILL_PROPOTION) / heightDistance);

		float[] squares = new Vector2fGridFlattener().flatten(grid);
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
