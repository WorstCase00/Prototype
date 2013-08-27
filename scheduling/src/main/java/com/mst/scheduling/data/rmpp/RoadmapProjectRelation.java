package com.mst.scheduling.data.rmpp;
import static com.google.common.base.Preconditions.*;
public class RoadmapProjectRelation implements IRoadmapProjectRelation {

	private final IRoadmapProject source;
	private final IRoadmapProject target;
	private final float prerequisitePercentage;
	
	public RoadmapProjectRelation(
			IRoadmapProject source,
			IRoadmapProject target, 
			float prerequisitePercentage) {
		checkNotNull(source);
		checkNotNull(target);
		this.source = source;
		this.target = target;
		this.prerequisitePercentage = prerequisitePercentage;
	}

	@Override
	public IRoadmapProject getSource() {
		return this.source;
	}

	@Override
	public IRoadmapProject getDepending() {
		return this.target;
	}

	@Override
	public float getPrerequisitePercentage() {
		return this.prerequisitePercentage;
	}

}
