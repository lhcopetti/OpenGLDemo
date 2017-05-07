package com.copetti.threeD.main.Triangle;

import com.copetti.threeD.game.GameWindow;
import com.copetti.threeD.math.CPSize;
import com.copetti.threeD.scenes.CubeScene;
import com.copetti.threeD.window.DemoWindow;
import com.copetti.threeD.window.GLFWWindowHandler;


/**
 * Hello world!
 *
 */
public class App
{

	public static void main(String[] args) throws Exception
	{
		DemoWindow w = new DemoWindow(new GLFWWindowHandler(),
				new CPSize(800, 800));
		w.setTitle("Window Title!");
		w.centerWindow();

		// GameWindow window = new GameWindow(w, new CyclicSceneSequence());
		GameWindow window = new GameWindow(w, new CubeScene());
		window.start();
	}

}
