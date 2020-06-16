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
import cufy.lang.DejaVu;
import cufy.lang.Recurse;
import cufy.meta.Filter;
import cufy.meta.Where;
import cufy.util.Arrayz;

import java.lang.reflect.Array;
import java.util.*;

/**
 * A converter that has a basic converting methods.
 * <p>
 * Methods:
 * <ul>
 *     <li>
 *         <b>Collections</b>
 *         <ul>
 *             <li>{@link #collectionToArray}</li>
 *             <li>{@link #collectionToCollection}</li>
 *             <li>{@link #collectionToList}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>Maps</b>
 *         <ul>
 *             <li>{@link #mapToMap}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>Numbers</b>
 *         <ul>
 *             <li>{@link #numberToNumber}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>Strings</b>
 *         <ul>
 *             <li>{@link #objectToString}</li>
 *             <li>{@link #stringToObject}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>Situations</b>
 *         <ul>
 *             <li>{@link #dejaVu}</li>
 *             <li>{@link #recurse}</li>
 *         </ul>
 *     </li>
 * </ul>
 *
 * @author lsafer
 * @version 0.1.5
 * @since 31-Aug-2019
 */
public class BaseConverter extends AbstractConverter {
	/**
	 * The global instance to avoid unnecessary instancing.
	 */
	@Where.Target
	public static final BaseConverter global = new BaseConverter();

	/**
	 * Inherit only.
	 */
	protected BaseConverter() {
	}

	@Override
	protected boolean convertPre(ConvertToken token) {
		Map<Object, ConvertToken> linearMap = (Map) token.linear.computeIfAbsent("refs", k -> new HashMap());
		Map<Object, ConvertToken> treeMap = (Map) token.tree.compute("refs", (k, v) -> v instanceof Map ? new HashMap((Map) v) : new HashMap());

		//RECURSE DETECTION
		for (Map.Entry<Object, ConvertToken> entry : linearMap.entrySet())
			if (entry.getKey() == token.input) {
				token.inputClazz = token.inputClazz.override(Recurse.class);
				token.data.put("recurseToken", entry.getValue());
				token.data.put("recurse", entry.getValue().output);
				return true;
			}

		//DEJAVU DETECTION
		for (Map.Entry<Object, ConvertToken> entry : treeMap.entrySet())
			if (entry.getKey() == token.input) {
				token.inputClazz = token.inputClazz.override(DejaVu.class);
				token.data.put("dejavuToken", entry.getValue());
				token.data.put("dejavu", entry.getValue().output);
				return true;
			}

		//REGISTER
		linearMap.put(token.input, token);
		treeMap.put(token.input, token);
		return true;
	}

	/**
	 * Replace the elements on the {@link ConvertToken#output} from the given token. All from the given {@link ConvertToken#input}. If the output is
	 * null, or have a type different than the class at {@link ConvertToken#outputClazz}. Or have length other than the length of the {@link
	 * ConvertToken#input}. Then the output will be replaced with a new output with the class of {@link ConvertToken#outputClazz}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@ConvertMethod(
			input = @Filter(
					includeAll = {Collection.class,
								  Object[].class,
					},
					include = {boolean[].class,
							   byte[].class,
							   char[].class,
							   double[].class,
							   float[].class,
							   int[].class,
							   long[].class,
							   short[].class
					}),
			output = @Filter(
					includeAll = Object[].class,
					include = {boolean[].class,
							   byte[].class,
							   char[].class,
							   double[].class,
							   float[].class,
							   int[].class,
							   long[].class,
							   short[].class
					}))
	protected void collectionToArray(ConvertToken<Collection, Object> token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		//declare the input
		Collection input = token.input instanceof Collection ? token.input : Arrayz.asList0(token.input);

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
			outputElement = this.convert(token.subToken(
					inputElement,
					outputElement,
					Clazz.ofi(inputElement),
					outputElement == null ? null : Clazz.ofi(outputElement),
					0
			));

			//Replace the element at the output with the converted element from the input
			Array.set(token.output, i, outputElement);
		}
	}

	/**
	 * Replace the elements on the {@link ConvertToken#output} from the given token. All from the given {@link ConvertToken#input}. If the output is
	 * null, or have a type different than the class at {@link ConvertToken#outputClazz}. Then the output will be replaced with a new output with the
	 * class of {@link ConvertToken#outputClazz}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException         if the given 'token' or 'token.input' is null
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the output collection
	 */
	@ConvertMethod(
			input = @Filter(
					includeAll = {Collection.class,
								  Object[].class
					},
					include = {boolean[].class,
							   byte[].class,
							   char[].class,
							   double[].class,
							   int[].class,
							   float[].class,
							   long[].class,
							   short[].class,
					}),
			output = @Filter(
					includeAll = Collection.class,
					excludeAll = List.class
			))
	protected void collectionToCollection(ConvertToken<Collection, Collection> token) throws ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		//declare the input
		Collection input = token.input instanceof Collection ? token.input : Arrayz.asList0(token.input);

