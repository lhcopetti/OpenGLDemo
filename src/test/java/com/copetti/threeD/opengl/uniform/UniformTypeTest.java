package com.copetti.threeD.opengl.uniform;

import static org.junit.Assert.assertEquals;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.junit.Test;


public class UniformTypeTest
{

	@Test
	public void testEnumFromClassObjectMatrix4f()
	{
		Object matrix = new Matrix4f().rotateZ((float) Math.toRadians(45));
		assertEquals(Matrix4f.class, UniformType.fromObject(matrix).getGlClass());
	}

	@Test
	public void testEnumFromClassObjectMatrix3f()
	{
		Object matrix = new Matrix3f().rotateX((float) Math.toRadians(45));
		assertEquals(Matrix3f.class, UniformType.fromObject(matrix).getGlClass());
	}
	
}
