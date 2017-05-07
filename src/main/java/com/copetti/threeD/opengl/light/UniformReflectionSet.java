package com.copetti.threeD.opengl.light;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.copetti.threeD.opengl.mesh.Mesh;


public class UniformReflectionSet
{

	private Mesh mesh;
	private Object object;
	private String suffix;

	public UniformReflectionSet(Mesh mesh, UniformApplicableByReflection object, String suffix)
	{
		this.mesh = mesh;
		this.object = object;
		this.suffix = suffix;
	}

	private static final String UNIFORM_PREFFIX = "u";

	public void apply()
	{
		for( Field f : getApplicableFields(object) )
		{
			f.setAccessible(true);
			applyFieldToMesh(f);
		}
	}

	public void applyFieldToMesh(Field f)
	{
		try
		{
			String uniformName = UNIFORM_PREFFIX + getUniformNameFromField(f)
					+ suffix;
			Object value;
			value = f.get(object);
			mesh.setUniform(uniformName, value);
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			System.out.println("Unable to operate on field named " + f.getName()
					+ ". " + e.getMessage());
		}
	}

	private List<Field> getApplicableFields(Object v)
	{
		return Arrays.asList(v.getClass().getDeclaredFields()).stream()
				.filter(this::notAStaticMethod).collect(Collectors.toList());
	}

	private boolean notAStaticMethod(Field f)
	{
		return (f.getModifiers() & Modifier.STATIC) == 0x00;
	}

	private String getUniformNameFromField(Field f)
	{
		String fieldName = f.getName().substring(0, 1).toUpperCase()
				+ f.getName().substring(1);
		return fieldName;
	}

}
