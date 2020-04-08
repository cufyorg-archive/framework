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
package cufy.convert;

import com.sun.nio.sctp.IllegalReceiveException;
import cufy.lang.Recurse;
import cufy.lang.Static;
import cufy.meta.MetaFamily;
import cufy.meta.MetaReference;

import java.lang.reflect.Array;
import java.util.*;

/**
 * A converter that has a basic converting methods.
 *
 * <ul>
 *     Methods:
 *     <li>
 *         <b>Arrays</b>
 *         <ul>
 *
 *             <li>{@link #array_array}</li>
 *             <li>{@link #array_collection}</li>
 *             <li>{@link #array_list}</li>
 *             <li>{@link #collection_array}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>Collections</b>
 *         <ul>
 *             <li>{@link #collection_array}</li>
 *             <li>{@link #collection_collection}</li>
 *             <li>{@link #collection_list}</li>
 *             <li>{@link #array_collection}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <ul>
 *             <b>Lists</b>
 *             <li>{@link #array_list}</li>
 *             <li>{@link #collection_list}</li>
 *         </ul>
 *         <ul>
 *             <b>Maps</b>
 *             <li>{@link #map_map}</li>
 *         </ul>
 *     </li>
 *         <ul>
 *             <b>Numbers</b>
 *             <li>{@link #number_byte}</li>
 *             <li>{@link #number_double}</li>
 *             <li>{@link #number_float}</li>
 *             <li>{@link #number_integer}</li>
 *             <li>{@link #number_long}</li>
 *             <li>{@link #number_short}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <ul>
 *             <b>Objects</b>
 *             <li>{@link #object_string}</li>=
 *             <li>{@link #recurse_object}</li>
 *             <li>{@link #string_object}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <ul>
 *             <b>Strings</b>
 *             <li>{@link #string_object}</li>
 *             <li>{@link #object_string}</li>
 *         </ul>
 *     </li>
 * </ul>
 *
 * @author lsafer
 * @version 0.1.2
 * @since 31-Aug-2019
 */
public class BaseConverter extends AbstractConverter {
	/**
	 * The global instance to avoid unnecessary instancing.
	 */
	@MetaReference
	final public static BaseConverter global = new BaseConverter();

	{
		DEBUGGING = false;
	}

	/**
	 * Inherit only.
	 */
	@Static
	protected BaseConverter() {
	}

