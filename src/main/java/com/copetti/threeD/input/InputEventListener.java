package com.copetti.threeD.input;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public class InputEventListener
{

	private @Getter InputAction expectsAction;
	private @Getter int expectsKey;

	private @Getter InputCallback handler;

	public boolean fire(InputEvent input)
	{
		if (!handlesInput(input)) return false;

		handler.handle();
		return true;
	}

	private boolean handlesInput(InputEvent input)
	{
		if (expectsAction != input.getAction()) return false;

		if (expectsKey != input.getKey()) return false;

		return true;
	}
}
