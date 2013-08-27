package com.mst.scheduling.data.mdtrtp;

import java.util.Map;

public interface IExecutionMode {

	int getProcessingTime();
	
	Map<IRenewableResource, Integer> getRenewableResourceConsumptions();
}
