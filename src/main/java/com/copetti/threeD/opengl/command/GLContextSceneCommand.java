package com.copetti.threeD.opengl.command;

import com.copetti.threeD.game.GameScene;
import static org.lwjgl.opengl.GL11.*;

public class GLContextSceneCommand extends SceneCommand {

	
	private GLEnableContextType commandMask;

	public GLContextSceneCommand(GLEnableContextType commandMask) {
		super(true);
		this.commandMask = commandMask;
	}

	@Override
	public boolean doExecute(GameScene scene) {
		glEnable(commandMask.getGlCommandMask());
		return true;
	}

	@Override
	public boolean doUndo(GameScene scene) {
		glDisable(commandMask.getGlCommandMask());
		return true;
	}

}
