package com.copetti.threeD.opengl.uniform;

import lombok.Getter;


public class Uniform
{

	private @Getter UniformType type;
	private @Getter Object value;

	public Uniform(Object value)
	{
		this.value = value;
		this.type = UniformType.fromObject(value);
	}
}
