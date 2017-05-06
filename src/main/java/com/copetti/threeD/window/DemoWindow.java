package com.copetti.threeD.window;

import com.copetti.threeD.glfw.KeyInputGLFWMapper;
import com.copetti.threeD.input.InputAction;
import com.copetti.threeD.input.InputEvent;
import com.copetti.threeD.input.InputHandler;
import com.copetti.threeD.math.CPPoint;
import com.copetti.threeD.math.CPSize;

import lombok.Getter;


public class DemoWindow
{

	private WindowManager window;
	private @Getter CPSize size;
	private boolean isClosed;

	private KeyEventHandler eventHandler;

	public DemoWindow(WindowManager window, CPSize windowSize)
	{
		this.window = window;
		this.size = windowSize;

		window.init(windowSize.width(), windowSize.height());
		isClosed = false;
	}

	public void setTitle(String title)
	{
		window.setTitle(title);
	}

	public void centerWindow()
	{
		if (isClosed) return;

		CPSize activeMonitorSize = window.getMonitorSize();
		CPPoint center = new CPPoint(
				(activeMonitorSize.width() - size.width()) / 2,
				(activeMonitorSize.height() - size.height()) / 2);
		window.setWindowPosition(center);
	}

	public boolean shouldClose()
	{
		return window.shouldClose();
	}

	public void deinit()
	{
		window.deinit();
	}

	public void update()
	{
		window.processEvents();
		window.display();
	}

	public void setInputHandler(InputHandler handler)
	{
		eventHandler = new KeyEventHandler()
		{

			@Override
			public void handleKeyEvent(int key, int scancode, int action,
					int mods)
			{
				InputAction inputAction = KeyInputGLFWMapper.getAction(action);
				handler.handleInput(
						new InputEvent(scancode, mods, inputAction, key));
			}
		};

		window.setKeyEventHandler(eventHandler);
	}

}