	/**
	 * Array => Array
	 * <br/>
	 * Replace the elements on the {@link ConvertArguments#output} from the given arguments. All from the given {@link ConvertArguments#input} after
	 * converting them using this class. If the output is null, or have different length, or have a type different than the class at {@link
	 * ConvertArguments#outputClazz}. Then it will be replaced with a new array with the class of {@link
	 * ConvertArguments#outputClazz}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'arguments' or 'arguments.input' is null
	 * @throws IllegalArgumentException if the given 'input' isn't an array. Or if the given 'inputClass', or 'outputClass' are not a array
	 *                                  classes
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = Object[].class,
					in = {boolean[].class,
						  byte[].class,
						  char[].class,
						  double[].class,
						  float[].class,
						  int[].class,
						  long[].class,
						  short[].class
					}),
			output = @MetaFamily(
					subIn = Object[].class,
					in = {boolean[].class,
						  byte[].class,
						  char[].class,
						  double[].class,
						  float[].class,
						  int[].class,
						  long[].class,
						  short[].class
					}))
	protected void array_array(ConvertArguments<Object, Object> arguments) {
		if (DEBUGGING) {
			Objects.requireNonNull(arguments, "arguments");
			Objects.requireNonNull(arguments.input, "arguments.input");

			if (!arguments.input.getClass().isArray())
				throw new IllegalArgumentException(arguments.input + " is not an array");
			if (!arguments.inputClazz.getKlass().isArray())
				throw new IllegalArgumentException(arguments.inputClazz.getKlass() + " is not a class for arrays");
			if (!arguments.outputClazz.getKlass().isArray())
				throw new IllegalArgumentException(arguments.outputClazz.getKlass() + " is not a class for arrays");
		}

		int length = Array.getLength(arguments.input);

		//Replace if the output is incompatible
		if (!arguments.outputClazz.getKlass().isInstance(arguments.output) || Array.getLength(arguments.output) != length)
			//output presented is not valid
			arguments.output = Array.newInstance(arguments.outputClazz.getKlass().getComponentType(), length);

		//converting foreach element
		for (int i = 0; i < length; i++) {
			//init
			Object outputElement = Array.get(arguments.output, i);
			Object inputElement = Array.get(arguments.input, i);

			//DyNaMiC cOnVeRsIoN _/-\_/-\_/- :0 ~ MA-GI-KKU
			outputElement = this.convert(new ConvertArguments(arguments, inputElement, outputElement, 0));

			//Replace the element at the output with the converted element from the input
			Array.set(arguments.output, i, outputElement);
		}
	}

	/**
	 * Array => Collection
	 * <br/>
	 * Replace the elements on the {@link ConvertArguments#output} from the given arguments. All from the given {@link ConvertArguments#input}. If
	 * the output is null, or have a type different than the class at {@link ConvertArguments#outputClazz}. Then the output will be replaced with a
	 * new output with the class of {@link ConvertArguments#outputClazz}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException         if the given 'arguments' or 'arguments.input' is null
	 * @throws IllegalArgumentException     if the given 'input' isn't an array. Or if the given 'inputClass' is not an array class. Or if the given
	 *                                      'outputClass' is not a collection class
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the output collection
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = Object[].class,
					in = {boolean[].class,
						  byte[].class,
						  char[].class,
						  double[].class,
						  int[].class,
						  float[].class,
						  long[].class,
						  short[].class,
					}),
			output = @MetaFamily(
					subIn = Collection.class,
					subOut = List.class
			))
	protected void array_collection(ConvertArguments<Object, Collection> arguments) throws ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(arguments, "arguments");
			Objects.requireNonNull(arguments.input, "arguments.input");

			if (!arguments.input.getClass().isArray())
				throw new IllegalArgumentException(arguments.input + " is not an array");
			if (!arguments.inputClazz.getKlass().isArray())
				throw new IllegalArgumentException(arguments.inputClazz.getKlass() + " is not a class for arrays");
			if (!Collection.class.isAssignableFrom(arguments.outputClazz.getKlass()))
				throw new IllegalArgumentException(arguments.outputClazz.getKlass() + " is not a class for collections");
		}

		int length = Array.getLength(arguments.input);

		//Replace if the output is incompatible
		if (!arguments.outputClazz.getKlass().isInstance(arguments.output))
			//output presented is not valid
			arguments.output = arguments.outputClazz.getKlass().getConstructor().newInstance();
		else //fresh start
			arguments.output.clear();

		for (int i = 0; i < length; i++) {
			//init
			Object inputElement = Array.get(arguments.input, i);
			Object outputElement;

			//DyNaMiC cOnVeRsIoN _/-\_/-\_/- :0 ~ MA-GI-KKU
			outputElement = this.convert(new ConvertArguments<>(arguments, inputElement, 0));

			//Set the elements from the input
			arguments.output.add(outputElement);
		}
	}

	/**
	 * Array => List
	 * <br/>
	 * Replace the elements on the {@link ConvertArguments#output} from the given arguments. All from the given {@link ConvertArguments#input}. If
	 * the output is null, or have a type different than the class at {@link ConvertArguments#outputClazz}. Then the output will be replaced with a
	 * new output with the class of {@link ConvertArguments#outputClazz}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException         if the given 'arguments' or 'arguments.input' is null
	 * @throws IllegalArgumentException     if the given 'input' isn't an array. Or if the given 'inputClass' is not an array class. Or if the given
	 *                                      'outputClass' is not a list class
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the output list
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = Object[].class,
					in = {boolean[].class,
						  byte[].class,
						  char[].class,
						  double[].class,
						  int[].class,
						  float[].class,
						  long[].class,
						  short[].class
					}
			),
			output = @MetaFamily(
					subIn = List.class
			)
	)
	protected void array_list(ConvertArguments<Object, List> arguments) throws ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(arguments, "arguments");
			Objects.requireNonNull(arguments.input, "arguments.input");

			if (!arguments.input.getClass().isArray())
				throw new IllegalArgumentException(arguments.input + " is not an array");
			if (!arguments.inputClazz.getKlass().isArray())
				throw new IllegalArgumentException(arguments.inputClazz.getKlass() + " is not a class for arrays");
			if (!List.class.isAssignableFrom(arguments.outputClazz.getKlass()))
				throw new IllegalArgumentException(arguments.outputClazz.getKlass() + " is not a class for lists");
		}

		int length = Array.getLength(arguments.input);

		//Replace if the output is incompatible
		if (!arguments.outputClazz.getKlass().isInstance(arguments.output))
			//output presented is not valid
			arguments.output = arguments.outputClazz.getKlass().getConstructor().newInstance();

		int size = arguments.output.size();

		for (int i = 0; i < length; i++) {
			//init
			Object inputElement = Array.get(arguments.input, i);
			Object outputElement = i < size ? arguments.output.get(i) : null;

			//DyNaMiC cOnVeRsIoN _/-\_/-\_/- :0 ~ MA-GI-KKU
			outputElement = this.convert(new ConvertArguments<>(arguments, inputElement, outputElement, 0));

			//Set the elements from the input
			if (i < size)
				arguments.output.set(i, outputElement);
			else arguments.output.add(outputElement);
		}
	}

	/**
	 * Collection => Array
	 * <br/>
	 * Replace the elements on the {@link ConvertArguments#output} from the given arguments. All from the given {@link ConvertArguments#input}. If
	 * the output is null, or have a type different than the class at {@link ConvertArguments#outputClazz}. Or have length other than the length of
	 * the {@link ConvertArguments#input}. Then the output will be replaced with a new output with the class of {@link
	 * ConvertArguments#outputClazz}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'arguments' or 'arguments.input' is null
	 * @throws IllegalArgumentException if the given 'input' isn't a collection. Or if the given 'inputClass' is not a collection class. Or if the
	 *                                  given 'outputClass' is not an array class
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = Collection.class
			),
			output = @MetaFamily(
					subIn = Object[].class,
					in = {boolean[].class,
						  byte[].class,
						  char[].class,
						  double[].class,
						  float[].class,
						  int[].class,
						  long[].class,
						  short[].class
					}))
	protected void collection_array(ConvertArguments<Collection, Object> arguments) {
		if (DEBUGGING) {
			Objects.requireNonNull(arguments, "arguments");
			Objects.requireNonNull(arguments.input, "arguments.input");

			if (!(arguments.input instanceof Collection))
				throw new IllegalReceiveException(arguments.input + " is not a collection");
			if (!Collection.class.isAssignableFrom(arguments.inputClazz.getKlass()))
				throw new IllegalArgumentException(arguments.inputClazz.getKlass() + " is not a class for collections");
			if (!arguments.outputClazz.getKlass().isArray())
				throw new IllegalArgumentException(arguments.outputClazz.getKlass() + " is not a class for arrays");
		}

		int length = arguments.input.size();

		if (!arguments.outputClazz.getKlass().isInstance(arguments.output) || Array.getLength(arguments.output) != length)
			arguments.output = Array.newInstance(arguments.outputClazz.getKlass().getComponentType(), length);

		Iterator it = arguments.input.iterator();
		for (int i = 0; i < length; i++) {
			//init
			Object inputElement = it.next();
			Object outputElement = Array.get(arguments.input, i);

			//DyNaMiC cOnVeRsIoN _/-\_/-\_/- :0 ~ MA-GI-KKU
			outputElement = this.convert(new ConvertArguments<>(arguments, inputElement, outputElement, 0));

			//Replace the element at the output with the converted element from the input
			Array.set(arguments.input, i, outputElement);
		}
	}

	/**
	 * Collection => Collection
	 * <br/>
	 * Replace the elements on the {@link ConvertArguments#output} from the given arguments. All from the given {@link ConvertArguments#input}. If
	 * the output is null, or have a type different than the class at {@link ConvertArguments#outputClazz}. Then the output will be replaced with a
	 * new output with the class of {@link ConvertArguments#outputClazz}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException         if the given 'arguments' or 'arguments.input' is null
	 * @throws IllegalArgumentException     if the given 'input' isn't a collection. Or if the given 'inputClass' or 'outputClass' are not classes
	 *                                      for collections
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the output collection
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = Collection.class
			),
			output = @MetaFamily(
					subIn = Collection.class,
					subOut = List.class
			))
	protected void collection_collection(ConvertArguments<Collection, Collection> arguments) throws ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(arguments, "arguments");
			Objects.requireNonNull(arguments.input, "arguments.input");

			if (!(arguments.input instanceof Collection))
				throw new IllegalArgumentException(arguments.input + " is not a collection");
			if (!Collection.class.isAssignableFrom(arguments.inputClazz.getKlass()))
				throw new IllegalArgumentException(arguments.inputClazz.getKlass() + " is not a class for collections");
			if (!Collection.class.isAssignableFrom(arguments.outputClazz.getKlass()))
				throw new IllegalArgumentException(arguments.outputClazz.getKlass() + " is not a class for collections");
		}

		int length = arguments.input.size();

		if (!arguments.outputClazz.getKlass().isInstance(arguments.output))
			arguments.output = arguments.outputClazz.getKlass().getConstructor().newInstance();
		else arguments.output.clear();

		Iterator it = arguments.input.iterator();
		for (int i = 0; i < length; i++) {
			Object inputElement = it.next();
			Object outputElement;

			//DyNaMiC cOnVeRsIoN _/-\_/-\_/- :0 ~ MA-GI-KKU
			outputElement = this.convert(new ConvertArguments<>(arguments, inputElement, 0));

			arguments.output.add(outputElement);
		}
	}

	/**
	 * Collection => Collection
	 * <br/>
	 * Replace the elements on the {@link ConvertArguments#output} from the given arguments. All from the given {@link ConvertArguments#input}. If
	 * the output is null, or have a type different than the class at {@link ConvertArguments#outputClazz}. Then the output will be replaced with a
	 * new output with the class of {@link ConvertArguments#outputClazz}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException         if the given 'arguments' or 'arguments.input' is null
	 * @throws IllegalArgumentException     if the given 'input' isn't a collection. Or if the given 'inputClass' is not a class for collections. Or
	 *                                      if the given 'outputClass' is not a class for lists.
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the output list
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = Collection.class
			),
			output = @MetaFamily(
					subIn = List.class
			))
	protected void collection_list(ConvertArguments<Collection, List> arguments) throws ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(arguments, "arguments");
			Objects.requireNonNull(arguments.input, "arguments.input");

			if (!(arguments.input instanceof Collection))
				throw new IllegalArgumentException(arguments.input + " is not a collection");
			if (!Collection.class.isAssignableFrom(arguments.inputClazz.getKlass()))
				throw new IllegalArgumentException(arguments.inputClazz.getKlass() + " is not a class for collections");
			if (!List.class.isAssignableFrom(arguments.outputClazz.getKlass()))
				throw new IllegalArgumentException(arguments.outputClazz.getKlass() + " is not a class for lists");
		}

		int length = arguments.input.size();

		if (!arguments.outputClazz.getKlass().isInstance(arguments.output))
			arguments.output = arguments.outputClazz.getKlass().getConstructor().newInstance();

		int size = arguments.output.size();

		Iterator it = arguments.input.iterator();
		for (int i = 0; i < length; i++) {
			Object inputElement = it.next();
			Object outputElement = i < size ? arguments.output.get(i) : null;

			//DyNaMiC cOnVeRsIoN _/-\_/-\_/- :0 ~ MA-GI-KKU
			outputElement = this.convert(new ConvertArguments<>(arguments, inputElement, outputElement, 0));

			if (i < size)
				arguments.output.set(i, outputElement);
			else arguments.output.add(outputElement);
		}
	}

	/**
	 * Map => Map
	 * <br/>
	 * Replace the elements on the {@link ConvertArguments#output} from the given arguments. All from the given {@link ConvertArguments#input}. If
	 * the output is null, or have a type different than the class at {@link ConvertArguments#outputClazz}. Then the output will be replaced with a
	 * new output with the class of {@link ConvertArguments#outputClazz}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException         if the given 'arguments' or 'arguments.input' is null
	 * @throws IllegalArgumentException     if the given 'input' isn't a map. Or if the given 'inputClass' or 'outputClass' are not classes for
	 *                                      maps
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the output map
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = Map.class
			),
			output = @MetaFamily(
					subIn = Map.class
			))
	protected void map_map(ConvertArguments<Map, Map> arguments) throws ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(arguments, "arguments");
			Objects.requireNonNull(arguments.input, "arguments.input");

			if (!(arguments.input instanceof Map))
				throw new IllegalArgumentException(arguments.input + " is not a map");
			if (!Map.class.isAssignableFrom(arguments.inputClazz.getKlass()))
				throw new IllegalArgumentException(arguments.inputClazz.getKlass() + " is not a class for maps");
			if (!Map.class.isAssignableFrom(arguments.outputClazz.getKlass()))
				throw new IllegalArgumentException(arguments.outputClazz.getKlass() + " is not a class for maps");
		}

		if (!arguments.outputClazz.getKlass().isInstance(arguments.output))
			arguments.output = arguments.outputClazz.getKlass().getConstructor().newInstance();

		arguments.output.keySet().retainAll(arguments.input.keySet());

		for (Map.Entry inputEntry : (Set<Map.Entry>) arguments.input.entrySet()) {
			Object key = inputEntry.getKey();

			Object inputElement = inputEntry.getValue();
			Object outputElement = arguments.output.get(key);

			outputElement = this.convert(new ConvertArguments<>(arguments, inputElement, outputElement, 1));

			arguments.output.put(key, outputElement);
		}
	}

	/**
	 * Number => Byte
	 * <br/>
	 * Set the {@link ConvertArguments#output} with a new {@link Byte} that holds the value of the given {@link ConvertArguments#input}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'arguments' or 'arguments.input' is null
	 * @throws IllegalArgumentException if the given 'input' is not a number. Or if the given 'inputClass' is not a class for numbers. Or if the
	 *                                  given 'outputClass' is not {@link Byte}
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = Number.class,
					in = {byte.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}),
			output = @MetaFamily(
					in = {Byte.class,
						  byte.class
					}))
	protected void number_byte(ConvertArguments<Number, Byte> arguments) {
		if (DEBUGGING) {
			Objects.requireNonNull(arguments, "arguments");
			Objects.requireNonNull(arguments.input, "arguments.input");

			if (!(arguments.input instanceof Number))
				throw new IllegalReceiveException(arguments.input + " is not a number");
			if (!Number.class.isAssignableFrom(arguments.inputClazz.getKlass()))
				throw new IllegalArgumentException(arguments.inputClazz.getKlass() + " is not a class for numbers");
			if (arguments.outputClazz.getKlass() != Byte.class)
				throw new IllegalArgumentException(arguments.outputClazz + " is not " + Byte.class);
		}

		arguments.output = arguments.input.byteValue();
	}

	/**
	 * Number => Double
	 * <br/>
	 * Set the {@link ConvertArguments#output} with a new {@link Double} that holds the value of the given {@link ConvertArguments#input}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'arguments' or 'arguments.input' is null
	 * @throws IllegalArgumentException if the given 'input' is not a number. Or if the given 'inputClass' is not a class for numbers. Or if the
	 *                                  given 'outputClass' is not {@link Double}
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = Number.class,
					in = {byte.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}),
			output = @MetaFamily(
					in = {Double.class,
						  double.class
					}))
	protected void number_double(ConvertArguments<Number, Double> arguments) {
		if (DEBUGGING) {
			Objects.requireNonNull(arguments, "arguments");
			Objects.requireNonNull(arguments.input, "arguments.input");

			if (!(arguments.input instanceof Number))
				throw new IllegalReceiveException(arguments.input + " is not a number");
			if (!Number.class.isAssignableFrom(arguments.inputClazz.getKlass()))
				throw new IllegalArgumentException(arguments.inputClazz.getKlass() + " is not a class for numbers");
			if (arguments.outputClazz.getKlass() != Double.class)
				throw new IllegalArgumentException(arguments.outputClazz + " is not " + Double.class);
		}

		arguments.output = arguments.input.doubleValue();
	}

	/**
	 * Number => Float
	 * <br/>
	 * Set the {@link ConvertArguments#output} with a new {@link Float} that holds the value of the given {@link ConvertArguments#input}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'arguments' or 'arguments.input' is null
	 * @throws IllegalArgumentException if the given 'input' is not a number. Or if the given 'inputClass' is not a class for numbers. Or if the
	 *                                  given 'outputClass' is not {@link Float}
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = Number.class,
					in = {byte.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}),
			output = @MetaFamily(
					in = {Float.class,
						  float.class
					}))
	protected void number_float(ConvertArguments<Number, Float> arguments) {
		if (DEBUGGING) {
			Objects.requireNonNull(arguments, "arguments");
			Objects.requireNonNull(arguments.input, "arguments.input");

			if (!(arguments.input instanceof Number))
				throw new IllegalReceiveException(arguments.input + " is not a number");
			if (!Number.class.isAssignableFrom(arguments.inputClazz.getKlass()))
				throw new IllegalArgumentException(arguments.inputClazz.getKlass() + " is not a class for numbers");
			if (arguments.outputClazz.getKlass() != Float.class)
				throw new IllegalArgumentException(arguments.outputClazz + " is not " + Float.class);
		}

		arguments.output = arguments.input.floatValue();
	}

	/**
	 * Number => Integer
	 * <br/>
	 * Set the {@link ConvertArguments#output} with a new {@link Integer} that holds the value of the given {@link ConvertArguments#input}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'arguments' or 'arguments.input' is null
	 * @throws IllegalArgumentException if the given 'input' is not a number. Or if the given 'inputClass' is not a class for numbers. Or if the
	 *                                  given 'outputClass' is not {@link Integer}
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = Number.class,
					in = {byte.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}),
			output = @MetaFamily(
					in = {int.class,
						  int.class
					}))
	protected void number_integer(ConvertArguments<Number, Integer> arguments) {
		if (DEBUGGING) {
			Objects.requireNonNull(arguments, "arguments");
			Objects.requireNonNull(arguments.input, "arguments.input");

			if (!(arguments.input instanceof Number))
				throw new IllegalReceiveException(arguments.input + " is not a number");
			if (!Number.class.isAssignableFrom(arguments.inputClazz.getKlass()))
				throw new IllegalArgumentException(arguments.inputClazz.getKlass() + " is not a class for numbers");
			if (arguments.outputClazz.getKlass() != Integer.class)
				throw new IllegalArgumentException(arguments.outputClazz + " is not " + Integer.class);
		}

		arguments.output = arguments.input.intValue();
	}

	/**
	 * Number => Long
	 * <br/>
	 * Set the {@link ConvertArguments#output} with a new {@link Long} that holds the value of the given {@link ConvertArguments#input}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'arguments' or 'arguments.input' is null
	 * @throws IllegalArgumentException if the given 'input' is not a number. Or if the given 'inputClass' is not a class for numbers. Or if the
	 *                                  given 'outputClass' is not {@link Long}
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = Number.class,
					in = {byte.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}),
			output = @MetaFamily(
					in = {Long.class,
						  long.class
					}))
	protected void number_long(ConvertArguments<Number, Long> arguments) {
		if (DEBUGGING) {
			Objects.requireNonNull(arguments, "arguments");
			Objects.requireNonNull(arguments.input, "arguments.input");

			if (!(arguments.input instanceof Number))
				throw new IllegalReceiveException(arguments.input + " is not a number");
			if (!Number.class.isAssignableFrom(arguments.inputClazz.getKlass()))
				throw new IllegalArgumentException(arguments.inputClazz.getKlass() + " is not a class for numbers");
			if (arguments.outputClazz.getKlass() != Long.class)
				throw new IllegalArgumentException(arguments.outputClazz + " is not " + Long.class);
		}

		arguments.output = arguments.input.longValue();
	}

	/**
	 * Number => Short
	 * <br/>
	 * Set the {@link ConvertArguments#output} with a new {@link Short} that holds the value of the given {@link ConvertArguments#input}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'arguments' or 'arguments.input' is null
	 * @throws IllegalArgumentException if the given 'input' is not a number. Or if the given 'inputClass' is not a class for numbers. Or if the
	 *                                  given 'outputClass' is not {@link Short}
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = Number.class,
					in = {byte.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}),
			output = @MetaFamily(
					in = {Short.class,
						  short.class
					}))
	protected void number_short(ConvertArguments<Number, Short> arguments) {
		if (DEBUGGING) {
			Objects.requireNonNull(arguments, "arguments");
			Objects.requireNonNull(arguments.input, "arguments.input");

			if (!(arguments.input instanceof Number))
				throw new IllegalReceiveException(arguments.input + " is not a number");
			if (!Number.class.isAssignableFrom(arguments.inputClazz.getKlass()))
				throw new IllegalArgumentException(arguments.inputClazz.getKlass() + " is not a class for numbers");
			if (arguments.outputClazz.getKlass() != Short.class)
				throw new IllegalArgumentException(arguments.outputClazz + " is not " + Short.class);
		}

		arguments.output = arguments.input.shortValue();
	}

	/**
	 * Object => String
	 * <br/>
	 *
	 * Set the {@link ConvertArguments#output} with a new {@link String} that holds the value of the given {@link ConvertArguments#input}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'arguments' is null
	 * @throws IllegalArgumentException if the given 'outputClass' is not {@link String}
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = Object.class,
					in = {byte.class,
						  boolean.class,
						  char.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}),
			output = @MetaFamily(
					in = String.class
			))
	protected void object_string(ConvertArguments<Object, String> arguments) {
		if (DEBUGGING) {
			Objects.requireNonNull(arguments, "arguments");
			if (arguments.outputClazz.getKlass() != String.class)
				throw new IllegalArgumentException(arguments.outputClazz.getKlass() + " is not " + String.class);
		}

		arguments.output = String.valueOf(arguments.input);
	}

	/**
	 * Recurse => Object
	 * <br/>
	 * Get invoked when a recursion converting occurred. Set the {@link ConvertArguments#output} to the mapped output for the recurred input.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'arguments' is null
	 * @throws ClassCastException       if the output instance of the given recurse is not instance of the given 'outputClass'
	 * @throws IllegalArgumentException if no recursion actually happened
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = Recurse.class
			),
			output = @MetaFamily(
					subIn = Object.class,
					in = {boolean.class,
						  byte.class,
						  char.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}))
	protected void recurse_object(ConvertArguments<Recurse, Object> arguments) {
		if (DEBUGGING) {
			Objects.requireNonNull(arguments, "arguments");
		}

		for (ConvertArguments grand = arguments.parent; grand != null; grand = grand.parent)
			if (grand.input == arguments.input) {
				arguments.output = grand.output;
				return;
			}

		throw new IllegalArgumentException(arguments.input + " haven't recurred");
	}

	/**
	 * String => Object
	 * <br/>
	 * Try to construct a new object of the value of the given {@link ConvertArguments#input} with type of the {@link ConvertArguments#outputClazz}.
	 * Using ether a method with a signature equals to 'valueOf(String)'. Or a constructor with a signature equals to '(String)'.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException         if the given 'arguments' or 'input' is null
	 * @throws IllegalArgumentException     if the given 'input' is not a string. Or if the 'inputClass' is not String.class. Or if the
	 *                                      'outputClass' don't have a 'valueOf(String)' method nor a (String) constructor
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the output object
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = String.class
			),
			output = @MetaFamily(
					subIn = Object.class,
					in = {boolean.class,
						  byte.class,
						  char.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}))
	protected void string_object(ConvertArguments<String, Object> arguments) throws ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(arguments, "arguments");
			Objects.requireNonNull(arguments.input, "arguments.input");

			if (!(arguments.input instanceof String))
				throw new IllegalArgumentException(arguments.input + " is not a string");
			if (arguments.inputClazz.getKlass() != String.class)
				throw new IllegalArgumentException(arguments.inputClazz.getKlass() + " is not " + String.class);
		}

		if (arguments.input.equals("null"))
			arguments.output = null;
		else try {
			arguments.output = arguments.outputClazz.getKlass().getMethod("valueOf", String.class).invoke(null, arguments.input);
		} catch (NoSuchMethodException ignored) {
			try {
				arguments.output = arguments.outputClazz.getKlass().getConstructor(String.class).newInstance(arguments.input);
			} catch (NoSuchMethodException ignored1) {
				throw new IllegalArgumentException(arguments.outputClazz.getKlass() + " don't 'valueOf(String)' nor constructor(String) method");
			}
		}
	}
}
