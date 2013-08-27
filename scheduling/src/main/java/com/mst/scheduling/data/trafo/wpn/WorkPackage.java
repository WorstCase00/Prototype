package com.mst.scheduling.data.trafo.wpn;

import java.util.Map;

import com.mst.scheduling.data.business.IProjectStageSkill;

public class WorkPackage implements IWorkPackage {

	private final String id;
	private final Map<IProjectStageSkill, Double> workDemandMap;
	
	public WorkPackage(String id, Map<IProjectStageSkill, Double> workDemandMap) {
		this.id = id;
		this.workDemandMap = workDemandMap;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public Map<IProjectStageSkill, Double> getWorkDemandMap() {
		return this.workDemandMap;
	}
}
