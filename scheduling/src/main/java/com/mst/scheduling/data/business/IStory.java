package com.mst.scheduling.data.business;

import java.util.LinkedHashMap;
import java.util.Map;

public interface IStory {

	String getId();
	
	String getTitle();
	
	LinkedHashMap<IProjectStage, Map<IProjectStageSkill, IWorkLoad>> getStageToWorkloadMap();
}