		//declare the output
		if (!token.outputClazz.isInstance(token.output)) {
			//output presented is not valid
			token.output = token.outputClazz.getConstructor().newInstance();
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
			outputElement = this.convert(token.subToken(
					inputElement,
					outputElement,
					Clazz.ofi(inputElement),
					outputElement == null ? null : Clazz.ofi(outputElement),
					0
			));

			//add the element to the output
			token.output.add(outputElement);
		}
	}

	/**
	 * Replace the elements on the {@link ConvertToken#output} from the given token. All from the given {@link ConvertToken#input}. If the output is
	 * null, or have a type different than the class at {@link ConvertToken#outputClazz}. Then the output will be replaced with a new output with the
	 * class of {@link ConvertToken#outputClazz}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException         if the given 'token' or 'token.input' is null
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the output list
	 */
	@ConvertMethod(
			input = @Filter(
					includeAll = {Collection.class,
								  Object[].class
					},
					include = {boolean[].class,
							   byte[].class,
							   char[].class,
							   double[].class,
							   int[].class,
							   float[].class,
							   long[].class,
							   short[].class
					}
			),
			output = @Filter(includeAll = List.class)
	)
	protected void collectionToList(ConvertToken<Collection, List> token) throws ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		//declare the input
		Collection input = token.input instanceof Collection ? token.input : Arrayz.asList0(token.input);

		//declare the output
		if (!token.outputClazz.isInstance(token.output)) {
			//output presented is not valid
			token.output = token.outputClazz.getConstructor().newInstance();
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
			outputElement = this.convert(token.subToken(
					inputElement,
					outputElement,
					Clazz.ofi(inputElement),
					outputElement == null ? null : Clazz.ofi(outputElement),
					0
			));

			//Set the elements from the input
			token.output.set(i, outputElement);
		}
	}

	/**
	 * Get invoked when a dejaVu converting occurred. Set the {@link ConvertToken#output} to the mapped output for the dejaVu input.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'token' is null
	 * @throws ClassCastException       if the output instance of the given recurse is not instance of the given 'outputClass'
	 * @throws IllegalArgumentException if no recursion actually happened
	 */
	@ConvertMethod(
			input = @Filter(DejaVu.class),
			output = @Filter(
					includeAll = Object.class,
					include = {boolean.class,
							   byte.class,
							   char.class,
							   double.class,
							   float.class,
							   int.class,
							   long.class,
							   short.class
					}))
	protected void dejaVu(ConvertToken<DejaVu, Object> token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		//IT SHOULD HAVE BEEN PASSED TO US :)
		if (token.data.containsKey("dejavu"))
			token.output = token.data.get("dejavu");
		else throw new IllegalArgumentException("the key 'dejavu' haven't been passed!");
	}

	/**
	 * Replace the elements on the {@link ConvertToken#output} from the given token. All from the given {@link ConvertToken#input}. If the output is
	 * null, or have a type different than the class at {@link ConvertToken#outputClazz}. Then the output will be replaced with a new output with the
	 * class of {@link ConvertToken#outputClazz}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException         if the given 'token' or 'token.input' is null
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the output map
	 */
	@ConvertMethod(
			input = @Filter(
					includeAll = Map.class
			),
			output = @Filter(
					includeAll = Map.class
			))
	protected void mapToMap(ConvertToken<Map, Map> token) throws ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		//declare output
		if (!token.outputClazz.isInstance(token.output)) {
			//output presented is not valid
			token.output = token.outputClazz.getConstructor().newInstance();
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
			outputElement = this.convert(token.subToken(
					inputElement,
					outputElement,
					Clazz.ofi(inputElement),
					outputElement == null ? null : Clazz.ofi(outputElement),
					1
			));

			//Set the elements from the input
			token.output.put(entry.getKey(), outputElement);
		}
	}

	/**
	 * Set the {@link ConvertToken#output} with a new {@link Number} that holds the value of the given {@link ConvertToken#input}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@ConvertMethod(
			input = @Filter(
					includeAll = Number.class,
					include = {byte.class,
							   double.class,
							   float.class,
							   int.class,
							   long.class,
							   short.class
					}
			),
			output = @Filter(
					include = {Byte.class,
							   Double.class,
							   Float.class,
							   Integer.class,
							   Long.class,
							   Short.class,
							   byte.class,
							   double.class,
							   float.class,
							   int.class,
							   long.class,
							   short.class
					}
			)
	)
	protected void numberToNumber(ConvertToken<Number, Number> token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		Class klass = token.outputClazz.getKlass();

		if (klass == byte.class || klass == Byte.class) {
			//BYTE
			token.output = token.input.intValue();
		} else if (klass == double.class || klass == Double.class) {
			//DOUBLE
			token.output = token.input.doubleValue();
		} else if (klass == float.class || klass == Float.class) {
			//FLOAT
			token.output = token.input.floatValue();
		} else if (klass == int.class || klass == Integer.class) {
			//INTEGER
			token.output = token.input.intValue();
		} else if (klass == long.class || klass == Long.class) {
			//LONG
			token.output = token.input.longValue();
		} else if (klass == short.class || klass == Short.class) {
			//SHORT
			token.output = token.input.shortValue();
		} else {
			throw new ConvertException("can't convert " + token.inputClazz + " to " + token.outputClazz);
		}
	}

	/**
	 * Set the {@link ConvertToken#output} with a new {@link String} that holds the value of the given {@link ConvertToken#input}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException if the given 'token' is null
	 */
	@ConvertMethod(
			input = @Filter(
					includeAll = Object.class,
					include = {byte.class,
							   boolean.class,
							   char.class,
							   double.class,
							   float.class,
							   int.class,
							   long.class,
							   short.class
					}),
			output = @Filter(
					include = String.class
			))
	protected void objectToString(ConvertToken<Object, String> token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		token.output = String.valueOf(token.input);
	}

	/**
	 * Get invoked when a recursion converting occurred. Set the {@link ConvertToken#output} to the mapped output for the recurred input.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'token' is null
	 * @throws ClassCastException       if the output instance of the given recurse is not instance of the given 'outputClass'
	 * @throws IllegalArgumentException if no recursion actually happened
	 */
	@ConvertMethod(
			input = @Filter(Recurse.class),
			output = @Filter(
					includeAll = Object.class,
					include = {boolean.class,
							   byte.class,
							   char.class,
							   double.class,
							   float.class,
							   int.class,
							   long.class,
							   short.class
					}))
	protected void recurse(ConvertToken<Recurse, Object> token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		//IT SHOULD HAVE BEEN PASSED TO US :)
		if (token.data.containsKey("recurse"))
			token.output = token.data.get("recurse");
		else throw new IllegalArgumentException("the key 'recurse' haven't been passed!");
	}

	/**
	 * Try to construct a new object of the value of the given {@link ConvertToken#input} with type of the {@link ConvertToken#outputClazz}. Using
	 * ether a method with a signature equals to 'valueOf(String)'. Or a constructor with a signature equals to '(String)'.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException         if the given 'token' or 'input' is null
	 * @throws IllegalArgumentException     if the 'outputClass' don't have a 'valueOf(String)' method nor a (String) constructor
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the output object
	 */
	@ConvertMethod(
			input = @Filter(
					includeAll = String.class
			),
			output = @Filter(
					includeAll = Object.class,
					include = {boolean.class,
							   byte.class,
							   char.class,
							   double.class,
							   float.class,
							   int.class,
							   long.class,
							   short.class
					}))
	protected void stringToObject(ConvertToken<String, Object> token) throws ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		if ("null".equals(token.input)) {
			token.output = null;
		} else try {
			token.output = token.outputClazz.getMethod("valueOf", String.class).invoke(null, token.input);
		} catch (NoSuchMethodException ignored) {
			try {
				token.output = token.outputClazz.getConstructor(String.class).newInstance(token.input);
			} catch (NoSuchMethodException ignored1) {
				throw new IllegalArgumentException(token.outputClazz + " don't 'valueOf(String)' nor constructor(String) method");
			}
		}
	}
}
