package com.copetti.threeD.game;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.copetti.threeD.time.TimeController;
import com.copetti.threeD.window.DemoWindow;


public class GameWindowTest
{

	private DemoWindow window;
	private GameScene scene;
	private GameWindow game;
	private TimeController time;

	@Before
	public void setUp()
	{
		scene = Mockito.mock(GameScene.class);
		window = Mockito.mock(DemoWindow.class);
		time = Mockito.mock(TimeController.class);
		game = new GameWindow(window, scene, time);
	}

	@Test
	public void managersSceneLifeCycle()
	{
		Mockito.when(window.shouldClose()).thenReturn(true);
		game.start();
		Mockito.verify(scene).onEnter();
		Mockito.verify(scene).onExit();
	}

	@Test
	public void loopCallsUpWithCorrectDeltaTime()
	{
		game.loop(10.f);

		Mockito.verify(scene).update(10.f);
		Mockito.verify(scene).draw();

		Mockito.verify(window).update();
	}

}
