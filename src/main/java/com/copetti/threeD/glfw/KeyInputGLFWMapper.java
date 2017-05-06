package com.copetti.threeD.glfw;

import org.lwjgl.glfw.GLFW;

import com.copetti.threeD.input.InputAction;

public class KeyInputGLFWMapper {

	public static InputAction getAction(int glfwAction) {
		if (glfwAction == GLFW.GLFW_RELEASE)
			return InputAction.RELEASE;
		else /* if(glfwAction == GLFW.GLFW_PRESS) */
			return InputAction.PRESS;
	}

}
