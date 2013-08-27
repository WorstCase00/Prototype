package com.mst.scheduling.data.trafo;

import java.util.Map;

import com.mst.scheduling.data.business.IEpic;
import com.mst.scheduling.data.business.IProjectStage;
import com.mst.scheduling.data.business.IProjectStageSkill;
import com.mst.scheduling.data.business.IStory;
import com.mst.scheduling.data.business.IWorkLoad;

public class ProjectStageTask implements IProjectStageTask {

	private final IEpic epic;
	private final IStory story;
	private final Map<IProjectStageSkill, IWorkLoad> workMap;

	public ProjectStageTask(IEpic epic, 
			IStory story,
			IProjectStage stage, 
			Map<IProjectStageSkill, IWorkLoad> workMap) {
		this.epic = epic;
		this.story = story;
		this.workMap = workMap;
	}

	public IEpic getEpic() {
		return epic;
	}

	public IStory getStory() {
		return story;
	}

	public Map<IProjectStageSkill, IWorkLoad> getWorkMap() {
		return workMap;
	}
	

}
