package org.alloy.metal.collections.list;

import java.util.Iterator;
import java.util.List;

import org.alloy.metal.collections.SizeInfo;
import org.alloy.metal.delegation.Delegator;

public class AListAdapter<T> implements AList<T>, Delegator<List<T>> {
	private List<T> list;

	public AListAdapter(List<T> list) {
		this.list = list;
	}

	@Override
	public List<T> getDelegate() {
		return list;
	}

	@Override
	public SizeInfo getSizeInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(T element) {
		return getDelegate().contains(element);
	}

	@Override
	public Iterator<T> iterator() {
		return getDelegate().iterator();
	}

	@Override
	public T get(long index) {
		return getDelegate().get((int) index);
	}
}