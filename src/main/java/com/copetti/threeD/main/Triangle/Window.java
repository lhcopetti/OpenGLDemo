package com.copetti.threeD.main.Triangle;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;


public class Window
{

	private long windowHandle;
	private int height;
	private int width;

	public Window(int width, int height)
	{
		this.height = height;
		this.width = width;
	}

	public void setWindowTitle(String title)
	{
		GLFW.glfwSetWindowTitle(windowHandle, title);
	}

	public void init()
	{
		if (glfwInit() != GLFW_TRUE)
			throw new RuntimeException("Could not load GLFW Library");

		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

		windowHandle = glfwCreateWindow(width, height, "", MemoryUtil.NULL,
				MemoryUtil.NULL);

		if (windowHandle == MemoryUtil.NULL)
			throw new RuntimeException("Failed to create the GLFW Window");

		glfwMakeContextCurrent(windowHandle);
		glfwSwapInterval(1);

		glfwShowWindow(windowHandle);
	}

	public void centerWindow()
	{
		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(windowHandle, (vidMode.width() - width) / 2,
				(vidMode.height() - height) / 2);
	}

}
