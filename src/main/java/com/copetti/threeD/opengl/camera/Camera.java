package com.copetti.threeD.opengl.camera;

import java.nio.IntBuffer;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import com.copetti.threeD.math.Angle;

import lombok.Getter;

public class Camera {
	private @Getter Vector3f position = new Vector3f(0, 0, 2);
	private @Getter Vector3f up = new Vector3f(0, 1, 0);
	private @Getter Vector3f direction = new Vector3f(0, 0.f, -1.f).normalize();

	private float fov = Angle.fromDegree(60.f).asRadians();
	private float near = 0.1f;
	private float far = 1000.f;

	private float getAspect() {
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		long window = GLFW.glfwGetCurrentContext();
		GLFW.glfwGetWindowSize(window, w, h);
		return w.get() / (float) h.get();
	}

	public Matrix4f getViewMatrix() {
		return new Matrix4f().lookAt(position, new Vector3f(position).add(direction), up);
	}

	public Matrix4f getProjectionMatrix() {
		return new Matrix4f().perspective(fov, getAspect(), near, far);
	}

	public void moveFront(float distance) {
		position.add(new Vector3f(direction).mul(distance));
	}

	public void moveBack(float distance) {
		position.add(new Vector3f(direction).mul(-distance));
	}

	public void strafeLeft(float distance) {
		position.add(new Vector3f(direction).cross(new Vector3f(0, -1.f, 0).mul(distance)));
	}

	public void strafeRight(float distance) {
		position.add(new Vector3f(direction).cross(new Vector3f(0, +1.f, 0).mul(distance)));
	}

	public void rotate(Angle angle) {
		new Matrix3f().rotateY(angle.asRadians()).transform(direction);
	}
}
