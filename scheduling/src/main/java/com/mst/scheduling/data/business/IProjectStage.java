package com.mst.scheduling.data.business;

import java.util.Set;

public interface IProjectStage {
	
	String getName();

	Set<IProjectStageSkill> getProjectStageSkills();
}
