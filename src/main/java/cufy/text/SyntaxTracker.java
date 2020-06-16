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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Helps to track syntax positioning on a stream.
 *
 * @author lsafer
 * @version 0.1.3
 * @since 21-Nov-2019
 */
public class SyntaxTracker implements Appendable {
	/**
	 * A list of strings to be skipped when seen in a literal fence.
	 */
	protected final List<String> ESCAPABLE;
	/**
	 * The map for literal syntax. Such syntax shouldn't contain any meaningful syntax inside it.
	 */
	protected final Map<String, String> LITERAL;
	/**
	 * The map for nestable syntax. Such syntax can have a nested syntax in it.
	 */
	protected final Map<String, String> NESTABLE;

	/**
	 * An array of the fences currently applied.
	 */
	protected final ArrayList<Map.Entry<String, String>> fences = new ArrayList<>();
	/**
	 * The current top fence.
	 */
	protected Map.Entry<String, String> fence;
	/**
	 * If current fence can't have inner fences.
	 */
	protected boolean literal = false;
	/**
	 * The string read after the start of the current fence or the end of the last removed fence.
	 */
	protected String past = "";

	/**
	 * Initialize a new syntax tracker.
	 *
	 * @param nestable  the syntax that can have a syntax inside it
	 * @param literal   the syntax that can not have a syntax inside it
	 * @param escapable a list of strings to be skipped when seen in a literal fence.
	 */
	public SyntaxTracker(Map<String, String> nestable, Map<String, String> literal, List<String> escapable) {
		this.NESTABLE = nestable;
		this.LITERAL = literal;
		this.ESCAPABLE = escapable;
	}

	@Override
	public Appendable append(CharSequence csq) {
		Objects.requireNonNull(csq, "csq");

		int length = csq.length();

		for (int i = 0; i < length; i++)
			this.append(csq.charAt(i));

		return this;
	}

	@Override
	public Appendable append(CharSequence csq, int start, int end) {
		Objects.requireNonNull(csq, "csq");

		int length = csq.length();

		if (start < 0 || end < 0 || length < start || length < end)
			throw new IndexOutOfBoundsException();

		for (int i = start; i < end; i++)
			this.append(csq.charAt(i));

		return this;
	}

	@Override
	public Appendable append(char c) {
		this.past += c;

		if (this.literal) {
			for (String escapable : this.ESCAPABLE)
				if (this.past.endsWith(escapable)) {
					this.past = "";
					return this;
				}
		}

		if (this.fence != null && this.past.endsWith(this.fence.getValue())) {
			//if there is a fence applied and the past string read ends with the closer string of the current fence
			this.unwrap();
		} else if (!this.literal) {
			//if there is no fence applied or the current fence haven't been closed yet and can have inner fences.
			for (Map.Entry<String, String> fence : this.LITERAL.entrySet())
				if (this.past.endsWith(fence.getKey()))
					this.wrap(fence, true);
			for (Map.Entry<String, String> fence : this.NESTABLE.entrySet())
				if (this.past.endsWith(fence.getKey()))
					this.wrap(fence, false);
		}

		return this;
	}

	/**
	 * Get the size of wrapping applied to this.
	 *
	 * @return the size of the wraps list
	 */
	public int depth() {
		return this.fences.size();
	}

	/**
	 * Returns the end string of the current fence.
	 *
	 * @return the end string of the current fence
	 */
	public String fenceEnd() {
		return this.fence == null ? null : this.fence.getValue();
	}

	/**
	 * Returns the start string of the current fence.
	 *
	 * @return the start string of the current fence.
	 */
	public String fenceStart() {
		return this.fence == null ? null : this.fence.getKey();
	}

	/**
	 * Remove the current top-fence.
	 *
	 * @throws IllegalStateException if `fences.size() == 0`
	 */
	private void unwrap() {
		if (this.fences.size() == 0)
			throw new IllegalStateException("fences.size() == 0");

		int size = this.fences.size();
		this.fences.remove(size - 1);

		this.past = "";
		this.fence = size == 1 ? null : this.fences.get(size - 2);
		this.literal = false;
	}

	/**
	 * Add the given fence.
	 *
	 * @param fence   to be added
	 * @param literal if the given fence can't have inner fences
	 * @throws NullPointerException if the given 'fence' is null
	 */
	private void wrap(Map.Entry<String, String> fence, boolean literal) {
		this.fences.add(fence);

		this.past = "";
		this.fence = fence;
		this.literal = literal;
	}
}
