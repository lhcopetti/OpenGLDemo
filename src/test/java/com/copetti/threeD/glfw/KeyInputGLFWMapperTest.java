package com.copetti.threeD.glfw;

import static org.junit.Assert.*;

import org.junit.Test;
import org.lwjgl.glfw.GLFW;

import com.copetti.threeD.input.InputAction;

public class KeyInputGLFWMapperTest {

	@Test
	public void testKeyInputActionMapper() {

		assertEquals(InputAction.PRESS, KeyInputGLFWMapper.getAction(GLFW.GLFW_PRESS));
		assertEquals(InputAction.RELEASE, KeyInputGLFWMapper.getAction(GLFW.GLFW_RELEASE));
	}

}
