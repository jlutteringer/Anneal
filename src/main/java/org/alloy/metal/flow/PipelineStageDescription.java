package org.alloy.metal.flow;

import org.alloy.metal.data.DataCharacteristics;

public class PipelineStageDescription {
	public static final PipelineStageDescription DEFAULT = new PipelineStageDescription(new DataCharacteristics(), new DataCharacteristics());

	private DataCharacteristics inputCharacteristics;
	private DataCharacteristics outputCharacteristics;

	public PipelineStageDescription(DataCharacteristics inputCharacteristics, DataCharacteristics outputCharacteristics) {
		this.inputCharacteristics = inputCharacteristics;
		this.outputCharacteristics = outputCharacteristics;
	}

	public DataCharacteristics getInputCharacteristics() {
		return inputCharacteristics;
	}

	public DataCharacteristics getOutputCharacteristics() {
		return outputCharacteristics;
	}
}