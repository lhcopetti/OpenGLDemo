package com.copetti.threeD.window;

import com.copetti.threeD.math.CPPoint;
import com.copetti.threeD.math.CPSize;


public interface WindowManager
{

	public void init(int width, int height);
	public void deinit();

	public void setTitle(String title);
	
	public void processEvents();
	public void display();

	public void setWindowPosition(CPPoint point);

	public CPSize getMonitorSize();
	
	public boolean shouldClose();
	
	public void setKeyEventHandler(KeyEventHandler keyEventHandler);
	
}
