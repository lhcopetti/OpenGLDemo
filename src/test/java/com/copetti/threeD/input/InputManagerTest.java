package com.copetti.threeD.input;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.lwjgl.glfw.GLFW;


public class InputManagerTest
{

	private InputManager manager;
	private InputHandler mock;
	private InputEvent pressWEvent = new InputEvent(0, 0, InputAction.PRESS, GLFW.GLFW_KEY_W);
	private InputEvent pressingWEvent = InputEvent.pressing(pressWEvent);
	private InputEvent releaseWEvent = InputEvent.release(pressWEvent);

	@Before
	public void before()
	{
		manager = new InputManager();
		mock = mock(InputHandler.class);
	}

	@Test
	public void testFiltersPressKey()
	{
		manager.addPressListener(GLFW.GLFW_KEY_A, mock);
		manager.handleInput(pressWEvent);
		verifyZeroInteractions(mock);
	}

	@Test
	public void testReleaseKeyEventIsFired()
	{
		manager.addReleaseListener(GLFW.GLFW_KEY_W, mock);
		manager.handleInput(releaseWEvent);
		manager.pollInputEvents();
		verify(mock).handleInput(releaseWEvent);
	}

	@Test
	public void testFiltersReleaseKey()
	{
		manager.addReleaseListener(GLFW.GLFW_KEY_W, mock);
		manager.handleInput(pressWEvent);
		verifyZeroInteractions(mock);
	}

	@Test
	public void testHandleInputCallDoesNotFireHandlers()
	{
		manager.addPressListener(GLFW.GLFW_KEY_W, mock);
		manager.handleInput(pressWEvent);
		verifyZeroInteractions(mock);
	}

	@Test
	public void testHandleInputIsCalled()
	{
		manager.addPressListener(GLFW.GLFW_KEY_W, mock);
		manager.handleInput(pressWEvent);
		manager.pollInputEvents();
		verify(mock).handleInput(pressWEvent);
	}

	@Test
	public void testHandlePressingInputIsCaledWhenPressEventIsFired()
	{
		manager.addPressListener(GLFW.GLFW_KEY_W, mock);
		manager.addPressingListener(GLFW.GLFW_KEY_W, mock);
		manager.handleInput(pressWEvent);
		manager.pollInputEvents();
		verify(mock).handleInput(pressWEvent);
		verify(mock).handleInput(pressingWEvent);
	}

	@Test
	public void pressingEventIsContinuouslyFired()
	{
		manager.addPressingListener(GLFW.GLFW_KEY_W, mock);
		manager.handleInput(pressWEvent);

		for( int i = 0; i < 10; i++ )
			manager.pollInputEvents();

		verify(mock, times(10)).handleInput(pressingWEvent);
	}

	@Test
	public void pressingEventIsContinuouslyFiredUntilReleaseEvent()
	{
		manager.addPressingListener(GLFW.GLFW_KEY_W, mock);
		manager.handleInput(pressWEvent);

		manager.pollInputEvents();
		verify(mock).handleInput(pressingWEvent);

		manager.handleInput(InputEvent.release(releaseWEvent));
		manager.pollInputEvents();
		verifyNoMoreInteractions(mock);
	}

}
