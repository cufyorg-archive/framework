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
package cufy.text;

import cufy.lang.Clazz;
import cufy.meta.Filter;
import cufy.util.Group;
import cufy.util.Reflectionu;
import cufy.util.UnmodifiableGroup;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Objects;

/**
 * An abstract class for formatter classes. Used to simplify the formatting processes and make it more inheritable. Also making the inheriting for
 * adding some futures more easier. Methods in this class will be invoked using the dynamic method grouping algorithm. In order to add a method on a
 * dynamic method group. The method should be annotated with that group annotation. Also the method should match the conditions of that group to
 * avoid parameters/output mismatches.
 *
 * <ul>
 *     The method groups list in this abstract:
 *     <li>{@link FormatMethod} methods that can format a value to a string then write it to the provided writer.</li>
 *     <li>{@link ParseMethod} methods that can parse a string read from a reader and parse it to the output.</li>
 *     <li>{@link ClassifyMethod} methods that can decide witch class is suitable for the sequence read from the given reader. Then return the reader to it's old position.</li>
 * </ul>
 *
 * @author lsafer
 * @version 0.1.2
 * @implNote you have to navigate this class to where your dynamic methods is. By using annotations.
 * @since 28-Sep-2019
 */
public abstract class AbstractFormat implements Format {
	/**
	 * The dynamic methods of this class.
	 */
	final protected Group<Method> methods = new UnmodifiableGroup<>(Reflectionu.getAllMethods(this.getClass()));
	/**
	 * If this class in a debugging mode or not.
	 *
	 * @implSpec if this set false all null-checks and type-checks should not be executed at runtime.
	 */
	protected boolean DEBUGGING = false;

	@Override
	public <T> Clazz<T> classify(ClassifyToken<T> token) throws IOException {
		Objects.requireNonNull(token, "token");

		for (Method method : this.getClassifyMethods())
			if (this.classify0(method, token))
				return token.output;

		if (token.output == null)
			this.classifyElse(token);

		return token.output;
	}

	@Override
	public <T> Writer format(FormatToken<T> token) throws IOException {
		Objects.requireNonNull(token, "token");

		Method method = this.getFormatMethod(token.klazz.getFamily());

		if (method == null)
			this.formatElse(token);
		else this.format0(method, token);

		return token.output;
	}

	@Override
	public <T> T parse(ParseToken<T> token) throws IOException {
		Objects.requireNonNull(token, "token");

		Method method = this.getParseMethod(token.klazz.getFamily());

		if (method == null)
			this.parseElse(token);
		else this.parse0(method, token);

		return token.output;
	}

	/**
	 * Invoke the given {@link ClassifyMethod} with the given parameters.
	 *
	 * @param method to be invoked
	 * @param token  the classification instance that holds the variables of this classification
	 * @return if the invoked method successfully classified the input and no need for further classifications
	 * @throws IOException              if any I/O exception occurs
	 * @throws NullPointerException     if the given 'token' or 'method' is null
	 * @throws ClassifyException        if any classifying exception occurs
	 * @throws IllegalArgumentException if the given method have limited access. Or if the given method have illegal parameters count
	 */
	protected boolean classify0(Method method, ClassifyToken token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(method, "method");
			Objects.requireNonNull(token, "token");
		}

