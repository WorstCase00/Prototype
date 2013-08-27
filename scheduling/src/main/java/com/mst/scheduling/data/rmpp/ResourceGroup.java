package com.mst.scheduling.data.rmpp;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Maps;
import com.mst.scheduling.data.business.IProjectStageSkill;

public class ResourceGroup implements IResourceGroup {

	private final Set<IMultiResource> multiResource;
	private final Map<IProjectStageSkill, Double> workTypeSupplyMap;

	public ResourceGroup(Set<IMultiResource> multiResources) {
		this.multiResource = multiResources;
		this.workTypeSupplyMap = initWorkTypeSupplyMap(multiResources);
		
	}

	private Map<IProjectStageSkill, Double> initWorkTypeSupplyMap(
			Set<IMultiResource> multiResources) {
		Map<IProjectStageSkill, Double> workSumyMap = Maps.newHashMap();
		for(IMultiResource resource : multiResources) {
			Map<IProjectStageSkill, Double> supplyMap = resource.getWorkSupplyMap();
			for(Entry<IProjectStageSkill, Double> supplyEntry : supplyMap.entrySet()) {
				IProjectStageSkill workType = supplyEntry.getKey();
				if(!workSumyMap.containsKey(workType)) {
					workSumyMap.put(workType, 0d);
				}
				double sum = workSumyMap.get(workType);
				sum += supplyEntry.getValue();
				workSumyMap.put(workType, sum);
			}
		}
		return workSumyMap;
	}

	@Override
	public Set<IMultiResource> getMultiResources() {
		return this.multiResource;
	}

	@Override
	public Map<IProjectStageSkill, Double> getWorkTypeSupply() {
		return this.workTypeSupplyMap;
	}

}
