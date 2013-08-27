package com.mst.scheduling.data.mdtrtp;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.mst.function.IDiscreteStepFunction;

public class RenewableResourceAvailability implements IRenewableResourceAvailability {

	private Set<IRenewableResource> resources;
	
	public RenewableResourceAvailability(Collection<IRenewableResource> values) {
		this.resources = Sets.newHashSet(values);
	}

	@Override
	public Set<IRenewableResource> getRenewableResources() {
		return this.resources;
	}

}
