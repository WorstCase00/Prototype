package com.mst.scheduling.data.rmpp;

import com.mst.function.IDiscreteInterval;
import com.mst.function.IDiscreteStepFunction;
import com.mst.scheduling.data.business.IProjectStageSkill;

public interface IRoadmapSchedule {

	int getReleaseTime(IRoadmapProject project);

	IDiscreteStepFunction getFreeFunction(IMultiResource resource);

	void schedule(
			IRoadmapProject project, 
			IRoadmapProjectStage stage,
			IMultiResource resource,
			IProjectStageSkill key, 
			IDiscreteInterval interval);

	void endProject(IRoadmapProject project, int releaseTime);
	
	int getMakeSpan();

}
