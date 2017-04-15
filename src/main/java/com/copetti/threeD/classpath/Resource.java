package com.copetti.threeD.classpath;

import java.util.Scanner;


public final class Resource
{

	public static String readAllText(String resourcePath)
	{
		Scanner scanner = new Scanner(Resource.class.getClassLoader()
				.getResourceAsStream(resourcePath), "UTF-8");
		String text = scanner.useDelimiter("\\A").next();
		scanner.close();
		return text;
	}
}
