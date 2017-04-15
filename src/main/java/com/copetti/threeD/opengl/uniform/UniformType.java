package com.copetti.threeD.opengl.uniform;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.stream.Stream;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UniformType
{
	Matrix4f(org.joml.Matrix4f.class)
	{

		@Override
		public void setUniform(int id, Object value)
		{
			FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
			Matrix4f matrix = (Matrix4f) value;
			matrix.get(buffer);
			glUniformMatrix4fv(id, false, buffer);
		}
	},
	Matrix3f(org.joml.Matrix3f.class)
	{

		@Override
		public void setUniform(int id, Object value)
		{
			Matrix3f matrix = (Matrix3f) value;
			FloatBuffer buffer = BufferUtils.createFloatBuffer(9);
			matrix.get(buffer);
			glUniformMatrix3fv(id, false, buffer);
		}

	};

	private @Getter Class<?> glClass;

	public abstract void setUniform(int id, Object value);

	public static UniformType fromObject(Object object)
	{
		return Stream.of(UniformType.values()) //
				.filter(x -> x.getGlClass().equals(object.getClass())) //
				.findFirst() //
				.orElseThrow(() -> //
		new IllegalArgumentException("Invalid object class: "
				+ object.getClass().getCanonicalName()));
	}
}
