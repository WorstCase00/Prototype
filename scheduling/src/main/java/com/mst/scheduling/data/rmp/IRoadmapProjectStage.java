package com.mst.scheduling.data.rmp;

import java.util.Map;

import com.mst.scheduling.data.business.IProjectStageSkill;

public interface IRoadmapProjectStage {

	Map<IProjectStageSkill, Integer> getWorkDemand();

	int getMaxMultiResources();
	
	String getId();
}
