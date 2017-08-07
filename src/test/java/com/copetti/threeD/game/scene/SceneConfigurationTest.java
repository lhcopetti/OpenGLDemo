package com.copetti.threeD.game.scene;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.*;
import org.mockito.Mock;

import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.opengl.command.SceneCommand;

public class SceneConfigurationTest {

	private @Mock SceneCommand mock1;
	private @Mock SceneCommand mock2;
	private @Mock GameScene scene;

	@Before
	public void before() {
		mock1 = mock(SceneCommand.class);
		mock2 = mock(SceneCommand.class);
		scene = mock(GameScene.class);
	}

	@Test
	public void testCameraIsEnabled() {
		SceneConfiguration config = new SceneConfiguration(true, null);
		assertTrue(config.isCameraEnabled());
	}

	@Test
	public void testConfigWillExecuteCommandsOnEnter() {
		SceneConfiguration config = new SceneConfiguration(true, Arrays.asList(mock1, mock2));
		config.applyOnEnter(scene);
		verify(mock1).execute(scene);
		verify(mock2).execute(scene);
	}

	@Test
	public void testConfigWillUndoReversibleCommands() {
		SceneConfiguration config = new SceneConfiguration(true, Arrays.asList(mock1, mock2));
		config.applyOnExit(scene);
		verify(mock1).undo(scene);
		verify(mock2).undo(scene);
	}
	
	@Test
	public void configShouldWorkForNullLists() {
		SceneConfiguration config = new SceneConfiguration(true, null);
		config.applyOnEnter(scene);
		config.applyOnExit(scene);
		
	}
}
