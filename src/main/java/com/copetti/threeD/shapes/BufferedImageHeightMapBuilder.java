package com.copetti.threeD.shapes;

import java.awt.image.BufferedImage;

import org.joml.Vector3f;

import com.copetti.threeD.math.grid.Grid2D;


public class BufferedImageHeightMapBuilder
{

	public static Grid2D<Vector3f> build(BufferedImage image)
	{
		return Grid2DCompliantBuilder.build(new Grid2DCompliant<Vector3f>()
		{

			@Override
			public int width()
			{
				return image.getWidth();
			}

			@Override
			public int height()
			{
				return image.getHeight();
			}

			@Override
			public Vector3f get(int i, int j)
			{
				return new Vector3f(i, image.getRGB(j, i) & 0xFF, j);
			}
		});
	}
}
