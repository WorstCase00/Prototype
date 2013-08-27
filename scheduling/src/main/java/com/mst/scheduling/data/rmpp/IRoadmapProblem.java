package com.mst.scheduling.data.rmpp;

import java.util.Set;

public interface IRoadmapProblem {

	Set<IResourceGroup> getResourceGroups();

	Set<IRoadmapProject> getRoadmapProjects();

	IPriorizationFunction getPriorizationFunction();

	Set<IRoadmapProject> getPrerequisites(IRoadmapProject project);

	Set<IRoadmapProject> getDependents(IRoadmapProject project);

	
}
