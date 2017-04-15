package com.copetti.threeD.window;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.copetti.threeD.math.CPPoint;
import com.copetti.threeD.math.CPSize;

import junit.framework.Assert;


@RunWith(MockitoJUnitRunner.class)
public class DemoWindowTest
{

	private WindowManager windowManager;
	private DemoWindow demo;

	private final CPSize windowSize = new CPSize(100, 100);

	@Before
	public void init()
	{
		windowManager = Mockito.mock(WindowManager.class);
		demo = new DemoWindow(windowManager, windowSize);
	}

	@Test
	public void testDemoWindowTitle()
	{
		demo.setTitle("This is the Title");
		Mockito.verify(windowManager).setTitle("This is the Title");
	}

	@Test
	public void testDemoCenterWindow()
	{
		final CPSize monitorSize = new CPSize(2000, 1000);
		Mockito.when(windowManager.getMonitorSize()).thenReturn(monitorSize);

		demo.centerWindow();
		Mockito.verify(windowManager).setWindowPosition(new CPPoint(950, 450));
	}

	@Test
	public void testDemoUpdate()
	{
		demo.update();
		Mockito.verify(windowManager).processEvents();
		Mockito.verify(windowManager).display();
	}

	@Test
	public void testClosedWindow()
	{
		Mockito.when(windowManager.shouldClose()).thenReturn(true);
		demo.update();
		Mockito.verify(windowManager).deinit();
		Assert.assertTrue(demo.shouldClose());
	}

}
