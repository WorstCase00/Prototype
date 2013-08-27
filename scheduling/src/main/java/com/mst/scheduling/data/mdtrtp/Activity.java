package com.mst.scheduling.data.mdtrtp;

import java.util.Map;
import java.util.Set;

public class Activity implements IActivity {

	private final String id;
	private final Map<IExecutionMode, Map<IRenewableResource, Integer>> modeToResourceConsumptionMap;
	private final Map<IExecutionMode, Integer> modeToProcessingTimeMap;

	public Activity(
			String id,
			Map<IExecutionMode, Map<IRenewableResource, Integer>> modeToResourceConsumptionMap,
			Map<IExecutionMode, Integer> modeToProcessingTimeMap) {
		this.id = id;
		this.modeToResourceConsumptionMap = modeToResourceConsumptionMap;
		this.modeToProcessingTimeMap = modeToProcessingTimeMap;
	}

	@Override
	public int getRenewableResourceConsumption(
			IExecutionMode mode,
			IRenewableResource resource) {
		return this.modeToResourceConsumptionMap.get(mode).get(resource);
	}

	@Override
	public Set<IExecutionMode> getModes() {
		return this.modeToResourceConsumptionMap.keySet();
	}

	@Override
	public Map<IRenewableResource, Integer> getRenewableResourceConsumptions(
			IExecutionMode mode) {
		return this.modeToResourceConsumptionMap.get(mode);
	}

	@Override
	public int getProcessingTime(IExecutionMode mode) {
		return this.modeToProcessingTimeMap.get(mode);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((modeToProcessingTimeMap == null) ? 0
						: modeToProcessingTimeMap.hashCode());
		result = prime
				* result
				+ ((modeToResourceConsumptionMap == null) ? 0
						: modeToResourceConsumptionMap.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modeToProcessingTimeMap == null) {
			if (other.modeToProcessingTimeMap != null)
				return false;
		} else if (!modeToProcessingTimeMap
				.equals(other.modeToProcessingTimeMap))
			return false;
		if (modeToResourceConsumptionMap == null) {
			if (other.modeToResourceConsumptionMap != null)
				return false;
		} else if (!modeToResourceConsumptionMap
				.equals(other.modeToResourceConsumptionMap))
			return false;
		return true;
	}
	
	
}
