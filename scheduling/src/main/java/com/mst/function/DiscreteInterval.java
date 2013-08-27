package com.mst.function;

public class DiscreteInterval implements IDiscreteInterval {

	private final int upper;
	private final int lower;

	public DiscreteInterval(int lower, int upper) {
		this.upper = upper;
		this.lower = lower;
	}

	@Override
	public int getUpper() {
		return this.upper;
	}

	@Override
	public int getLower() {
		return this.lower;
	}

	@Override
	public int getSize() {
		return this.upper - this.lower;
	}

}
