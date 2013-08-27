package com.mst.scheduling.data.rmpp;

import java.util.Set;

public class RoadmapProblem implements IRoadmapProblem {

	private final Set<IRoadmapProject> roadmapProjects;
	private final Set<IResourceGroup> resourceGroups;
	private final IPriorizationFunction priorizationFunction;
	private final IProjectNetwork projectNetwork;
	private final IPlanningCycleDefinition planningCycleDefinition;
	
	public RoadmapProblem(Set<IRoadmapProject> roadmapProjects,
			Set<IResourceGroup> resourceGroups,
			IPriorizationFunction priorizationFunction,
			IProjectNetwork projectNetwork, 
			IPlanningCycleDefinition planningCycleDefinition) {
		this.roadmapProjects = roadmapProjects;
		this.resourceGroups = resourceGroups;
		this.priorizationFunction = priorizationFunction;
		this.projectNetwork = projectNetwork;
		this.planningCycleDefinition = planningCycleDefinition;
	}

	
	@Override
	public Set<IResourceGroup> getResourceGroups() {
		return this.resourceGroups;
	}

	@Override
	public Set<IRoadmapProject> getRoadmapProjects() {
		return this.roadmapProjects;
	}

	@Override
	public IPriorizationFunction getPriorizationFunction() {
		return this.priorizationFunction;
	}

	@Override
	public Set<IRoadmapProject> getPrerequisites(IRoadmapProject project) {
		return this.projectNetwork.getPredecessors(project);
	}

	@Override
	public IPlanningCycleDefinition getPlanningCycleDefinition() {
		return this.planningCycleDefinition;
	}

	@Override
	public Set<IRoadmapProjectRelation> getDependentRelations(
			IRoadmapProject project) {
		return this.projectNetwork.getOutgoingEdgesOf(project);
	}

}
