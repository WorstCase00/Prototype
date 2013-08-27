package com.mst.scheduling.data.business;

public enum SkillLevel {

	ONE(2.0),
	TWO(1.3),
	THREE(1);
	
	private SkillLevel(double executionTimeFacor) {
		this.executionTimeFactor = executionTimeFacor;
	}

	private final double executionTimeFactor;

	public static SkillLevel parse(String level) {
		if(level.equalsIgnoreCase("1")) {
			return ONE;
		} else if (level.equalsIgnoreCase("2")) {
			return TWO;
		} else if(level.equalsIgnoreCase("3")) {
			return THREE;
		}
		return null;
	}
	
	public static double getSupplyFactor(SkillLevel level) {
		return 1d / level.executionTimeFactor;
	}
}
