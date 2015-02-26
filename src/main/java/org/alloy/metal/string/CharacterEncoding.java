package org.alloy.metal.string;

public enum CharacterEncoding {
	UTF_8("UTF-8");

	private String encodingString;

	private CharacterEncoding(String encodingString) {
		this.encodingString = encodingString;
	}

	@Override
	public String toString() {
		return encodingString;
	}
}