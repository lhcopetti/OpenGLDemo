package com.copetti.threeD.game.scene;

public class SceneConfigurationBuilder
{
	private SceneConfiguration config;
	
	public SceneConfigurationBuilder()
	{
		config = new SceneConfiguration();
	}
	
	public static SceneConfiguration defaults() {
		return new SceneConfiguration();
	}
	
	public SceneConfigurationBuilder enableCamera() {
		config.cameraEnabled = true;
		return this;
	}
	
	public static SceneConfigurationBuilder newBuilder() {
		return new SceneConfigurationBuilder();
	}
	
	public SceneConfiguration build()
	{
		return config;
	}
}
