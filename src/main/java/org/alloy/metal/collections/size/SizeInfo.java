package org.alloy.metal.collections.size;

public interface SizeInfo {
	public default int getFixedSize() {
		return Integer.MAX_VALUE;
	}
}