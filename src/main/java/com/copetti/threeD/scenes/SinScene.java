package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector2f;

import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.game.KeyboardControlledAngles;
import com.copetti.threeD.input.InputEvent;
import com.copetti.threeD.math.IndexUtils;
import com.copetti.threeD.math.grid.Grid2D;
import com.copetti.threeD.math.grid.Vector2fGridFlattener;
import com.copetti.threeD.opengl.mesh.Mesh;
import com.copetti.threeD.opengl.mesh.MeshBuilder;
import com.copetti.threeD.shapes.RectangleMeshVertices;

public class SinScene implements GameScene {

	private KeyboardControlledAngles angleTransform;
	private Mesh mesh;
	private float runningTime = 0.f;

	private static final int WIDTH = 30;
	private static final int HEIGHT = 30;
	private static final float WINDOW_FILL_PROPOTION = 35f;

	@Override
	public void onEnter() {
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);

		Grid2D<Vector2f> grid = RectangleMeshVertices.newGrid(WIDTH, HEIGHT);

		for (Vector2f v : grid)
			v.set(v.x * (1 + WINDOW_FILL_PROPOTION) / (grid.width() - 1),
					v.y * (1 + WINDOW_FILL_PROPOTION) / (grid.height() - 1));

		float[] squares = new Vector2fGridFlattener().flatten(grid);
		int[] indexes = IndexUtils.connectAsGrid(grid);

		angleTransform = new KeyboardControlledAngles();
		mesh = MeshBuilder.newBuilder() //
				.addVector2fAttribute("aPosition", squares) //
				.setIndexBuffer(indexes) //
				.loadShaderFromResource("sin_shader") //
				.build();
	}

	@Override
	public void onExit() {
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
	}

	@Override
	public void handleInput(InputEvent input) {
		angleTransform.handleInput(input);
	}

	@Override
	public void update(float deltaTime) {
		runningTime += deltaTime;
		angleTransform.update(deltaTime);
	}

	@Override
	public void draw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		mesh.setUniform("uWorld", angleTransform.getTransformationMatrix().scale(0.05f));
		mesh.setUniform("uTime", new Float(runningTime));
		mesh.draw();
	}

}
