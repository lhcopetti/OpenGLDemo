package com.copetti.threeD.opengl.light;

import org.joml.Vector3f;

import com.copetti.threeD.opengl.mesh.Mesh;

import lombok.Getter;


public class DirectionalLight implements UniformApplicableByReflection
{

	private @Getter Vector3f position = new Vector3f(0.f, 0.f, 2.f);
	private @Getter Vector3f ambient = new Vector3f(.1f, .1f, .1f);
	private @Getter Vector3f diffuse = new Vector3f(1.f, .8f, 1.f);
	private @Getter Vector3f specular = new Vector3f(1.f, 1.f, 1.f);

	public void apply(Mesh mesh)
	{
		new UniformReflectionSet(mesh, this, "Light").apply();
	}
}
