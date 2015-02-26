package org.alloy.metal.utility;

import org.alloy.metal.domain.Container;
import org.alloy.metal.flow.Source;
import org.alloy.metal.iteration._Iteration;

public class _Range {
	public static Source<Long> range(long start, long end) {
		return _Iteration.<Long> iterable(() -> {
			Container<Long> container = Container.create(start);
			return (consumer) -> {
				Long value = container.getValue();
				if (value <= end) {
					consumer.accept(value);
					container.setValue(value + 1);
					return true;
				}

				return false;
			};
		});
	}
}
