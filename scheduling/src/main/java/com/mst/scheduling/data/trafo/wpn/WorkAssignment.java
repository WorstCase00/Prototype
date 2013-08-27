package com.mst.scheduling.data.trafo.wpn;

import com.mst.function.IDiscreteInterval;
import com.mst.scheduling.data.business.IProjectStageSkill;
import com.mst.scheduling.data.rmpp.IRoadmapProject;
import com.mst.scheduling.data.rmpp.IRoadmapProjectStage;

public class WorkAssignment {

	private final IRoadmapProject project;
	private final IRoadmapProjectStage stage;
	private final IProjectStageSkill skill;
	private final IDiscreteInterval interval;

	public WorkAssignment(IRoadmapProject project, IRoadmapProjectStage stage,
			IProjectStageSkill skill, IDiscreteInterval interval) {
		this.project = project;
		this.stage = stage;
		this.skill = skill;
		this.interval = interval;
	}

	public IRoadmapProject getProject() {
		return project;
	}

	public IRoadmapProjectStage getStage() {
		return stage;
	}

	public IProjectStageSkill getSkill() {
		return skill;
	}

	public IDiscreteInterval getInterval() {
		return interval;
	}
}
