package com.copetti.threeD.game;

import java.util.function.BinaryOperator;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import com.copetti.threeD.input.InputAction;
import com.copetti.threeD.input.InputEvent;
import com.copetti.threeD.input.InputHandler;


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

	@Override
	public void handleInput(InputEvent input)
	{
		if (input.getAction() == InputAction.RELEASE)
			handleKeyPress(input);
		else
			if (input.getAction() == InputAction.RELEASE) //
				handleKeyRelease(input);
	}

	private void handleKeyPress(InputEvent input)
	{
		switch (input.getKey())
		{
		case GLFW.GLFW_KEY_A:
			yRotation = Rotation.ROTATION_INCREMENT;
			break;
		case GLFW.GLFW_KEY_D:
			yRotation = Rotation.ROTATION_DECREMENT;
			break;
		case GLFW.GLFW_KEY_W:
			xRotation = Rotation.ROTATION_INCREMENT;
			break;
		case GLFW.GLFW_KEY_S:
			xRotation = Rotation.ROTATION_DECREMENT;
			break;
		}
	}

	private void handleKeyRelease(InputEvent input)
	{
		switch (input.getKey())
		{
		case GLFW.GLFW_KEY_A:
		case GLFW.GLFW_KEY_D:
			yRotation = Rotation.ROTATION_NONE;
		case GLFW.GLFW_KEY_W:
		case GLFW.GLFW_KEY_S:
			xRotation = Rotation.ROTATION_NONE;
			break;
		}
	}
	
	public void update(float deltaTime)
	{
		xAngle = xRotation.transform(xAngle, deltaTime);
		yAngle = yRotation.transform(yAngle, deltaTime);
	}
	
	public Matrix4f getTransformationMatrix()
	{
		return new Matrix4f().rotateX(xAngle).rotateY(yAngle);
	}
}
