package com.copetti.threeD.game;

import org.lwjgl.opengl.GL;

import com.copetti.threeD.time.TimeController;
import com.copetti.threeD.window.DemoWindow;


public class GameWindow
{

	private DemoWindow window;
	private GameScene scene;
	private TimeController time;

	private static final float FIXED_TIME_STEP = 1 / 30.f;

	public GameWindow(DemoWindow window, GameScene scene)
	{
		this(window, scene, null);
		
		window.setInputHandler(scene);
	}

	public GameWindow(DemoWindow window, GameScene scene, TimeController time)
	{
		this.scene = scene;
		this.window = window;

		if (null == time) this.time = () -> {
			return System.currentTimeMillis();
		};
	}

	public void start()
	{
		GL.createCapabilities();
		
		long before = time.getCurrentTime() - 1;
		scene.onEnter();

		while (!window.shouldClose())
		{
			float time = (System.currentTimeMillis() - before) / 1000f;

			if (time > FIXED_TIME_STEP)
			{
				before = System.currentTimeMillis() - 1
						+ (long) (time - FIXED_TIME_STEP);
				loop(time);
			}
		}

		window.deinit();
		scene.onExit();
	}

	protected void loop(float deltaTime)
	{
		scene.update(deltaTime);
		scene.draw();

		window.update();
	}

}
