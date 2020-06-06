/*
 *	Copyright 2020 Cufy
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package cufy.concurrent;

import cufy.util.Arrayz;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Loop for each item of a list.
 *
 * @param <I> items Type
 * @author lsafer
 * @version 0.1.5
 * @since 07-Dec-2019
 */
public class Foreach<I> extends Loop<Foreach.Code<I>> {
	/**
	 * List of items to loop.
	 */
	final protected Iterable<I> iterable;

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @throws NullPointerException if the given array is null
	 */
	public Foreach(I[] array) {
		Objects.requireNonNull(array, "array");
		this.iterable = Arrays.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @throws NullPointerException if the given array is null
	 */
	public Foreach(boolean[] array) {
		Objects.requireNonNull(array, "array");
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @throws NullPointerException if the given array is null
	 */
	public Foreach(byte[] array) {
		Objects.requireNonNull(array, "array");
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @throws NullPointerException if the given array is null
	 */
	public Foreach(char[] array) {
		Objects.requireNonNull(array, "array");
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @throws NullPointerException if the given array is null
	 */
	public Foreach(double[] array) {
		Objects.requireNonNull(array, "array");
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @throws NullPointerException if the given array is null
	 */
	public Foreach(float[] array) {
		Objects.requireNonNull(array, "array");
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @throws NullPointerException if the given array is null
	 */
	public Foreach(int[] array) {
		Objects.requireNonNull(array, "array");
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @throws NullPointerException if the given array is null
	 */
	public Foreach(long[] array) {
		Objects.requireNonNull(array, "array");
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @throws NullPointerException if the given array is null
	 */
	public Foreach(short[] array) {
		Objects.requireNonNull(array, "array");
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param iterable of items to be looped foreach
	 * @throws NullPointerException if the given iterable is null
	 */
	public Foreach(Iterable<I> iterable) {
		Objects.requireNonNull(iterable, "iterable");
		this.iterable = iterable;
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @param code  first looping code
	 * @throws NullPointerException if the given 'array' or 'code' is null
	 */
	public Foreach(I[] array, Code<I> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append(code);
		this.iterable = Arrays.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @param code  first looping code
	 * @throws NullPointerException if the given 'array' or 'code' is null
	 */
	public Foreach(boolean[] array, Code<Boolean> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append((Code) code);
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @param code  first looping code
	 * @throws NullPointerException if the given 'array' or 'code' is null
	 */
	public Foreach(byte[] array, Code<Byte> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append((Code) code);
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @param code  first looping code
	 * @throws NullPointerException if the given 'array' or 'code' is null
	 */
	public Foreach(char[] array, Code<Character> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append((Code) code);
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @param code  first looping code
	 * @throws NullPointerException if the given 'array' or 'code' is null
	 */
	public Foreach(double[] array, Code<Double> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append((Code) code);
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @param code  first looping code
	 * @throws NullPointerException if the given 'array' or 'code' is null
	 */
	public Foreach(float[] array, Code<Float> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append((Code) code);
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @param code  first looping code
	 * @throws NullPointerException if the given 'array' or 'code' is null
	 */
	public Foreach(int[] array, Code<Integer> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append((Code) code);
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @param code  first looping code
	 * @throws NullPointerException if the given 'array' or 'code' is null
	 */
	public Foreach(long[] array, Code<Long> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append((Code) code);
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @param code  first looping code
	 * @throws NullPointerException if the given 'array' or 'code' is null
	 */
	public Foreach(short[] array, Code<Short> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append((Code) code);
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param iterable of items to be looped foreach
	 * @param code     first looping code
	 * @throws NullPointerException if the given 'iterable' or 'code' is null
	 */
	public Foreach(Iterable<I> iterable, Code<I> code) {
		Objects.requireNonNull(iterable, "iterable");
		Objects.requireNonNull(code, "code");
		this.append(code);
		this.iterable = iterable;
	}

	@Override
	protected void loop() {
		for (I t : this.iterable)
			if (!this.next(t))
				break;
	}

	/**
	 * A loop-code for {@code Foreach} loops.
	 *
	 * @param <I> the type of iterating items
	 */
	@FunctionalInterface
	public interface Code<I> extends Loop.Code<Foreach> {
		@Override
		default void run(Foreach loop, Object item) {
			this.onRun(loop, (I) item);
		}

		/**
		 * Perform this {@code Foreach} loop-code with the given item. Get called when a {@code Foreach} loop is executing its code and this code is
		 * added to its code.
		 *
		 * @param loop the loop that executed this code
		 * @param item the current item in the foreach-iteration
		 * @throws NullPointerException if the given 'loop' is null
		 */
		void onRun(Foreach<I> loop, I item);
	}
}
