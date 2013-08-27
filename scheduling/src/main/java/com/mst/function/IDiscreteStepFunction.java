package com.mst.function;

public interface IDiscreteStepFunction {

	int getXWhereYGeq(int value, int minX);

	int getXWhereYLeq(int value, int minX);

	void add(IDiscreteInterval interval, int i);

}
