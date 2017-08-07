package com.copetti.threeD.game.scene;

import java.util.List;

import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.opengl.command.SceneCommand;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class SceneConfiguration {

	@Getter boolean cameraEnabled;
	private @Getter List<SceneCommand> contextCommands;
	
	public void applyOnEnter(GameScene scene)
	{
		if (null != contextCommands)
			contextCommands.forEach(x -> x.execute(scene));
	}
	
	public void applyOnExit(GameScene scene)
	{
		if (null != contextCommands)
			contextCommands.forEach(x -> x.undo(scene));
	}
}
