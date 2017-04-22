package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.*;

import java.util.function.BinaryOperator;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.input.InputEvent;
import com.copetti.threeD.math.IndexUtils;
import com.copetti.threeD.math.grid.Grid2D;
import com.copetti.threeD.math.grid.Vector3fGridFlattener;
import com.copetti.threeD.opengl.mesh.Mesh;
import com.copetti.threeD.opengl.mesh.MeshBuilder;
import com.copetti.threeD.shapes.SphericalMeshVertices;


public class SphereScene implements GameScene
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

	private float xAngle;
	private float yAngle;

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

	private static final int NUM_AZIMUTH_DIVISIONS = 15;
	private static final int NUM_POLAR_DIVISIONS = 15;

	private Mesh mesh;

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
		xAngle = xRotation.transform(xAngle, deltaTime);
		yAngle = yRotation.transform(yAngle, deltaTime);
	}

	@Override
	public void draw()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.0f, 0.4f, 0.4f, 1.0f);

		mesh.setUniform("uWorld",
				new Matrix4f().rotateX(xAngle).rotateY(yAngle));
		mesh.draw();
	}
}
