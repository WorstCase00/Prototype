package com.mst.scheduling.data.rmp;

import java.util.Map;

import com.mst.scheduling.data.business.IProjectStageSkill;

public class MultiResource implements IMultiResource {

	private final String id;
	private final Map<IProjectStageSkill, Double> resourceSupplyMap;

	public MultiResource(String id, Map<IProjectStageSkill, Double> resourceSupplyMap) {
		this.id = id;
		this.resourceSupplyMap = resourceSupplyMap;
	}

	@Override
	public Map<IProjectStageSkill, Double> getWorkSupplyMap() {
		return this.resourceSupplyMap;
	}
	
	@Override
	public String getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return this.id;
	}


}
