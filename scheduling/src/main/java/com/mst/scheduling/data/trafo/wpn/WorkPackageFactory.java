package com.mst.scheduling.data.trafo.wpn;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.mst.scheduling.data.business.IProjectStage;
import com.mst.scheduling.data.business.IProjectStageSkill;
import com.mst.scheduling.data.business.IStory;
import com.mst.scheduling.data.business.IWorkLoad;

class WorkPackageFactory {

	private static final String START_PREFIX = "Start-";
	private static final String END_PREFIX = "End-";
	
	static IWorkPackage createStartNode(IStory story) {
		String id = START_PREFIX + story.getId();
		return createEmpty(id);
	}

	private static IWorkPackage createEmpty(String id) {
		Map<IProjectStageSkill, Double> empty = Maps.newHashMapWithExpectedSize(0);
		IWorkPackage node = new WorkPackage(id, empty);
		return node;
	}

	static IWorkPackage createEndNode(IStory story) {
		String id = END_PREFIX + story.getId();
		return createEmpty(id);
	}

	static IWorkPackage createStartNode(IProjectStage stage, IStory story) {
		String id = START_PREFIX + story.getId() + stage.getName();
		return createEmpty(id);
	}

	static IWorkPackage createEndNode(IProjectStage stage, IStory story) {
		String id = END_PREFIX + story.getId() + stage.getName();
		return createEmpty(id);
	}

	static IWorkPackage createPackage(IStory story,
			Entry<IProjectStageSkill, IWorkLoad> skillToWorkEntry) {
		IProjectStageSkill stageSkill = skillToWorkEntry.getKey();
		String id = story.getId() + stageSkill.getName();
		Map<IProjectStageSkill, Double> workSpec = Maps.newHashMapWithExpectedSize(1);
		Double value = (double) skillToWorkEntry.getValue().getEstimation();
		workSpec.put(skillToWorkEntry.getKey(), value);
		IWorkPackage workPackage = new WorkPackage(id, workSpec);
		return workPackage;
	}

	public static IWorkPackage createStartPackage() {
		return createEmpty(START_PREFIX);
	}

	public static IWorkPackage createEndPackage() {
		return createEmpty(END_PREFIX);
	}
}
