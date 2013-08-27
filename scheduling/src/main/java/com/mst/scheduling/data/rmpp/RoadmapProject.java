package com.mst.scheduling.data.rmpp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.mst.scheduling.data.business.IProjectStageSkill;

public class RoadmapProject implements IRoadmapProject {

	private static final String START_ID = "start";
	public static final IRoadmapProject START_DUMMY = new RoadmapProject(START_ID, new ArrayList<IRoadmapProjectStage>());
	private static final String END_ID = "end";
	public static final IRoadmapProject END_DUMMY =  new RoadmapProject(END_ID, new ArrayList<IRoadmapProjectStage>());;

	private final String id;
	private final List<IRoadmapProjectStage> roadmapProjectStages;
	private final Map<IProjectStageSkill, Integer> workTypeDemands;
	
	public RoadmapProject(String id,
			List<IRoadmapProjectStage> roadmapProjectStages) {
		this.id = id;
		this.roadmapProjectStages = roadmapProjectStages;
		this.workTypeDemands = initWorkTypeDemands(roadmapProjectStages);
	}

	private Map<IProjectStageSkill, Integer> initWorkTypeDemands(
			List<IRoadmapProjectStage> roadmapProjectStages) {
		Map<IProjectStageSkill, Integer> allDemands = Maps.newHashMap();
		for(IRoadmapProjectStage stage : roadmapProjectStages) {
			allDemands.putAll(stage.getWorkDemand());
		}
		return allDemands;
	}

	@Override
	public Map<IProjectStageSkill, Integer> getWorkTypeDemands() {
		return this.workTypeDemands;
	}

	@Override
	public List<IRoadmapProjectStage> getProjectStages() {
		return this.roadmapProjectStages;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "RoadmapProject [id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoadmapProject other = (RoadmapProject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}