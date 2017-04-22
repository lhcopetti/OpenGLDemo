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
			private final int STEP = 32;
			
			@Override
			public int width()
			{
				return image.getWidth() / STEP;
			}

			@Override
			public int height()
			{
				return image.getHeight() / STEP;
			}

			@Override
			public Vector3f get(int i, int j)
			{
				return new Vector3f(i, image.getRGB(j * STEP, i * STEP) & 0xFF, j);
			}
		});
	}
}
