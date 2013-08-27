package com.mst.scheduling.data.business;

import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;

public class Team implements ITeam {

	private final String name;
	private ImmutableSet<IEmployee> employees;
	
	public Team(String name, Set<IEmployee> employees) {
		this.name = name;
		this.employees = ImmutableSet.copyOf(employees);
	}

	public String getName() {
		return this.name;
	}

	public Set<IEmployee> getEmployees() {
		return this.employees;
	}

	@Override
	public String toString() {
		return "Team [name=" + name + ", employees=" + Joiner.on(",").join(employees) + "]";
	}
	

}
