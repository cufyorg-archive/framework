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

import cufy.meta.Filter;
import cufy.util.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Objects;

/**
 * An abstract class for converter classes. Used to simplify the conversion processes and make it more inheritable. Also making the inheriting for
 * adding some futures more easier. Methods in this class will be invoked using the dynamic method grouping algorithm. In order to add a method on a
 * dynamic method group. The method should be annotated with that group annotation. Also the method should match the conditions of that group to avoid
 * parameters/output mismatches.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 31-Aug-2019
 */
public abstract class AbstractConverter implements Converter {
	/**
	 * The converting methods of this class.
	 */
	final protected Group<Method> methods = Collectionz.unmodifiableGroup(new HashGroup<>(Reflection.getAllMethods(this.getClass())));
	/**
	 * If this class in a debugging mode or not.
	 * <p>
	 * Note: if this set to false all null-checks and type-checks should not be executed at runtime.
	 */
	protected boolean DEBUGGING = false;

	@Override
	public <I, O> O convert(ConvertToken<I, O> token) {
		Objects.requireNonNull(token, "token");

		if (this.convertPre(token)) {
			Method method = this.getConvertMethod(token.inputClazz.getFamily(), token.outputClazz.getFamily());

			if (method == null)
				this.convertElse(token);
			else this.convert0(method, token);
		}

		return token.output;
	}

	/**
	 * Get invoked if no conversion method is found for the given token. Meanwhile the input is instance of the outputClazz and the inputClazz is the
	 * outputClazz.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 */
	protected void cloneElse(ConvertToken token) {
		//by default: at this point `inputClazz` and `outputClazz` are the same
		if (Reflection.hasPrimitiveClass(token.outputClazz.getFamily())) {
			//when the class is one of the essential types, it can't be cloned by default
			token.output = token.input;
		} else {
			//we have a contract to remove any link between the 'input' and the 'output'
			throw new CloneException("Can't clone " + token.outputClazz);
		}
	}

	/**
	 * Invoke the given {@link ConvertMethod} with the given parameters.
	 *
	 * @param method to be invoked
	 * @param token  the conversion instance that holds the variables of this conversion
	 * @throws ConvertException         if any converting error occurred
	 * @throws NullPointerException     if any of the given parameters is null
	 * @throws IllegalArgumentException if the given method have limited access. Or if the given method have illegal parameters count
	 */
	protected void convert0(Method method, ConvertToken token) {
		if (DEBUGGING) {
			Objects.requireNonNull(method, "method");
			Objects.requireNonNull(token, "token");
		}

		try {
			method.setAccessible(true);
			method.invoke(this, token);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(method + " have limited access", e);
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			if (cause instanceof ConvertException)
				throw (ConvertException) cause;
			else throw new ConvertException(cause);
		}
	}

	/**
	 * Get invoked if no conversion method is found for the given token.
	 * <p>
	 * Note: called dynamically. No need for direct call
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws ConvertException     if any converting error occurred
	 * @throws NullPointerException if the given token is null
	 */
	protected void convertElse(ConvertToken token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		if (token.input == null) {
			//null is a value for any type and it is a final value and no need to duplicate it
			token.output = null;
		} else if (token.outputClazz.isInstance(token.input)) {
			//if the value is the wanted. Then we should duplicated to remove the link between the input and the output.
			if (token.inputClazz == token.outputClazz) {
				//give it to the subclass if it can handle it. because if we have a method to duplicate it, this method wouldn't be called!
				this.cloneElse(token);
			} else {
				//this is the cloning formula :)
				token.output = this.convert(new ConvertToken<>(token.input, null, token.inputClazz, token.inputClazz));
			}
		} else if (token.outputClazz.hasPrimitive()) {
			//primitive classes should be treated differently
			if (token.outputClazz.toObjectClazz().isInstance(token.input))
				token.output = token.input;
		} else {
			throw new ConvertException("Cannot convert " + token.inputClazz.getFamily() + " to " + token.outputClazz.getFamily());
		}
	}

	/**
	 * Operations to be done before any converting action.
	 *
	 * @param token the token provided by the caller
	 * @return whether the converting process should be continued or not
	 * @throws NullPointerException if the given 'token' is null
	 * @throws ConvertException     if any converting exception occurs
	 */
	protected boolean convertPre(ConvertToken token) {
		return true;
	}

	/**
	 * Find a method that converts the given 'inputClass' to the given 'outputClass'.
	 *
	 * @param inputClass  type that the targeted method can except as a parameter
	 * @param outputClass type that the targeted method can return
	 * @return a method that can convert the given inputClass to the given outputClass class
	 * @throws NullPointerException if any of the given parameters is null
	 */
	protected Method getConvertMethod(Class inputClass, Class outputClass) {
		if (DEBUGGING) {
			Objects.requireNonNull(inputClass, "inputClass");
			Objects.requireNonNull(outputClass, "outputClass");
		}

		//QUERY the best method!
		Group<Method> valid = this.methods
				.subGroup(ConvertMethod.class, m -> m.isAnnotationPresent(ConvertMethod.class))
				.subGroup(Arrayz.asList(inputClass, outputClass), m -> {
					ConvertMethod ann = m.getAnnotation(ConvertMethod.class);
					return Filter.Util.test(ann.input(), inputClass) &&
						   Filter.Util.test(ann.output(), outputClass);
				});

		if (valid.size() == 0) {
			return null;
		} else {
			Iterator<Method> i = valid.iterator();
			Method max = i.next();
			int mOrder = max.getAnnotation(ConvertMethod.class).priority();

			while (i.hasNext()) {
				Method next = i.next();
				int nOrder = next.getAnnotation(ConvertMethod.class).priority();

				if (nOrder < mOrder) {
					max = next;
					mOrder = nOrder;
				}
			}

			return max;
		}
	}
}
