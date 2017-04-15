package com.copetti.threeD.math;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode
public class CPPoint
{

	private int x;
	private int y;

	public CPPoint(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int x()
	{
		return x;
	}

	public int y()
	{
		return y;
	}
}
