package com.copetti.threeD.input;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
public class InputEvent
{

	private @Getter int scanCode;
	private @Getter int mods;
	private @Getter InputAction action;
	private @Getter int key;

	public static InputEvent pressing(InputEvent ev)
	{
		return changeAction(ev, InputAction.PRESSING);
	}

	public static InputEvent release(InputEvent ev)
	{
		return changeAction(ev, InputAction.RELEASE);
	}
	
	private static InputEvent changeAction(InputEvent ev, InputAction action)
	{
		return new InputEvent(ev.scanCode, ev.mods, action,
				ev.key);		
	}

}
