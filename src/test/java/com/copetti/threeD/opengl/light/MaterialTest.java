package com.copetti.threeD.opengl.light;

import static org.mockito.Mockito.verify;

import org.joml.Vector3f;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import com.copetti.threeD.opengl.mesh.Mesh;


public class MaterialTest
{

	private Material material;
	private Mesh mock;

	private Vector3f ambient = new Vector3f(5.f, 10.f, 15.f);
	private Vector3f diffuse = new Vector3f(1.2f, 1.8f, 12.f);
	private Vector3f specular = new Vector3f(1.9f, 1.8f, 1.7f);
	private float specularPower = 10.f;

	@Before
	public void before()
	{
		material = new Material();
		material.getAmbient().set(ambient);
		material.getDiffuse().set(diffuse);
		material.getSpecular().set(specular);
		material.setSpecularPower(specularPower);
		mock = mock(Mesh.class);
	}

	@Test
	public void testApplyMaterial()
	{
		material.apply(mock);

		verify(mock).setUniform("uAmbientMaterial", ambient);
		verify(mock).setUniform("uDiffuseMaterial", diffuse);
		verify(mock).setUniform("uSpecularMaterial", specular);
		verify(mock).setUniform("uSpecularPowerMaterial", specularPower);
	}

}
