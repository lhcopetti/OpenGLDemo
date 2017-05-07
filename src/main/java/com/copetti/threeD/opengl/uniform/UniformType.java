package com.copetti.threeD.opengl.uniform;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.stream.Stream;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

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

	},
	OneFloat(Float.class)
	{

		public void setUniform(int id, Object value)
		{
			float f = (float) value;
			glUniform1f(id, f);
		}
	},
	OneBoolean(Boolean.class)
	{

		@Override
		public void setUniform(int id, Object value)
		{
			glUniform1i(id,
					((boolean) value) ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
		}
	},
	Vector3f(Vector3f.class)
	{

		@Override
		public void setUniform(int id, Object value)
		{
			Vector3f v = (Vector3f) value;
			glUniform3f(id, v.x, v.y, v.z);
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
