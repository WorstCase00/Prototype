package com.mst.scheduling.data.business;

import org.joda.time.PeriodType;

public interface IWorkLoad {

	float getEstimation();
	
	PeriodType getTimeUnit();
}
