package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import com.copetti.threeD.classpath.Resource;
import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.game.KeyboardControlledAngles;
import com.copetti.threeD.image.ByteBufferImage;
import com.copetti.threeD.input.InputAction;
import com.copetti.threeD.input.InputEvent;
import com.copetti.threeD.math.CenterSupport;
import com.copetti.threeD.math.IndexUtils;
import com.copetti.threeD.math.grid.Grid2D;
import com.copetti.threeD.math.grid.Vector3fGridFlattener;
import com.copetti.threeD.opengl.mesh.Mesh;
import com.copetti.threeD.opengl.mesh.MeshBuilder;
import com.copetti.threeD.shapes.BufferImageHeightMapBuilder;
import com.copetti.threeD.shapes.Grid2DCompliantBuilder;


public class HeightMapScene extends GameScene
{

	private Mesh mesh;
	private KeyboardControlledAngles angleTransform;
	
	private String[] images;
	private int currentImageIndex;
	private ByteBufferImage currentImage;
	private int step = 16;
	
	public HeightMapScene() {
		images = new String[] {"gray/mountains.png", "gray/radial.jpg", "gray/scale.png", "gray/volcano.png" };
	}

	@Override
	public void handleInput(InputEvent input)
	{
		angleTransform.handleInput(input);
		
		if (input.getKey() == GLFW.GLFW_KEY_P && input.getAction() == InputAction.RELEASE)
			changeStep(step + 1);
		if (input.getKey() == GLFW.GLFW_KEY_M && input.getAction() == InputAction.RELEASE)
			changeStep(step -1 <= 0 ? 1 : step - 1);
		if (input.getKey() == GLFW.GLFW_KEY_COMMA && input.getAction() == InputAction.RELEASE)
			changeImage(currentImageIndex - 1 < 0 ? images.length - 1 : currentImageIndex - 1);
		if (input.getKey() == GLFW.GLFW_KEY_PERIOD && input.getAction() == InputAction.RELEASE)
			changeImage(currentImageIndex + 1 >= images.length ? 0 : currentImageIndex + 1);
	}

	@Override
	public void onEnter()
	{
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

		angleTransform = new KeyboardControlledAngles();
		setUpHeightMap(currentImageIndex, step);
	}
	
	public void setUpHeightMap(int imageIndex, int step)
	{
		currentImage = loadImage(images[imageIndex]);
		Grid2D<Vector3f> heightMap = loadHeightMap(currentImage, step);
		mesh = buildNewMesh(heightMap);	
	}
	
	public void changeImage(int newImageIndex)
	{
		currentImageIndex = newImageIndex;
		setUpHeightMap(newImageIndex, step);
	}
	
	private void changeStep(int newStep)
	{
		step = newStep;
		Grid2D<Vector3f> map = loadHeightMap(currentImage, newStep);
		mesh = buildNewMesh(map);
	}
	
	private Mesh buildNewMesh(Grid2D<Vector3f> map)
	{
		return MeshBuilder //
			.newBuilder() //
			.addVector3fAttribute("aPosition",
					new Vector3fGridFlattener().flatten(map)) //
			.setIndexBuffer(IndexUtils.connectAsGrid(map))
			.loadShaderFromResource("heightmap_shader").build();
	}
	
	private ByteBufferImage loadImage(String filepath)
	{
		File file;
		try {
			file = Resource.getFile(filepath);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage());
		}
		
		return ByteBufferImage.createUsingSTB(file);
	}
	
	private Grid2D<Vector3f> loadHeightMap(ByteBufferImage image, int step)
	{		
		Grid2D<Vector3f> heightMap = Grid2DCompliantBuilder.build(new BufferImageHeightMapBuilder(image, step));

		Iterator<Vector3f> iter = heightMap.iterator();
		Iterable<Vector3f> iterable = () -> iter;
		Stream<Vector3f> stream = StreamSupport.stream(iterable.spliterator(),
				false);

		CenterSupport.centerVector3f(heightMap);

		float distanceX = heightMap.width() - 1;
		float distanceY = (float) stream
				.mapToDouble(vector -> Math.abs(vector.y)).max().getAsDouble();
		float distanceZ = heightMap.height() - 1;
		for( Vector3f v : heightMap )
		{
			float x = v.x * (1 + .8f) / distanceZ;
			float y = v.y * (1 + -.5f) / distanceY;
			float z = v.z * (1 + .8f) / distanceX;
			v.set(x, y, z);
		}
		return heightMap;
	}

	@Override
	public void onExit()
	{
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}

	@Override
	public void update(float deltaTime)
	{
		angleTransform.update(deltaTime);
	}

	@Override
	public void draw()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.0f, 0.4f, 0.4f, 1.0f);

		mesh.setUniform("uWorld", angleTransform.getTransformationMatrix());
		mesh.draw();
	}
}
