package com.copetti.threeD.opengl.light;

import org.joml.Vector3f;

import lombok.Getter;
import lombok.Setter;


public class Material
{

	private @Getter Vector3f ambient = new Vector3f(1.f, 1.f, 1.f);
	private @Getter Vector3f diffuse = new Vector3f(1.f, 0.8f, 1.f);
	private @Getter Vector3f specular = new Vector3f(1.f, 1.f, 1.f);
	private @Getter @Setter float specularPower = 250.f;
	
}
