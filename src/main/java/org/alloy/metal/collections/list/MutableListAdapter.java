package org.alloy.metal.collections.list;

import java.util.List;

public class MutableListAdapter<T> extends AListAdapter<T> implements MutableList<T> {
	public MutableListAdapter(List<T> list) {
		super(list);
	}

	@Override
	public boolean add(T element) {
		return getDelegate().add(element);
	}

	@Override
	public boolean remove(T element) {
		return getDelegate().remove(element);
	}

	@Override
	public boolean add(long index, T element) {
		getDelegate().add((int) index, element);
		return true;
	}

	@Override
	public T set(long index, T element) {
		// TODO Auto-generated method stub
		return null;
	}
}