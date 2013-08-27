package com.mst.scheduling.data.business;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.base.Joiner;

public class Story implements IStory {

	private final String id;
	private final String title;
	private final LinkedHashMap<IProjectStage, Map<IProjectStageSkill, IWorkLoad>> stageToWorkLoadMap;
	
	public Story(
			String id, 
			String title,
			LinkedHashMap<IProjectStage, Map<IProjectStageSkill, IWorkLoad>> stageToWorkLoadMap) {
		this.id = id;
		this.title = title;
		this.stageToWorkLoadMap = stageToWorkLoadMap;
	}

	public String getId() {
		return this.id;
	}

	@Override
	public LinkedHashMap<IProjectStage, Map<IProjectStageSkill, IWorkLoad>> getStageToWorkloadMap() {
		return this.stageToWorkLoadMap;
	}

	public String getTitle() {
		return this.title;
	}

	@Override
	public String toString() {
		return "Story [id=" + id + ", title=" + title + ", skillToWorkLoadMap="
				+ Joiner.on(",").join(stageToWorkLoadMap.entrySet()) + "]";
	}

}
