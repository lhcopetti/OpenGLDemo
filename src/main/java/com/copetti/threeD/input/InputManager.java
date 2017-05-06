package com.copetti.threeD.input;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class InputManager implements InputHandler
{

	private List<InputEventListener> listeners;
	private List<InputEvent> events;

	public InputManager()
	{
		listeners = new ArrayList<>();
		events = new ArrayList<>();
	}

	public void addListener(InputAction press, int key, InputHandler handler)
	{
		listeners.add(new InputEventListener(press, key, handler));
	}

	public void handleInput(InputEvent inputEvent)
	{
		if (inputEvent.getAction() == InputAction.RELEASE)
		{
			events = events.stream() //
					.filter(x -> x.getKey() != inputEvent.getKey()) //
					.collect(Collectors.toList()); //
		}

		events.add(inputEvent);

		if (inputEvent.getAction() == InputAction.PRESS)
			events.add(InputEvent.pressing(inputEvent));
	}

	public void addPressListener(int key, InputHandler handler)
	{
		addListener(InputAction.PRESS, key, handler);
	}

	public void addReleaseListener(int key, InputHandler mock)
	{
		addListener(InputAction.RELEASE, key, mock);
	}

	public void addPressingListener(int key, InputHandler handler)
	{
		addListener(InputAction.PRESSING, key, handler);
	}

	public void pollInputEvents()
	{
		listeners.forEach(this::fireEventForListener);

		listeners = listeners.stream() //
				.filter(x -> x.getExpectsAction() == InputAction.PRESSING) //
				.collect(Collectors.toList());
	}

	public void fireEventForListener(InputEventListener listener)
	{
		events.forEach(listener::fire);
	}

}
