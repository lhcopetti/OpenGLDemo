package com.copetti.threeD.game;

import static org.lwjgl.opengl.GL11.*;

import com.copetti.threeD.game.scene.SceneConfiguration;
import com.copetti.threeD.game.scene.SceneConfigurationBuilder;
import com.copetti.threeD.input.InputEvent;
import com.copetti.threeD.input.InputHandler;
import com.copetti.threeD.input.InputManager;
import com.copetti.threeD.opengl.camera.Camera;
import com.copetti.threeD.opengl.mesh.Mesh;


public abstract class GameScene implements InputHandler
{

	protected InputManager inputManager;
	protected Camera camera;
	protected Mesh mesh;

	protected KeyboardControlledAngles angleTransform;

	private SceneConfiguration config;
	
	public GameScene() {
		this(SceneConfigurationBuilder.defaults());
	}

	public GameScene(SceneConfiguration config)
	{
		this.config = config;
		camera = new Camera();
		inputManager = new InputManager();
		angleTransform = new KeyboardControlledAngles();
	}

	public final void update(float deltaTime)
	{
		angleTransform.update(deltaTime);
		inputManager.pollInputEvents();
		doUpdate(deltaTime);
	}

	public void clearBackground()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.9f, 0.3f, 0.1f, 1.0f);
	}
	
	private void setCamera()
	{
		mesh.setUniform("uView", camera.getViewMatrix());
		mesh.setUniform("uProjection", camera.getProjectionMatrix());
	}

	public void draw()
	{
		clearBackground();

		if (config.isCameraEnabled()) setCamera();

		mesh.setUniform("uWorld", angleTransform.getTransformationMatrix());

		doDraw(mesh);
		mesh.draw();
	}

	@Override
	public void handleInput(InputEvent input)
	{
		angleTransform.handleInput(input);
		inputManager.handleInput(input);
	}
	
	public final void onEnter()
	{
		config.applyOnEnter(this);
		doOnEnter();
	}

	public final void onExit()
	{
		config.applyOnExit(this);
		doOnExit();
	}

	protected void doOnEnter() { /* hook */ }
	protected void doOnExit() { /* hook */ }
	
	public void doDraw(Mesh mesh)
	{
	}

	public abstract void doUpdate(float deltaTime);
}
