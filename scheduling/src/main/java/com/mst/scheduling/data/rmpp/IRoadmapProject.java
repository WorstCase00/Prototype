package com.mst.scheduling.data.rmpp;

import java.util.List;
import java.util.Map;

import com.mst.scheduling.data.business.IProjectStageSkill;

public interface IRoadmapProject {

	Map<IProjectStageSkill, Integer> getWorkTypeDemands();

	List<IRoadmapProjectStage> getProjectStages();
	
	String getId();
}
