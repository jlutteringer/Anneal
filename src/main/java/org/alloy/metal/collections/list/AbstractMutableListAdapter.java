package org.alloy.metal.collections.list;

import java.util.List;

public abstract class AbstractMutableListAdapter<N extends List<T>, T> extends AListAdapter<N, T> implements MutableList<T> {
	public AbstractMutableListAdapter(N list) {
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
	public boolean add(int index, T element) {
		getDelegate().add(index, element);
		return true;
	}

	@Override
	public T set(int index, T element) {
		return this.getDelegate().set(index, element);
	}

	@Override
	public List<T> asList() {
		return this.getDelegate();
	}

	@Override
	public void clear() {
		this.getDelegate().clear();
	}
}
