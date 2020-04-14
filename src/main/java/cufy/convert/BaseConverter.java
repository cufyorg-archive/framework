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

import cufy.lang.Clazz;
import cufy.lang.Recurse;
import cufy.meta.Filter;
import cufy.meta.Reference;
import cufy.util.Arrayu;

import java.lang.reflect.Array;
import java.util.*;

/**
 * A converter that has a basic converting methods.
 *
 * <ul>
 *     Methods:
 *     <li>
 *         <b>Collections</b>
 *         <ul>
 *             <li>{@link #collection_array}</li>
 *             <li>{@link #collection_collection}</li>
 *             <li>{@link #collection_list}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <ul>
 *             <b>Maps</b>
 *             <li>{@link #map_map}</li>
 *         </ul>
 *     </li>
 *     <li>
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
 *             <li>{@link #object_string}</li>
 *             <li>{@link #string_object}</li>
 *             <li>{@link #recurse_object}</li>
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
	@Reference
	final public static BaseConverter global = new BaseConverter();

	/**
	 * Inherit only.
	 */
	protected BaseConverter() {
	}

	/**
	 * Get a "easy-to-use" sub-token for the given parameters.
	 *
	 * @param token         the converting token
	 * @param inputElement  the element found in the input
	 * @param outputElement the element found in the same position of the inputElement but in the output
	 * @param index         the index of the componentType to work on
	 * @return the results of converting
	 */
	protected ConvertToken _elementSubToken(ConvertToken token, Object inputElement, Object outputElement, int index) {
		return token.subToken(
				inputElement,
				outputElement,
				Clazz.ofi(inputElement),
				outputElement == null ? null : Clazz.ofi(outputElement),
				index
		);
	}

	/**
	 * Collection => Array
	 * <br/>
	 * Replace the elements on the {@link ConvertToken#output} from the given token. All from the given {@link ConvertToken#input}. If
	 * the output is null, or have a type different than the class at {@link ConvertToken#outputClazz}. Or have length other than the length of
	 * the {@link ConvertToken#input}. Then the output will be replaced with a new output with the class of {@link
	 * ConvertToken#outputClazz}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@ConvertMethod(
			input = @Filter(
					subIn = {Collection.class,
							 Object[].class,
					},
					in = {boolean[].class,
						  byte[].class,
						  char[].class,
						  double[].class,
						  float[].class,
						  int[].class,
						  long[].class,
						  short[].class
					}),
			output = @Filter(
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
	protected void collection_array(ConvertToken<Collection, Object> token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		//declare the input
		Collection input = token.input instanceof Collection ? token.input : Arrayu.asList0(token.input);

		//declare the output
		if (!token.outputClazz.isInstance(token.output) || Array.getLength(token.output) != input.size()) {
			//output presented is not valid
			token.output = Array.newInstance(token.outputClazz.getComponentType(), input.size());
		}

		//converting elements
		Iterator it = input.iterator();
		for (int i = 0; it.hasNext(); i++) {
			//init
			Object inputElement = it.next();
			Object outputElement = Array.get(token.output, i);

			//DyNaMiC cOnVeRsIoN _/-\_/-\_/- :0 ~ MA-GI-KKU
			outputElement = this.convert(this._elementSubToken(token, inputElement, outputElement, 0));

			//Replace the element at the output with the converted element from the input
			Array.set(token.output, i, outputElement);
		}
	}

	/**
	 * Collection => Collection
	 * <br/>
	 * Replace the elements on the {@link ConvertToken#output} from the given token. All from the given {@link ConvertToken#input}. If
	 * the output is null, or have a type different than the class at {@link ConvertToken#outputClazz}. Then the output will be replaced with a
	 * new output with the class of {@link ConvertToken#outputClazz}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException         if the given 'token' or 'token.input' is null
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the output collection
	 */
	@ConvertMethod(
			input = @Filter(
					subIn = {Collection.class,
							 Object[].class
					},
					in = {boolean[].class,
						  byte[].class,
						  char[].class,
						  double[].class,
						  int[].class,
						  float[].class,
						  long[].class,
						  short[].class,
					}),
			output = @Filter(
					subIn = Collection.class,
					subOut = List.class
			))
	protected void collection_collection(ConvertToken<Collection, Collection> token) throws ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		//declare the input
		Collection input = token.input instanceof Collection ? token.input : Arrayu.asList0(token.input);

		//declare the output
		if (!token.outputClazz.isInstance(token.output)) {
			//output presented is not valid
			token.output = token.outputClazz.getKlass().getConstructor().newInstance();
		} else { //fresh start
			token.output.clear();
		}

		//converting elements
		Iterator it = input.iterator();
		while (it.hasNext()) {
			//init
			Object inputElement = it.next();
			Object outputElement = null;

			//DyNaMiC cOnVeRsIoN _/-\_/-\_/- :0 ~ MA-GI-KKU
			outputElement = this.convert(this._elementSubToken(token, inputElement, outputElement, 0));

			//add the element to the output
			token.output.add(outputElement);
		}
	}

	/**
	 * Collection => Collection
	 * <br/>
	 * Replace the elements on the {@link ConvertToken#output} from the given token. All from the given {@link ConvertToken#input}. If
	 * the output is null, or have a type different than the class at {@link ConvertToken#outputClazz}. Then the output will be replaced with a
	 * new output with the class of {@link ConvertToken#outputClazz}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException         if the given 'token' or 'token.input' is null
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the output list
	 */
	@ConvertMethod(
			input = @Filter(
					subIn = {Collection.class,
							 Object[].class
					},
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
			output = @Filter(
					subIn = List.class
			)
	)
	protected void collection_list(ConvertToken<Collection, List> token) throws ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		//declare the input
		Collection input = token.input instanceof Collection ? token.input : Arrayu.asList0(token.input);

		//declare the output
		if (!token.outputClazz.isInstance(token.output)) {
			//output presented is not valid
			token.output = token.outputClazz.getKlass().getConstructor().newInstance();
		} else {
			int inputSize = input.size();
			int outputSize = token.output.size();

			if (inputSize < outputSize) {
				//if the output is small
				token.output.subList(inputSize, outputSize).clear();
			} else if (inputSize > outputSize) {
				//if the output is large
				token.output.addAll(Collections.nCopies(inputSize - outputSize, null));
			}
		}

		//converting elements
		Iterator it = input.iterator();
		for (int i = 0; it.hasNext(); i++) {
			//init
			Object inputElement = it.next();
			Object outputElement = token.output.get(i);

			//DyNaMiC cOnVeRsIoN _/-\_/-\_/- :0 ~ MA-GI-KKU
			outputElement = this.convert(this._elementSubToken(token, inputElement, outputElement, 0));

			//Set the elements from the input
			token.output.set(i, outputElement);
		}
	}

	/**
	 * Map => Map
	 * <br/>
	 * Replace the elements on the {@link ConvertToken#output} from the given token. All from the given {@link ConvertToken#input}. If
	 * the output is null, or have a type different than the class at {@link ConvertToken#outputClazz}. Then the output will be replaced with a
	 * new output with the class of {@link ConvertToken#outputClazz}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException         if the given 'token' or 'token.input' is null
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the output map
	 */
	@ConvertMethod(
			input = @Filter(
					subIn = Map.class
			),
			output = @Filter(
					subIn = Map.class
			))
	protected void map_map(ConvertToken<Map, Map> token) throws ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		//declare output
		if (!token.outputClazz.isInstance(token.output)) {
			//output presented is not valid
			token.output = token.outputClazz.getKlass().getConstructor().newInstance();
		} else {
			//remove unwanted keys
			token.output.keySet().retainAll(token.input.keySet());
		}
		//converting elements
		for (Map.Entry entry : (Set<Map.Entry>) token.input.entrySet()) {
			//init
			Object inputElement = entry.getValue();
			Object outputElement = token.output.get(entry.getKey());

			//DyNaMiC cOnVeRsIoN _/-\_/-\_/- :0 ~ MA-GI-KKU
			outputElement = this.convert(this._elementSubToken(token, inputElement, outputElement, 1));

			//Set the elements from the input
			token.output.put(entry.getKey(), outputElement);
		}
	}

	/**
	 * Number => Byte
	 * <br/>
	 * Set the {@link ConvertToken#output} with a new {@link Byte} that holds the value of the given {@link ConvertToken#input}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@ConvertMethod(
			input = @Filter(
					subIn = Number.class,
					in = {byte.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}),
			output = @Filter(
					in = {Byte.class,
						  byte.class
					}))
	protected void number_byte(ConvertToken<Number, Byte> token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		token.output = token.input.byteValue();
	}

	/**
	 * Number => Double
	 * <br/>
	 * Set the {@link ConvertToken#output} with a new {@link Double} that holds the value of the given {@link ConvertToken#input}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@ConvertMethod(
			input = @Filter(
					subIn = Number.class,
					in = {byte.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}),
			output = @Filter(
					in = {Double.class,
						  double.class
					}))
	protected void number_double(ConvertToken<Number, Double> token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		token.output = token.input.doubleValue();
	}

	/**
	 * Number => Float
	 * <br/>
	 * Set the {@link ConvertToken#output} with a new {@link Float} that holds the value of the given {@link ConvertToken#input}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@ConvertMethod(
			input = @Filter(
					subIn = Number.class,
					in = {byte.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}),
			output = @Filter(
					in = {Float.class,
						  float.class
					}))
	protected void number_float(ConvertToken<Number, Float> token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		token.output = token.input.floatValue();
	}

	/**
	 * Number => Integer
	 * <br/>
	 * Set the {@link ConvertToken#output} with a new {@link Integer} that holds the value of the given {@link ConvertToken#input}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@ConvertMethod(
			input = @Filter(
					subIn = Number.class,
					in = {byte.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}),
			output = @Filter(
					in = {Integer.class,
						  int.class
					}))
	protected void number_integer(ConvertToken<Number, Integer> token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		token.output = token.input.intValue();
	}

	/**
	 * Number => Long
	 * <br/>
	 * Set the {@link ConvertToken#output} with a new {@link Long} that holds the value of the given {@link ConvertToken#input}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@ConvertMethod(
			input = @Filter(
					subIn = Number.class,
					in = {byte.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}),
			output = @Filter(
					in = {Long.class,
						  long.class
					}))
	protected void number_long(ConvertToken<Number, Long> token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		token.output = token.input.longValue();
	}

	/**
	 * Number => Short
	 * <br/>
	 * Set the {@link ConvertToken#output} with a new {@link Short} that holds the value of the given {@link ConvertToken#input}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@ConvertMethod(
			input = @Filter(
					subIn = Number.class,
					in = {byte.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}),
			output = @Filter(
					in = {Short.class,
						  short.class
					}))
	protected void number_short(ConvertToken<Number, Short> token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		token.output = token.input.shortValue();
	}

	/**
	 * Object => String
	 * <br/>
	 *
	 * Set the {@link ConvertToken#output} with a new {@link String} that holds the value of the given {@link ConvertToken#input}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException if the given 'token' is null
	 */
	@ConvertMethod(
			input = @Filter(
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
			output = @Filter(
					in = String.class
			))
	protected void object_string(ConvertToken<Object, String> token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		token.output = String.valueOf(token.input);
	}

	/**
	 * Recurse => Object
	 * <br/>
	 * Get invoked when a recursion converting occurred. Set the {@link ConvertToken#output} to the mapped output for the recurred input.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'token' is null
	 * @throws ClassCastException       if the output instance of the given recurse is not instance of the given 'outputClass'
	 * @throws IllegalArgumentException if no recursion actually happened
	 */
	@ConvertMethod(
			input = @Filter(
					subIn = Recurse.class
			),
			output = @Filter(
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
	protected void recurse_object(ConvertToken<Recurse, Object> token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		for (ConvertToken grand = token.parent; grand != null; grand = grand.parent)
			if (grand.input == token.input) {
				token.output = grand.output;
				return;
			}

		throw new IllegalArgumentException(token.input + " haven't recurred");
	}

	/**
	 * String => Object
	 * <br/>
	 * Try to construct a new object of the value of the given {@link ConvertToken#input} with type of the {@link ConvertToken#outputClazz}.
	 * Using ether a method with a signature equals to 'valueOf(String)'. Or a constructor with a signature equals to '(String)'.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException         if the given 'token' or 'input' is null
	 * @throws IllegalArgumentException     if the 'outputClass' don't have a 'valueOf(String)' method nor a (String) constructor
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the output object
	 */
	@ConvertMethod(
			input = @Filter(
					subIn = String.class
			),
			output = @Filter(
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
	protected void string_object(ConvertToken<String, Object> token) throws ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		if (token.input.equals("null")) {
			token.output = null;
		} else try {
			token.output = token.outputClazz.getKlass().getMethod("valueOf", String.class).invoke(null, token.input);
		} catch (NoSuchMethodException ignored) {
			try {
				token.output = token.outputClazz.getKlass().getConstructor(String.class).newInstance(token.input);
			} catch (NoSuchMethodException ignored1) {
				throw new IllegalArgumentException(token.outputClazz.getKlass() + " don't 'valueOf(String)' nor constructor(String) method");
			}
		}
	}
}
