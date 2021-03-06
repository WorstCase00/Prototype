package com.mst.scheduling.data.rmpp;

import java.util.Map;
import java.util.Set;

import com.mst.scheduling.data.business.IProjectStageSkill;

public interface IResourceGroup {

	Set<IMultiResource> getMultiResources();

	Map<IProjectStageSkill, Double> getWorkTypeSupply();
}
