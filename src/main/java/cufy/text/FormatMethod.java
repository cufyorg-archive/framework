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

import cufy.meta.Filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Navigate the {@link AbstractFormat} class that the annotated method is a stringing method.
 *
 * @author lsafer
 * @version 0.1.3
 * @apiNote the annotated method SHOULD match the {@link AbstractFormat#format0} rules
 * @since 30-Mar-2020
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FormatMethod {
	/**
	 * Classes that the annotated method dose support as a parameter.
	 *
	 * @return the supported classes
	 */
	Filter value() default @Filter;
}
