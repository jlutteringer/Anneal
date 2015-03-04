package org.alloy.metal.collections;

import java.util.Collection;

import org.alloy.metal.collections.size.FixedSizeInfo;
import org.alloy.metal.collections.size.SizeInfo;
import org.alloy.metal.delegation.Delegator;
import org.alloy.metal.iteration._Iteration;
import org.alloy.metal.iteration.cursor.Cursor;

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
	public Cursor<T> cursor() {
		return _Iteration.cursor(this.getDelegate().iterator());
	}

	@Override
	public SizeInfo getSizeInfo() {
		return new FixedSizeInfo(this.getDelegate().size());
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