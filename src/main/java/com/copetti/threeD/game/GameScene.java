package com.copetti.threeD.game;

import static org.lwjgl.opengl.GL11.*;

import com.copetti.threeD.input.InputEvent;
import com.copetti.threeD.input.InputHandler;
import com.copetti.threeD.input.InputManager;
import com.copetti.threeD.opengl.camera.Camera;
import com.copetti.threeD.opengl.mesh.Mesh;
import com.sun.org.apache.bcel.internal.generic.NEW;


public abstract class GameScene implements InputHandler
{

	protected InputManager inputManager;
	protected Camera camera;
	protected Mesh mesh;

	protected KeyboardControlledAngles angleTransform;

	private boolean enableCamera;

	public GameScene()
	{
		this(false);
	}

	public GameScene(boolean enableCamera)
	{
		this.enableCamera = enableCamera;
		camera = new Camera();
		inputManager = new InputManager();
		angleTransform = new KeyboardControlledAngles();
	}

	public void update(float deltaTime)
	{
		angleTransform.update(deltaTime);
		inputManager.pollInputEvents();
		doUpdate(deltaTime);
	}

	public void draw()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.9f, 0.3f, 0.1f, 1.0f);

		mesh.setUniform("uEnableCamera", new Boolean(enableCamera));
		if (enableCamera)
		{
			mesh.setUniform("uView", camera.getViewMatrix());
			mesh.setUniform("uProjection", camera.getProjectionMatrix());
		}
		mesh.setUniform("uWorld", angleTransform.getTransformationMatrix());
		mesh.draw();
	}

	@Override
	public void handleInput(InputEvent input)
	{
		angleTransform.handleInput(input);
		inputManager.handleInput(input);
	}

	public void onEnter()
	{
	}

	public void onExit()
	{
	}

	public void doUpdate(float deltaTime)
	{
	}

	public void doDraw(Mesh mesh)
	{
	}
}
