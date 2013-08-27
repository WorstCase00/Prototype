package com.mst.scheduling.data.rmp;

import java.util.Map;

import com.mst.scheduling.data.business.IProjectStageSkill;

public interface IMultiResource {
	
	String getId();

	Map<IProjectStageSkill, Double> getWorkSupplyMap();
}
