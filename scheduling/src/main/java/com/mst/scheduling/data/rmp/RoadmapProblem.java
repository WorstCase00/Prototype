package com.mst.scheduling.data.rmp;

import java.util.Set;

public class RoadmapProblem implements IRoadmapProblem {

	private final Set<IRoadmapProject> roadmapProjects;
	private final Set<IResourceGroup> resourceGroups;
	private final IPriorizationFunction priorizationFunction;
	private final IProjectNetwork projectNetwork;
	
	public RoadmapProblem(Set<IRoadmapProject> roadmapProjects,
			Set<IResourceGroup> resourceGroups,
			IPriorizationFunction priorizationFunction,
			IProjectNetwork projectNetwork) {
		this.roadmapProjects = roadmapProjects;
		this.resourceGroups = resourceGroups;
		this.priorizationFunction = priorizationFunction;
		this.projectNetwork = projectNetwork;
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
	public Set<IRoadmapProject> getDependents(IRoadmapProject project) {
		return this.projectNetwork.getSuccessors(project);
	}

}
