package com.copetti.threeD.opengl.command;

import lombok.Getter;
import static org.lwjgl.opengl.GL11.*;

public enum GLEnableContextType {

	DONT_DRAW_BACKWARDS(GL_CULL_FACE),
	THREE_D_RENDERING(GL_DEPTH_TEST);
	
	private GLEnableContextType(int mask) {
		this.glCommandMask = mask;
	}
	
	private @Getter int glCommandMask;
}
