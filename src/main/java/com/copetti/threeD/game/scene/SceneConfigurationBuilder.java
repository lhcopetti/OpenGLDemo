package com.copetti.threeD.game.scene;

import java.util.*;

import com.copetti.threeD.opengl.command.*;

public class SceneConfigurationBuilder {
	private boolean cameraEnabled;
	private List<SceneCommand> contextCommands;

	public SceneConfigurationBuilder() {
		cameraEnabled = false;
		contextCommands = new ArrayList<>();
	}

	public static SceneConfiguration defaults() {
		return new SceneConfiguration();
	}

	public SceneConfigurationBuilder enableCamera() {
		cameraEnabled = true;
		return this;
	}

	public SceneConfigurationBuilder enable3D() {
		contextCommands.add(new GLContextSceneCommand(GLEnableContextType.THREE_D_RENDERING));
		return this;
	}

	public SceneConfigurationBuilder enableBackwardDrawing() {
		contextCommands.add(new GLContextSceneCommand(GLEnableContextType.DONT_DRAW_BACKWARDS));
		return this;
	}
	
	public SceneConfigurationBuilder enableLineMode() {
		contextCommands.add(new GLPolygonModeCommand());
		return this;
	}

	public static SceneConfigurationBuilder newBuilder() {
		return new SceneConfigurationBuilder();
	}

	public SceneConfiguration build() {
		return new SceneConfiguration(cameraEnabled, contextCommands);
	}
}
