package com.copetti.threeD.scenes;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.joml.Vector3f;

import com.copetti.threeD.classpath.Resource;
import com.copetti.threeD.game.GameScene;
import com.copetti.threeD.game.KeyboardControlledAngles;
import com.copetti.threeD.input.InputEvent;
import com.copetti.threeD.math.CenterSupport;
import com.copetti.threeD.math.IndexUtils;
import com.copetti.threeD.math.grid.Grid2D;
import com.copetti.threeD.math.grid.Vector3fGridFlattener;
import com.copetti.threeD.opengl.mesh.Mesh;
import com.copetti.threeD.opengl.mesh.MeshBuilder;
import com.copetti.threeD.shapes.BufferedImageHeightMapBuilder;


public class HeightMapScene implements GameScene
{

	private Mesh mesh;
	private KeyboardControlledAngles angleTransform;
	private BufferedImage image;

	@Override
	public void handleInput(InputEvent input)
	{
		angleTransform.handleInput(input);
	}

	@Override
	public void onEnter()
	{
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

		String montanhaImage = "gray/montanha.jpg";
		try
		{
			image = Resource.loadBufferedImage(montanhaImage);
		}
		catch (IOException e)
		{
			throw new RuntimeException(
					"Could not load Image: " + montanhaImage);
		}

		Grid2D<Vector3f> heightMap = BufferedImageHeightMapBuilder.build(image);

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

		angleTransform = new KeyboardControlledAngles();

		mesh = MeshBuilder //
				.newBuilder() //
				.addVector3fAttribute("aPosition",
						new Vector3fGridFlattener().flatten(heightMap)) //
				.setIndexBuffer(IndexUtils.connectAsGrid(heightMap))
				.loadShaderFromResource("heightmap_shader").build();

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
