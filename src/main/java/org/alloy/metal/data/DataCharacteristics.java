package org.alloy.metal.data;

import org.alloy.metal.collections.set.ASet;
import org.alloy.metal.collections.set._Sets;

public class DataCharacteristics {
	private ASet<DataRestriction> dataRestrictions;

	public DataCharacteristics(DataRestriction... dataRestrictions) {
		this.dataRestrictions = _Sets.set(dataRestrictions);
	}

	public ASet<DataRestriction> getDataRestrictions() {
		return dataRestrictions;
	}

	public static enum DataRestriction {
		SERIAL
	}
}
