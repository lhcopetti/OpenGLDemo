package com.copetti.threeD.opengl.command;

import com.copetti.threeD.game.GameScene;

import lombok.*;

@AllArgsConstructor
public abstract class SceneCommand {

	protected boolean needsReversing;
	
	public boolean execute(GameScene scene)
	{
		return doExecute(scene);
	}
	
	public boolean undo(GameScene scene)
	{
		if (needsReversing)
			return doUndo(scene);
		
		return true;
	}
	
	protected abstract boolean doExecute(GameScene scene);
	protected abstract boolean doUndo(GameScene scene);
}
