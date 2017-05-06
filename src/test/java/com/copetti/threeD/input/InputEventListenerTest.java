
package com.copetti.threeD.input;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.lwjgl.glfw.GLFW;


public class InputEventListenerTest
{

	InputEventListener pressWListener;
	InputEvent pressW;
	InputHandler handler;

	@Before
	public void before()
	{
		handler = mock(InputHandler.class);
		pressW = new InputEvent(0, 0, InputAction.PRESS, GLFW.GLFW_KEY_W);
		pressWListener = new InputEventListener(InputAction.PRESS,
				GLFW.GLFW_KEY_W, handler);
	}

	@Test
	public void testHandleInputForPress()
	{
		assertTrue(pressWListener.fire(
				new InputEvent(0, 0, InputAction.PRESS, GLFW.GLFW_KEY_W)));
		verify(handler).handleInput(pressW);
	}

	@Test
	public void testDoNotHandleInputForPressing()
	{
		assertFalse(pressWListener.fire(
				new InputEvent(0, 0, InputAction.PRESSING, GLFW.GLFW_KEY_W)));
		verify(handler, never()).handleInput(pressW);
	}

	@Test
	public void testDoNotHandleInputForRelease()
	{
		assertFalse(pressWListener.fire(
				new InputEvent(0, 0, InputAction.RELEASE, GLFW.GLFW_KEY_W)));
		verify(handler, never()).handleInput(pressW);
	}

	@Test
	public void testDoNotHandleInputForDifferentKey()
	{
		assertFalse(pressWListener.fire(
				new InputEvent(0, 0, InputAction.PRESS, GLFW.GLFW_KEY_V)));
		verify(handler, never()).handleInput(pressW);
	}

	@Test
	public void testHandleInputFireMethodCallsHandler()
	{
		pressWListener.fire(pressW);
		verify(handler).handleInput(pressW);
	}

}
