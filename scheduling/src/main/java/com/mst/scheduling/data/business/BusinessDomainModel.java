package com.mst.scheduling.data.business;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class BusinessDomainModel {

	private final ImmutableSet<ITeam> teams;
	private final ImmutableList<IProjectStage> stages;
	private final ImmutableSet<IEpic> epics;
	private final ImmutableSet<IStoryRelation> storyRelations;

	public BusinessDomainModel(
			Set<ITeam> teams, 
			List<IProjectStage> stages,
			Set<IEpic> epics,
			Set<IStoryRelation> storyRelations) {
		this.teams = ImmutableSet.copyOf(teams);
		this.stages = ImmutableList.copyOf(stages);
		this.epics = ImmutableSet.copyOf(epics);
		this.storyRelations = ImmutableSet.copyOf(storyRelations);
	}

	public ImmutableSet<ITeam> getTeams() {
		return teams;
	}

	public ImmutableList<IProjectStage> getStages() {
		return stages;
	}

	public ImmutableSet<IEpic> getEpics() {
		return epics;
	}
	
	public ImmutableSet<IStoryRelation> getStoryRelations() {
		return this.storyRelations;
	}
	
}
