package com.copetti.threeD.input;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public class InputEvent
{

	private @Getter int scanCode;
	private @Getter int mods;
	private @Getter int action;
	private @Getter int key;

}
