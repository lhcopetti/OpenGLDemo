package com.copetti.threeD.game;

import java.util.function.BinaryOperator;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import com.copetti.threeD.input.InputEvent;
import com.copetti.threeD.input.InputHandler;
import com.copetti.threeD.input.InputManager;


public class KeyboardControlledAngles implements InputHandler
{

	enum Rotation
	{
		ROTATION_INCREMENT((x, y) -> {
			return x + y;
		}), ROTATION_DECREMENT((x, y) -> {
			return x - y;
		}), ROTATION_NONE((x, y) -> {
			return x;
		});

		private Rotation(BinaryOperator<Float> op)
		{
			this.operator = op;
		}

		private BinaryOperator<Float> operator;

		public float transform(float current, float differential)
		{
			return operator.apply(current, differential);
		}
	}

	private Rotation xRotation = Rotation.ROTATION_NONE;
	private Rotation yRotation = Rotation.ROTATION_NONE;

	private float xAngle;
	private float yAngle;

	private InputManager inputManager;

	public KeyboardControlledAngles()
	{
		inputManager = new InputManager();
		inputManager.addPressingListener(GLFW.GLFW_KEY_A, this::pressedA);
		inputManager.addPressingListener(GLFW.GLFW_KEY_D, this::pressedD);
		inputManager.addPressingListener(GLFW.GLFW_KEY_W, this::pressedW);
		inputManager.addPressingListener(GLFW.GLFW_KEY_S, this::pressedS);

		inputManager.addReleaseListener(GLFW.GLFW_KEY_D, this::releasedAorD);
		inputManager.addReleaseListener(GLFW.GLFW_KEY_A, this::releasedAorD);
		inputManager.addReleaseListener(GLFW.GLFW_KEY_W, this::releasedWorS);
		inputManager.addReleaseListener(GLFW.GLFW_KEY_S, this::releasedWorS);
	}

	@Override
	public void handleInput(InputEvent input)
	{
		inputManager.handleInput(input);
	}

	private void pressedA()
	{
		yRotation = Rotation.ROTATION_INCREMENT;
	}

	private void pressedD()
	{
		yRotation = Rotation.ROTATION_DECREMENT;
	}

	private void pressedW()
	{
		xRotation = Rotation.ROTATION_INCREMENT;
	}

	private void pressedS()
	{
		xRotation = Rotation.ROTATION_DECREMENT;
	}

	private void releasedAorD()
	{
		yRotation = Rotation.ROTATION_NONE;
	}

	private void releasedWorS()
	{
		xRotation = Rotation.ROTATION_NONE;
	}

	public void update(float deltaTime)
	{
		inputManager.pollInputEvents();
		xAngle = xRotation.transform(xAngle, deltaTime);
		yAngle = yRotation.transform(yAngle, deltaTime);
	}

	public Matrix4f getTransformationMatrix()
	{
		return new Matrix4f().rotateX(xAngle).rotateY(yAngle);
	}
}
