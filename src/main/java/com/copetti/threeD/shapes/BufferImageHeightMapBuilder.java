package com.copetti.threeD.shapes;

import java.nio.ByteBuffer;

import org.joml.Vector3f;

import com.copetti.threeD.image.ByteBufferImage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BufferImageHeightMapBuilder implements Grid2DCompliant<Vector3f> {

	private @Getter ByteBufferImage image;
	private int step;

	@Override
	public int width() {
		return image.getWidth() / step;
	}

	@Override
	public int height() {
		return image.getHeight() / step;
	}

	@Override
	public Vector3f get(int i, int j) {

		ByteBuffer buffer = image.getImageBuffer();
		int index = image.getComponentsPerPixel() * (image.getWidth() * i + j);
		return new Vector3f(i, 0xFF - buffer.get(step * index) & 0xFF, j);
	}

}
