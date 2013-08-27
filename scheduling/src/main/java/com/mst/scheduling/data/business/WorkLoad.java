package com.mst.scheduling.data.business;
import static com.google.common.base.Preconditions.*;
import org.joda.time.PeriodType;

public class WorkLoad implements IWorkLoad {

	private final float estimation;
	private final PeriodType timeUnit;

	public WorkLoad(float estimation, PeriodType timeUnit) {
		checkArgument(estimation >= 0);
		checkNotNull(timeUnit);
		this.estimation = estimation;
		this.timeUnit = timeUnit;
	}

	public float getEstimation() {
		return this.estimation;
	}

	public PeriodType getTimeUnit() {
		return this.timeUnit;
	}

	@Override
	public String toString() {
		return "WorkLoad [estimation=" + estimation + "]";
	}

}
