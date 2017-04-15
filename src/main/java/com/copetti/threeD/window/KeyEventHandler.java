package com.copetti.threeD.window;

@FunctionalInterface
public interface KeyEventHandler
{

	void handleKeyEvent(int key, int scancode, int action, int mods);
}
