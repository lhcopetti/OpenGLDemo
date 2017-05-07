package com.copetti.threeD.opengl.light;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.joml.Vector3f;
import org.junit.Before;
import org.junit.Test;

import com.copetti.threeD.opengl.mesh.Mesh;


public class DirectionalLightTest
{

	private DirectionalLight light;
	private Mesh mock;

	private Vector3f ambient = new Vector3f(5.f, 10.f, 15.f);
	private Vector3f diffuse = new Vector3f(1.2f, 1.8f, 12.f);
	private Vector3f specular = new Vector3f(1.9f, 1.8f, 1.7f);
	private Vector3f position = new Vector3f(5.f, 3.8f, 1.3f);

	@Before
	public void before()
	{
		light = new DirectionalLight();
		light.getAmbient().set(ambient);
		light.getDiffuse().set(diffuse);
		light.getSpecular().set(specular);
		light.getPosition().set(position);
		mock = mock(Mesh.class);
	}

	@Test
	public void testApplyLightToMesh()
	{
		light.apply(mock);

		verify(mock).setUniform("uAmbientLight", ambient);
		verify(mock).setUniform("uDiffuseLight", diffuse);
		verify(mock).setUniform("uSpecularLight", specular);
		verify(mock).setUniform("uPositionLight", position);
	}

}
