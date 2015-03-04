package org.alloy.metal.collections.list;

import java.util.LinkedList;

public class MutableLinkedListAdapter<T> extends AbstractMutableListAdapter<LinkedList<T>, T> implements MutableLinkedList<T> {
	public MutableLinkedListAdapter(LinkedList<T> list) {
		super(list);
	}

	@Override
	public T pop() {
		return this.getDelegate().pop();
	}
}