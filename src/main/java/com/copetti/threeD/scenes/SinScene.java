package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector2f;

import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.game.KeyboardControlledAngles;
import com.copetti.threeD.game.scene.SceneConfigurationBuilder;
import com.copetti.threeD.input.InputEvent;
import com.copetti.threeD.math.IndexUtils;
import com.copetti.threeD.math.grid.Grid2D;
import com.copetti.threeD.math.grid.Vector2fGridFlattener;
import com.copetti.threeD.opengl.mesh.MeshBuilder;
import com.copetti.threeD.shapes.RectangleMeshVertices;

public class SinScene extends GameScene {

	private float runningTime = 0.f;

	private static final int WIDTH = 30;
	private static final int HEIGHT = 30;
	private static final float WINDOW_FILL_PROPOTION = 35f;

	public SinScene() {
		super(SceneConfigurationBuilder.newBuilder() //
				.enable3D() //
				.enableBackwardDrawing() //
				.enableLineMode() //
				.build());
	}

	@Override
	public void doOnEnter() {
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
	public void handleInput(InputEvent input) {
		angleTransform.handleInput(input);
	}

	@Override
	public void draw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		mesh.setUniform("uWorld", angleTransform.getTransformationMatrix().scale(0.05f));
		mesh.setUniform("uTime", new Float(runningTime));
		mesh.draw();
	}

	@Override
	public void doUpdate(float deltaTime) {
		runningTime += deltaTime;
	}

}
