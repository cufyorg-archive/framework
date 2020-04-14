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

/**
 * Thrown to indicate that the application has attempted to convert an object to a string but that object does not have the appropriate type.
 *
 * @author lsafer
 * @version 0.1.3
 * @since 18-Nov-2019
 */
public class FormatException extends RuntimeException {
	/**
	 * Constructs a new exception with null as its detail message. The cause is not initialized, and may subsequently be initialized by a call to
	 * Throwable.initCause(java.lang.Throwable).
	 */
	public FormatException() {
	}

	/**
	 * Constructs a new exception with the specified arguments.
	 *
	 * @param msg the message
	 */
	public FormatException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a new exception with the specified arguments.
	 *
	 * @param cause the cause
	 */
	public FormatException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new exception with the specified arguments.
	 *
	 * @param msg   the message
	 * @param cause the cause
	 */
	public FormatException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Constructs a new exception with the specified detail message, cause, suppression enabled or disabled, and writable stack trace enabled or
	 * disabled.
	 *
	 * @param message            the detail message.
	 * @param cause              the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param enableSuppression  whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	protected FormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
