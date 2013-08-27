package com.mst.scheduling.data.trafo.wpn;

import java.util.Map;

import com.mst.scheduling.data.business.IProjectStageSkill;

public interface IWorkPackage {

	String getId();
	
	Map<IProjectStageSkill, Double> getWorkDemandMap();
}
