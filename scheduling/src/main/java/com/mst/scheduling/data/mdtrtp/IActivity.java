package com.mst.scheduling.data.mdtrtp;

import java.util.Map;
import java.util.Set;

public interface IActivity {
	
	int getRenewableResourceConsumption(IExecutionMode mode, IRenewableResource resource);
	
	Set<IExecutionMode> getModes();

	Map<IRenewableResource, Integer> getRenewableResourceConsumptions(IExecutionMode mode);

	int getProcessingTime(IExecutionMode mode);
}
