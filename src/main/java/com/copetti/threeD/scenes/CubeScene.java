package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector3f;

import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.math.NormalCalculation;
import com.copetti.threeD.opengl.mesh.Mesh;
import com.copetti.threeD.opengl.mesh.MeshBuilder;


public class CubeScene extends GameScene
{

	public CubeScene()
	{
		super(true);
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
				0.0f, 0f, 0f, //
				0.0f, 0.0f, 0.0f, //
				0.f, 0.0f, 0.0f, //
				0f, 0f, 0.f, //
				0.0f, 0.f, 0.f, //
				0.0f, .0f, 0.0f, //

		}; //

		/* Colors */
		float[] arrayColors = new float[vertexData.length];
		int gIndex = 0;

		for( int i = 0; i < colorsData.length; i += 3 )
			for( int j = 0; j < 6; j++ )
				for( int k = 0; k < 3; k++ )
					arrayColors[gIndex++] = colorsData[i + k];

		mesh = MeshBuilder //
				.newBuilder() //
				.addVector3fAttribute("aPosition", vertexData) //
				.addVector3fAttribute("aColor", arrayColors) //
				.addVector3fAttribute("aNormal", NormalCalculation.calculateNormal(vertexData)) //
				.loadShaderFromResource("cube_shader") //
				.build();
	}

	@Override
	public void clearBackground()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.f, 0.f, 0.f, 1.f);
	}

	@Override
	public void onExit()
	{
		glDisable(GL_CULL_FACE);
		glDisable(GL_DEPTH_TEST);
	}

	@Override
	public void doUpdate(float deltaTime)
	{

	}
	
	@Override
	public void doDraw(Mesh mesh)
	{
		mesh.setUniform("uCameraPosition", camera.getPosition());
		mesh.setUniform("uAmbientLight", new Vector3f(.1f, .1f, .1f));
		mesh.setUniform("uLightPos", new Vector3f(0.5f, 0f, 1f));
		mesh.setUniform("uDiffuseLight", new Vector3f(1.f, 1.f, .8f));
		mesh.setUniform("uSpecularLight", new Vector3f(1.f, 1.f, 1f));
		mesh.setUniform("uSpecularPower", new Float(250.f));
		mesh.setUniform("uAttenuationFactor", new Float(.5f));
	}

}