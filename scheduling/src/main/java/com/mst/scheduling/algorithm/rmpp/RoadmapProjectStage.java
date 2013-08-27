package com.mst.scheduling.algorithm.rmpp;

import java.util.Map;

import com.mst.scheduling.data.business.IProjectStageSkill;
import com.mst.scheduling.data.rmp.IRoadmapProjectStage;

public class RoadmapProjectStage implements IRoadmapProjectStage {

	private final String id;
	private final Map<IProjectStageSkill, Integer> workDemand;
	private final int maxMultiResource;

	public RoadmapProjectStage(String id,
			Map<IProjectStageSkill, Integer> workDemand, int maxMultiResource) {
		this.id = id;
		this.workDemand = workDemand;
		this.maxMultiResource = maxMultiResource;
	}

	@Override
	public Map<IProjectStageSkill, Integer> getWorkDemand() {
		return this.workDemand;
	}

	@Override
	public int getMaxMultiResources() {
		return this.maxMultiResource;
	}

	@Override
	public String getId() {
		return this.id;
	}

}
