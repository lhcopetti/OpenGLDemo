package com.copetti.threeD.window;

import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.system.MemoryUtil;

import com.copetti.threeD.math.CPPoint;
import com.copetti.threeD.math.CPSize;


public class GLFWWindowHandler implements WindowManager
{

	private long windowHandler;
	private GLFWKeyCallback glfwKeyCallback;

	public void init(int width, int height)
	{
		if (glfwInit() != GLFW_TRUE)
			throw new RuntimeException("Could not load GLFW Library");

		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

		windowHandler = glfwCreateWindow(width, height, "", MemoryUtil.NULL,
				MemoryUtil.NULL);

		if (windowHandler == MemoryUtil.NULL)
			throw new RuntimeException("Failed to create the GLFW Window");

		glfwMakeContextCurrent(windowHandler);
		glfwSwapInterval(1);

		glfwShowWindow(windowHandler);
	}

	public void deinit()
	{
		glfwDestroyWindow(windowHandler);
		glfwTerminate();
	}

	public void setTitle(String title)
	{
		glfwSetWindowTitle(windowHandler, title);
	}

	public CPSize getMonitorSize()
	{
		GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
		return new CPSize(vid.width(), vid.height());
	}

	public void setWindowPosition(CPPoint point)
	{
		glfwSetWindowPos(windowHandler, point.x(), point.y());
	}

	public void processEvents()
	{
		glfwPollEvents();
	}

	public void display()
	{
		glfwSwapBuffers(windowHandler);
	}

	public boolean shouldClose()
	{
		return glfwWindowShouldClose(windowHandler) == GLFW_TRUE;
	}

	@Override
	public void setKeyEventHandler(KeyEventHandler keyEventHandler)
	{
		glfwKeyCallback = new GLFWKeyCallback()
		{

			@Override
			public void invoke(long window, int key, int scancode, int action,
					int mods)
			{
				keyEventHandler.handleKeyEvent(key, scancode, action, mods);
			}
		};
		
		glfwSetKeyCallback(windowHandler, glfwKeyCallback);
	}

}
