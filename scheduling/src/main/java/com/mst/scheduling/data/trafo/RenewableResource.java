package com.mst.scheduling.data.trafo;

import com.mst.function.IDiscreteStepFunction;
import com.mst.scheduling.data.mdtrtp.IRenewableResource;

public class RenewableResource implements IRenewableResource {

	private String id;
	private IDiscreteStepFunction availabilityFunction;

	public RenewableResource(String id, IDiscreteStepFunction availabilityFunction) {
		this.id = id;
		this.availabilityFunction = availabilityFunction;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IDiscreteStepFunction getAvailabilityFunction() {
		return availabilityFunction;
	}

	public void setAvailabilityFunction(IDiscreteStepFunction availabilityFunction) {
		this.availabilityFunction = availabilityFunction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RenewableResource other = (RenewableResource) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
