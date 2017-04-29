package com.copetti.threeD.classpath;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Scanner;

import javax.imageio.ImageIO;


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

	public static BufferedImage loadBufferedImage(String resourcePath)
			throws IOException
	{
		return ImageIO.read(resourceAsStream(resourcePath));
	}

	public static InputStream resourceAsStream(String resourcePath)
	{
		return Resource.class.getClassLoader()
				.getResourceAsStream(resourcePath);
	}
	
	public static File getFile(String filePath) throws URISyntaxException
	{
		return new File(Resource.class.getClassLoader().getResource(filePath).toURI());
	}

}
