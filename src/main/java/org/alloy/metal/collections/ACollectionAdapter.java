package org.alloy.metal.collections;

import java.util.Collection;
import java.util.Iterator;

import org.alloy.metal.delegation.Delegator;

public class ACollectionAdapter<T> implements ACollection<T>, Delegator<Collection<T>> {
	private Collection<T> collection;

	public ACollectionAdapter(Collection<T> collection) {
		this.collection = collection;
	}

	@Override
	public Collection<T> getDelegate() {
		return collection;
	}

	@Override
	public Iterator<T> iterator() {
		return this.getDelegate().iterator();
	}

	@Override
	public SizeInfo getSizeInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(T element) {
		return this.getDelegate().contains(element);
	}

	@Override
	public Collection<T> asCollection() {
		return getDelegate();
	}
}