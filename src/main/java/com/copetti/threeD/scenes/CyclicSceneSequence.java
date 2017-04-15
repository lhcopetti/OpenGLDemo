package com.copetti.threeD.scenes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.lwjgl.glfw.GLFW;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.input.InputEvent;


public class CyclicSceneSequence implements GameScene
{

	List<Class<? extends GameScene>> scenes;

	GameScene currentScene;
	private int currentIndex = 0;

	public CyclicSceneSequence()
			throws InstantiationException, IllegalAccessException
	{
		this.scenes = new ArrayList<>();
		this.scenes.addAll(loadScenes());

		if (scenes.size() < 0)
			throw new RuntimeException("No classes have been found");

		currentScene = this.scenes.get(currentIndex).newInstance();
	}

	public Set<Class<? extends GameScene>> loadScenes()
	{
		ConfigurationBuilder conf = ConfigurationBuilder
				.build("com.copetti.threeD.scenes");
		Reflections ref = new Reflections(conf);

		Set<Class<? extends GameScene>> subTypesOf = ref
				.getSubTypesOf(GameScene.class);
		subTypesOf.remove(getClass());
		return subTypesOf;
	}

	@Override
	public void onEnter()
	{
		currentScene.onEnter();
	}

	@Override
	public void onExit()
	{
		currentScene.onExit();
	}

	@Override
	public void update(float deltaTime)
	{
		currentScene.update(deltaTime);
	}

	@Override
	public void handleInput(InputEvent input)
	{
		System.out.println("Handle input: " + input.getKey());

		if (!shouldPropagate(input)) return;

		currentScene.handleInput(input);
	}

	private boolean shouldPropagate(InputEvent input)
	{
		if (input.getAction() != GLFW.GLFW_RELEASE) return true;

		if (input.getKey() == GLFW.GLFW_KEY_LEFT)
		{
			switchScene(-1);
			return false;
		}
		else
			if (input.getKey() == GLFW.GLFW_KEY_RIGHT)
			{
				switchScene(+1);
				return false;

			}
		return true;
	}

	public void switchScene(int direction)
	{
		currentScene.onExit();

		System.out.println("Scenes: " + scenes.size());
		System.out.println("Current Index: " + currentIndex);
		int newIndex = currentIndex + direction;

		if (newIndex < 0)
			currentIndex = scenes.size() - 1;
		else
			currentIndex = newIndex % scenes.size();

		try
		{
			currentScene = scenes.get(currentIndex).newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new RuntimeException("Failed to instantiate: "
					+ scenes.get(currentIndex).getCanonicalName());
		}
		currentScene.onEnter();
	}

	@Override
	public void draw()
	{
		currentScene.draw();
	}

}
