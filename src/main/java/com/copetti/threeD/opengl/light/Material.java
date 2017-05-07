package com.copetti.threeD.opengl.light;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.joml.Vector3f;

import com.copetti.threeD.opengl.mesh.Mesh;

import lombok.Getter;
import lombok.Setter;


public class Material
{

	private static final String MATERIAL_PREFFIX = "u";
	private static final String MATERIAL_SUFFIX = "Material";

	private @Getter Vector3f ambient = new Vector3f(1.f, 1.f, 1.f);
	private @Getter Vector3f diffuse = new Vector3f(1.f, 0.8f, 1.f);
	private @Getter Vector3f specular = new Vector3f(1.f, 1.f, 1.f);
	private @Getter @Setter float specularPower = 250.f;

	public void applyAmbient(Mesh mesh) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException
	{
		applyFieldToMesh(mesh, getFieldByName("ambient"));
	}

	private Field getFieldByName(String name) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException
	{
		return Material.class.getDeclaredField(name);
	}

	public void apply(Mesh mesh)
	{
		for( Field f : getApplicableFields() )
			applyFieldToMesh(mesh, f);
	}

	public void applyFieldToMesh(Mesh m, Field f)
	{
		try
		{
			String uniformName = getUniformNameFromField(f);
			Object value;
			value = f.get(this);
			m.setUniform(uniformName, value);
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			System.out
					.println("Unable to operate on field named " + f.getName());
		}
	}

	private List<Field> getApplicableFields()
	{
		return Arrays.asList(Material.class.getDeclaredFields()).stream()
				.filter(f -> (f.getModifiers() & Modifier.STATIC) == 0x00)
				.collect(Collectors.toList());
	}

	private String getUniformNameFromField(Field f)
	{
		String fieldName = f.getName().substring(0, 1).toUpperCase()
				+ f.getName().substring(1);
		return MATERIAL_PREFFIX + fieldName + MATERIAL_SUFFIX;
	}

}
