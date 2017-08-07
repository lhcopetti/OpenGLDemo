package com.copetti.threeD.opengl.command;

import com.copetti.threeD.game.GameScene;
import static org.lwjgl.opengl.GL11.*;

public class GLPolygonModeCommand extends SceneCommand {

	public GLPolygonModeCommand() {
		super(true);
	}

	@Override
	protected boolean doExecute(GameScene scene) {
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		return true;
	}

	@Override
	protected boolean doUndo(GameScene scene) {
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		return true;
	}

}
