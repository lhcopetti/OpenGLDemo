package com.copetti.threeD.opengl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.copetti.threeD.opengl.array.ArrayBufferFactory;


public class ArrayBufferFactoryTest
{

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Test
	public void creatingArrayBufferWithNegativeElementSize()
	{
		expected.expect(IllegalArgumentException.class);
		expected.expectMessage("Invalid elementSize: -1");
		ArrayBufferFactory.newArrayBuffer(-1, null);
	}

	@Test
	public void creatingArrayBufferWithElementSizeGreaterThanArrayLength()
	{
		expected.expect(IllegalArgumentException.class);
		expected.expectMessage(
				"Element size (4) is greater than array count (3)");
		ArrayBufferFactory.newArrayBuffer(4, new float[]
		{ 1, 2, 3 });
	}

	@Test
	public void arrayCountNotDivisibleByElementSize()
	{
		expected.expect(IllegalArgumentException.class);
		expected.expectMessage(
				"Array count (10) is not divisible by ElementSize (4)");
		ArrayBufferFactory.newArrayBuffer(4, new float[]
		{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
	}

	@Test
	public void indexBufferWithNullParam()
	{
		expected.expect(NullPointerException.class);
		expected.expectMessage("data is null");
		ArrayBufferFactory.newIndexBuffer(null);
	}

	@Test
	public void IntArrayCannotBeEmpty()
	{
		expected.expect(IllegalArgumentException.class);
		expected.expectMessage("The data array is empty");
		ArrayBufferFactory.newIndexBuffer(new int[] {});
	}
}
