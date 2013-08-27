package com.mst.scheduling.data.business;

import java.util.Map;

public interface IEmployee {

	String getName();
	
	int getHoursPerWeek();
	
	Map<IProjectStageSkill, SkillLevel> getSkillLevelMap();
}
