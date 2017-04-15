package com.copetti.threeD.game;

import com.copetti.threeD.input.InputEvent;
import com.copetti.threeD.input.InputHandler;


public interface GameScene extends InputHandler
{

	public void onEnter();

	public void onExit();

	public void update(float deltaTime);

	public void draw();

	@Override
	default void handleInput(InputEvent input)
	{
	}
}
