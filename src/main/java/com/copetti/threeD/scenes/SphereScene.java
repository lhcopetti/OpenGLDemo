package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector3f;

import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.game.KeyboardControlledAngles;
import com.copetti.threeD.math.IndexUtils;
import com.copetti.threeD.math.grid.Grid2D;
import com.copetti.threeD.math.grid.Vector3fGridFlattener;
import com.copetti.threeD.opengl.mesh.MeshBuilder;
import com.copetti.threeD.shapes.SphericalMeshVertices;


public class SphereScene extends GameScene
{

	private static final int NUM_AZIMUTH_DIVISIONS = 15;
	private static final int NUM_POLAR_DIVISIONS = 15;

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
	public void doUpdate(float deltaTime)
	{
		// TODO Auto-generated method stub
		
	}
	
}
