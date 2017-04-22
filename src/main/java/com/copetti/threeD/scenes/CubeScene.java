package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.*;

import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.game.KeyboardControlledAngles;
import com.copetti.threeD.input.InputEvent;
import com.copetti.threeD.opengl.mesh.Mesh;
import com.copetti.threeD.opengl.mesh.MeshBuilder;


public class CubeScene implements GameScene
{

	private Mesh mesh;
	private KeyboardControlledAngles angleTransform;

	public CubeScene()
	{
	}

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

		/* Colors */
		float[] arrayColors = new float[vertexData.length];
		int gIndex = 0;

		for( int i = 0; i < colorsData.length; i += 3 )
			for( int j = 0; j < 6; j++ )
				for( int k = 0; k < 3; k++ )
					arrayColors[gIndex++] = colorsData[i + k];

		angleTransform = new KeyboardControlledAngles();
		mesh = MeshBuilder //
				.newBuilder() //
				.addVector3fAttribute("aPosition", vertexData) //
				.addVector3fAttribute("aColor", arrayColors) //
				.loadShaderFromResource("cube_shader") //
				.build();
	}

	@Override
	public void onExit()
	{
		glDisable(GL_CULL_FACE);
		glDisable(GL_DEPTH_TEST);
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
		glClearColor(0.3f, 0.3f, 0.7f, 1.0f);

		mesh.setUniform("uWorld", angleTransform.getTransformationMatrix());
		mesh.draw();
	}

}
