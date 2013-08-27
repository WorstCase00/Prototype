package com.mst.scheduling.data.trafo;

import java.util.Map;

import com.mst.scheduling.data.mdtrtp.IExecutionMode;
import com.mst.scheduling.data.mdtrtp.IRenewableResource;

public class ExecutionMode implements IExecutionMode {

	private final int processingTime;
	private final Map<IRenewableResource, Integer> renewableResourceConsumptions;

	public ExecutionMode(int processingTime,
			Map<IRenewableResource, Integer> renewableResourceConsumptions) {
		this.processingTime = processingTime;
		this.renewableResourceConsumptions = renewableResourceConsumptions;
	}

	@Override
	public int getProcessingTime() {
		return this.processingTime;
	}

	@Override
	public Map<IRenewableResource, Integer> getRenewableResourceConsumptions() {
		return this.renewableResourceConsumptions;
	}

}
