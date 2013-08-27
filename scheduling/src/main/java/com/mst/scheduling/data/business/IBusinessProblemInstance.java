package com.mst.scheduling.data.business;

import java.util.List;
import java.util.Set;

public interface IBusinessProblemInstance {

	Set<ITeam> getTeams();
	
	List<IProjectStage> getProjectStages();
}
