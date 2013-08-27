package com.mst.scheduling.data.trafo;

import com.mst.scheduling.data.business.IProjectStageSkill;
import com.mst.scheduling.data.business.IWorkLoad;
import com.mst.scheduling.data.business.ProjectStageSkill;

public interface IStoryStageSkillTask {

	String getStoryId();
	
	IProjectStageSkill getSkill();
	
	IWorkLoad getWorkLoad();
}
