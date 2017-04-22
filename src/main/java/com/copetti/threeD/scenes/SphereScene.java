package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector3f;

import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.game.KeyboardControlledAngles;
import com.copetti.threeD.input.InputEvent;
import com.copetti.threeD.math.IndexUtils;
import com.copetti.threeD.math.grid.Grid2D;
import com.copetti.threeD.math.grid.Vector3fGridFlattener;
import com.copetti.threeD.opengl.mesh.Mesh;
import com.copetti.threeD.opengl.mesh.MeshBuilder;
import com.copetti.threeD.shapes.SphericalMeshVertices;


public class SphereScene implements GameScene
{

	private static final int NUM_AZIMUTH_DIVISIONS = 15;
	private static final int NUM_POLAR_DIVISIONS = 15;

	private KeyboardControlledAngles angleTransform;
	private Mesh mesh;
	
	@Override
	public void handleInput(InputEvent input)
	{
		angleTransform.handleInput(input);
	}

	@Override
	public void onEnter()
	{
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);

		Grid2D<Vector3f> grid = SphericalMeshVertices
				.newSphericalGrid(NUM_AZIMUTH_DIVISIONS, NUM_POLAR_DIVISIONS);

		for( Vector3f v : grid )
			v.mul(0.8f);

		int[] indexes = IndexUtils.connectAsGrid(grid);

		angleTransform = new KeyboardControlledAngles();
		mesh = MeshBuilder.newBuilder() //
				.addVector3fAttribute("aPosition",
						new Vector3fGridFlattener().flatten(grid)) //
				.setIndexBuffer(indexes) //
				.loadShaderFromResource("spherical_shader") //
				.build();
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}

	@Override
	public void onExit()
	{
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}

	@Override
	public void update(float deltaTime)
	{
		angleTransform.update(deltaTime);
	}

	@Override
	public void draw()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.0f, 0.4f, 0.4f, 1.0f);

		mesh.setUniform("uWorld", angleTransform.getTransformationMatrix());
		mesh.draw();
	}
}
