package com.copetti.threeD.image;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ByteBufferImage {

	private @Getter int width;
	private @Getter int height;
	private @Getter int componentsPerPixel;
	private @Getter ByteBuffer imageBuffer;
	
	public static ByteBufferImage createUsingSTB(File file)
	{
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer components = BufferUtils.createIntBuffer(1);
		
		ByteBuffer stbImage = org.lwjgl.stb.STBImage.stbi_load(file.getAbsolutePath(), width, height, components, 0);
		
		return new ByteBufferImage(width.get(0), height.get(0), components.get(0), stbImage);
	}
}
