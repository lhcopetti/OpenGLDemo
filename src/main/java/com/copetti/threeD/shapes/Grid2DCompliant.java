package com.copetti.threeD.shapes;


public interface Grid2DCompliant<T>
{

	int width();
	int height();
	
	T get(int i, int j);
}
