package com.copetti.threeD.math.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class Grid2DIteratorTest
{

	private Grid2D<Integer> grid;

	@Before
	public void setupGrid()
	{
		int value = 0;
		grid = new Grid2D<>(3, 2);
		for( int i = 0; i < grid.height(); i++ )
			for( int j = 0; j < grid.width(); j++ )
				grid.set(i, j, value++);
	}

	private <T> List<T> collect(Iterator<T> iterator)
	{
		List<T> values = new ArrayList<T>();
		while (iterator.hasNext())
			values.add(iterator.next());

		return values;
	}

	@Test
	public void testIteratorHasNextWhenCreated()
	{
		Grid2D<Integer> grid = new Grid2D<>(1, 1);
		Iterator<Integer> iter = grid.iterator();
		Assert.assertTrue(iter.hasNext());
	}

	@Test
	public void testIteratorConsumesOnNextCall()
	{
		Grid2D<Integer> grid = new Grid2D<>(1, 1);
		Iterator<Integer> iter = grid.iterator();
		iter.next();
		Assert.assertFalse(iter.hasNext());
	}

	@Test
	public void testIteratorHasNextOnNotSquaredGrid()
	{
		Grid2D<Integer> grid = new Grid2D<>(2, 3);
		Iterator<Integer> iter = grid.iterator();
		for( int i = 0; i < 6; i++ )
			iter.next();
		Assert.assertFalse(iter.hasNext());
	}

	@Test
	public void testIteratorNextSupplyOrder()
	{
		List<Integer> values = Arrays.asList(0, 1, 2, 3, 4, 5);
		Assert.assertThat(collect(grid.iterator()), is(values));
	}
}
