package com.mst.scheduling.data.business;

import java.util.Map;

import com.google.common.base.Joiner;

import static com.google.common.base.Preconditions.*;

public class Employee implements IEmployee {

	private final String name;
	private final int hoursPerWeek;
	private final Map<IProjectStageSkill, SkillLevel> skillMap;

	public Employee(
			String name, 
			int hoursPerWeek,
			Map<IProjectStageSkill, SkillLevel> skillMap) {
		checkNotNull(name);
		checkArgument(hoursPerWeek >= 0);
		checkNotNull(skillMap);
		this.name = name;
		this.hoursPerWeek = hoursPerWeek;
		this.skillMap = skillMap;
	}

	public String getName() {
		return this.name;
	}

	public int getHoursPerWeek() {
		return this.hoursPerWeek;
	}

	public Map<IProjectStageSkill, SkillLevel> getSkillLevelMap() {
		return this.skillMap;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", hoursPerWeek=" + hoursPerWeek
				+ ", skillMap=" + Joiner.on(",").join(skillMap.entrySet()) + "]";
	}
	
	

}
