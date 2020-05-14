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
import java.util.function.BiConsumer;

/**
 * Loop for each item of a list.
 *
 * @param <I> items Type
 * @author lsafer
 * @version 0.1.3
 * @since 07-Dec-2019
 */
public class Foreach<I> extends Loop<BiConsumer<Foreach<I>, I>, I> {
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
	public Foreach(I[] array, BiConsumer<Foreach<I>, I> code) {
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
	public Foreach(boolean[] array, BiConsumer<Foreach<Boolean>, Boolean> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append((BiConsumer) code);
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @param code  first looping code
	 * @throws NullPointerException if the given 'array' or 'code' is null
	 */
	public Foreach(byte[] array, BiConsumer<Foreach<Byte>, Byte> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append((BiConsumer) code);
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @param code  first looping code
	 * @throws NullPointerException if the given 'array' or 'code' is null
	 */
	public Foreach(char[] array, BiConsumer<Foreach<Character>, Character> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append((BiConsumer) code);
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @param code  first looping code
	 * @throws NullPointerException if the given 'array' or 'code' is null
	 */
	public Foreach(double[] array, BiConsumer<Foreach<Double>, Double> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append((BiConsumer) code);
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @param code  first looping code
	 * @throws NullPointerException if the given 'array' or 'code' is null
	 */
	public Foreach(float[] array, BiConsumer<Foreach<Float>, Float> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append((BiConsumer) code);
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @param code  first looping code
	 * @throws NullPointerException if the given 'array' or 'code' is null
	 */
	public Foreach(int[] array, BiConsumer<Foreach<Integer>, Integer> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append((BiConsumer) code);
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @param code  first looping code
	 * @throws NullPointerException if the given 'array' or 'code' is null
	 */
	public Foreach(long[] array, BiConsumer<Foreach<Long>, Long> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append((BiConsumer) code);
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param array of items to be looped foreach
	 * @param code  first looping code
	 * @throws NullPointerException if the given 'array' or 'code' is null
	 */
	public Foreach(short[] array, BiConsumer<Foreach<Short>, Short> code) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(code, "code");
		this.append((BiConsumer) code);
		this.iterable = (List) Arrayz.asList(array);
	}

	/**
	 * Initialize this.
	 *
	 * @param iterable of items to be looped foreach
	 * @param code     first looping code
	 * @throws NullPointerException if the given 'iterable' or 'code' is null
	 */
	public Foreach(Iterable<I> iterable, BiConsumer<Foreach<I>, I> code) {
		Objects.requireNonNull(iterable, "iterable");
		Objects.requireNonNull(code, "code");
		this.append(code);
		this.iterable = iterable;
	}

	@Override
	public Foreach<I> append(BiConsumer<Foreach<I>, I> code) {
		Objects.requireNonNull(code, "code");
		return (Foreach<I>) this.append0(param -> code.accept(this, param));
	}

	@Override
	protected void loop() {
		for (I t : this.iterable)
			if (!this.next(t))
				break;
	}
}