		try {
			method.setAccessible(true);
			return (boolean) method.invoke(this, token);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(method + " have limited access", e);
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			if (cause instanceof IOException) {
				throw (IOException) cause;
			} else if (cause instanceof ParseException) {
				throw (ParseException) cause;
			} else {
				throw new ParseException(cause);
			}
		}
	}

	/**
	 * Get invoked if no classifying method is found for the given token.
	 *
	 * @param token the classification instance that holds the variables of this classification
	 * @throws IOException          if any I/O exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 * @throws ClassifyException    if any classifying exception occurs
	 * @apiNote called dynamically. No need for direct call
	 */
	protected void classifyElse(ClassifyToken token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		throw new ClassifyException("Can't classify " + token.input);
	}

	/**
	 * Invoke the given {@link FormatMethod} with the given parameters.
	 *
	 * @param method to be invoked
	 * @param token  the formatting instance that holds the variables of this formatting
	 * @throws IOException              if any I/O exception occurs
	 * @throws NullPointerException     if the given 'token' or 'method' is null
	 * @throws FormatException          if any formatting exception occurs
	 * @throws IllegalArgumentException if the given method have limited access. Or if the given method have illegal parameters count
	 */
	protected void format0(Method method, FormatToken token) throws IOException {
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
			if (cause instanceof IOException) {
				throw (IOException) cause;
			} else if (cause instanceof FormatException) {
				throw (FormatException) cause;
			} else {
				throw new FormatException(cause);
			}
		}
	}

	/**
	 * Get invoked if no formatting method is found for the given token.
	 *
	 * @param token the formatting instance that holds the variables of this formatting
	 * @throws IOException          if any I/O exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 * @throws FormatException      if any formatting exception occurs
	 * @apiNote called dynamically. No need for direct call
	 */
	protected void formatElse(FormatToken token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		throw new FormatException("Can't format " + token.input.getClass());
	}

	/**
	 * Get the {@link ClassifyMethod} group.
	 *
	 * @return the {@link ClassifyMethod} group
	 */
	protected Group<Method> getClassifyMethods() {
		return this.methods.subGroup(ClassifyMethod.class, m -> m.isAnnotationPresent(ClassifyMethod.class));
	}

	/**
	 * Get the first formatter method that supports the given class as a parameter. (methods ordered randomly).
	 *
	 * @param klass to query a method for
	 * @return the first format method supports given class. Or null if this class don't have one
	 * @throws NullPointerException if the given class is null
	 */
	protected Method getFormatMethod(Class klass) {
		Objects.requireNonNull(klass, "klass");

		Group<Method> valid = this.methods
				.subGroup(FormatMethod.class, m -> m.isAnnotationPresent(FormatMethod.class))
				.subGroup(klass, m -> Filter.util.test(m.getAnnotation(FormatMethod.class).value(), klass));

		Iterator<Method> i = valid.iterator();
		return i.hasNext() ? i.next() : null;
	}

	/**
	 * Get the first parser method that supports the given class as a parameter. (methods ordered randomly).
	 *
	 * @param klass to query a method for
	 * @return the first parse method supports given class. Or null if this class don't have one
	 * @throws NullPointerException if the given class is null
	 */
	protected Method getParseMethod(Class klass) {
		Objects.requireNonNull(klass, "klass");

		Group<Method> valid = this.methods
				.subGroup(ParseMethod.class, m -> m.isAnnotationPresent(ParseMethod.class))
				.subGroup(klass, m -> Filter.util.test(m.getAnnotation(ParseMethod.class).value(), klass));

		Iterator<Method> i = valid.iterator();
		return i.hasNext() ? i.next() : null;
	}

	/**
	 * Invoke the given {@link ParseMethod} with the given parameters.
	 *
	 * @param method to be invoked
	 * @param token  the parsing instance that holds the variables of this parsing
	 * @throws IOException              if any I/O exception occurs
	 * @throws NullPointerException     if the given 'token' or 'method' is null
	 * @throws ParseException           if any parsing exception occurs
	 * @throws IllegalArgumentException if the given method have limited access. Or if the given method have illegal parameters count
	 */
	protected void parse0(Method method, ParseToken token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(method, "method");
			Objects.requireNonNull(token, "token");
		}

		try {
			method.setAccessible(true);
			method.invoke(this, token);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(method + " have a limited access", e);
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			if (cause instanceof IOException) {
				throw (IOException) cause;
			} else if (cause instanceof ParseException) {
				throw (ParseException) cause;
			} else {
				throw new ParseException(cause);
			}
		}
	}

	/**
	 * Get invoked if no parsing method is found for the given token.
	 *
	 * @param token the parsing instance that holds the variables of this parsing
	 * @throws IOException          if any I/O exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 * @throws ParseException       if any parsing exception occurs
	 * @apiNote called dynamically. No need for direct call
	 */
	protected void parseElse(ParseToken token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		throw new ParseException("Can't parse " + token.klazz);
	}
}
