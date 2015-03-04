package org.alloy.metal.collections.size;

public class FixedSizeInfo implements SizeInfo {
	private int size;

	public FixedSizeInfo(int size) {
		this.size = size;
	}

	@Override
	public int getFixedSize() {
		return size;
	}
}