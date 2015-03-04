package org.alloy.metal.collections.list;

import java.util.List;

public class MutableListAdapter<T> extends AbstractMutableListAdapter<List<T>, T> {
	public MutableListAdapter(List<T> list) {
		super(list);
	}
}