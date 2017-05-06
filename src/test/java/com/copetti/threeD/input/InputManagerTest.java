package com.copetti.threeD.input;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.lwjgl.glfw.GLFW;
import org.mockito.Mockito;


public class InputManagerTest
{

	private InputManager manager;
	private InputCallback pressMock;
	private InputCallback pressingMock;
	private InputCallback releaseMock;
	private InputEvent pressWEvent = new InputEvent(0, 0, InputAction.PRESS, GLFW.GLFW_KEY_W);
	private InputEvent pressingWEvent = InputEvent.pressing(pressWEvent);
	private InputEvent releaseWEvent = InputEvent.release(pressWEvent);

	public List<InputCallback> allMocks()
	{
		return Arrays.asList(pressMock, pressingMock, releaseMock);
	}
	
	public void verifyZeroInteractions(List<InputCallback> mocks)
	{
		mocks.stream().forEach(Mockito::verifyNoMoreInteractions);
	}
	
	@Before
	public void before()
	{
		manager = new InputManager();
		pressMock = mock(InputCallback.class);
		pressingMock = mock(InputCallback.class);
		releaseMock = mock(InputCallback.class);
	}
	
	@Test
	public void testFiltersPressKey()
	{
		manager.addPressListener(GLFW.GLFW_KEY_A, pressMock);
		manager.handleInput(pressWEvent);
		verifyZeroInteractions(allMocks());
	}

	@Test
	public void testReleaseKeyEventIsFired()
	{
		manager.addReleaseListener(GLFW.GLFW_KEY_W, releaseMock);
		manager.handleInput(releaseWEvent);
		manager.pollInputEvents();
		verify(releaseMock).handle();
	}

	@Test
	public void testFiltersReleaseKey()
	{
		manager.addReleaseListener(GLFW.GLFW_KEY_W, releaseMock);
		manager.handleInput(pressWEvent);
		verifyZeroInteractions(allMocks());
	}

	@Test
	public void testReleaseKeyIsFired()
	{
		manager.addReleaseListener(GLFW.GLFW_KEY_W, releaseMock);
		manager.handleInput(releaseWEvent);
		manager.pollInputEvents();
		
		manager.handleInput(releaseWEvent);
		manager.pollInputEvents();
		
		verify(releaseMock, times(2)).handle();
	}
	
	@Test
	public void testHandleInputCallDoesNotFireHandlers()
	{
		manager.addPressListener(GLFW.GLFW_KEY_W, pressMock);
		manager.handleInput(pressingWEvent);
		verifyZeroInteractions(allMocks());
	}

	@Test
	public void testHandleInputIsCalled()
	{
		manager.addPressListener(GLFW.GLFW_KEY_W, pressMock);
		manager.handleInput(pressWEvent);
		manager.pollInputEvents();
		verify(pressMock).handle();
	}

	@Test
	public void testHandlePressingInputIsCaledWhenPressEventIsFired()
	{
		manager.addPressListener(GLFW.GLFW_KEY_W, pressMock);
		manager.addPressingListener(GLFW.GLFW_KEY_W, pressingMock);
		manager.handleInput(pressWEvent);
		manager.pollInputEvents();
		verify(pressMock).handle();
		verify(pressingMock).handle();
	}

	@Test
	public void pressingEventIsContinuouslyFired()
	{
		manager.addPressingListener(GLFW.GLFW_KEY_W, pressingMock);
		manager.handleInput(pressWEvent);

		for( int i = 0; i < 10; i++ )
			manager.pollInputEvents();

		verify(pressingMock, times(10)).handle();
	}

	@Test
	public void pressingEventIsContinuouslyFiredUntilReleaseEvent()
	{
		manager.addPressingListener(GLFW.GLFW_KEY_W, pressingMock);
		manager.handleInput(pressWEvent);

		manager.pollInputEvents();
		verify(pressingMock).handle();

		manager.handleInput(InputEvent.release(releaseWEvent));
		manager.pollInputEvents();
		verifyZeroInteractions(allMocks());
	}

}
