package org.alloy.metal.string;

import java.io.Serializable;
import java.util.NoSuchElementException;

import org.alloy.metal.iteration.FetchingIterator;
import org.alloy.metal.string.StringTokenIterator.StringToken;
import org.alloy.metal.utility._Precondition;

public class StringTokenIterator extends FetchingIterator<StringToken> {
	private final String tokenPrefix;
	private final String tokenSuffix;
	private final String source;

	private int currentLocation = 0;

	public StringTokenIterator(String tokenPrefix, String tokenSuffix, String source) {
		_Precondition.notNull(tokenPrefix, tokenSuffix);

		this.tokenPrefix = tokenPrefix;
		this.tokenSuffix = tokenSuffix;
		this.source = _String.getDefault(source);
	}

	@Override
	protected StringToken fetch() throws NoSuchElementException {
		if (currentLocation >= source.length() - 1) {
			throw new NoSuchElementException();
		}

		int tokenPrefixIndex = source.indexOf(tokenPrefix, currentLocation);
		if (tokenPrefixIndex == -1) {
			throw new NoSuchElementException();
		}

		currentLocation = tokenPrefixIndex + tokenPrefix.length();

		int tokenSuffixIndex = source.indexOf(tokenSuffix, currentLocation);
		if (tokenSuffixIndex == -1) {
			throw new NoSuchElementException();
		}

		currentLocation = tokenSuffixIndex + tokenSuffix.length();

		String value = source.substring(tokenPrefixIndex, tokenSuffixIndex);
		return new StringToken(value, tokenPrefixIndex);
	}

	public static class StringToken implements Serializable {
		private static final long serialVersionUID = -8252126400328088545L;

		private final String value;
		private final int sourceIndex;

		public StringToken(String value, int sourceIndex) {
			super();
			this.value = value;
			this.sourceIndex = sourceIndex;
		}

		public String getValue() {
			return value;
		}

		public int getSourceIndex() {
			return sourceIndex;
		}
	}
}