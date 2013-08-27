package com.mst.function;

import java.util.Collections;
import java.util.List;

import com.google.common.primitives.Ints;

public class DiscreteStepFunction implements IDiscreteStepFunction {

	private final List<Integer> xs;
	private final List<Integer> ys;

	public DiscreteStepFunction(int[] x, int[] y) {
		this.xs = Ints.asList(x);
		this.ys = Ints.asList(y);
	}

	public DiscreteStepFunction(int x, int y) {
		this.xs = Ints.asList(x);
		this.ys = Ints.asList(y);
	}

	@Override
	public int getXWhereYGeq(int value, int minX) {
		int xIndex = getInsertionIndex(minX);
		for (int i = xIndex; i < xs.size(); i++) {
			int fx = ys.get(xIndex);
			if(fx >= value) {
				return xs.get(xIndex);
			}
		}
		return Integer.MAX_VALUE;
	}

	private int getInsertionIndex(int x) {
		int index = Collections.binarySearch(xs, x);
		if(index >= 0) {
			return index;
		} else if(index < -1) {
			return -index -2;
		}
		return 0;
	}

	@Override
	public int getXWhereYLeq(int value, int minX) {
		int xIndex = getInsertionIndex(minX);
		for (int i = xIndex; i < xs.size(); i++) {
			int fx = ys.get(xIndex);
			if(fx <= value) {
				return xs.get(xIndex);
			}
		}
		return Integer.MAX_VALUE;
	}

	public int value(int x) {
		int index = getInsertionIndex(x);
		return ys.get(xs.get(index));
	}

	@Override
	public void add(IDiscreteInterval interval, int value) {
		int start = interval.getLower();
		int startIndex = Collections.binarySearch(xs, start);
		if(startIndex < -1) {
			int insertIndex = -startIndex - 1;
			int insertYValue = ys.get(insertIndex - 1) + value;
			xs.add(insertIndex, start);
			ys.add(insertIndex, insertYValue);
			startIndex = insertIndex + 1;
		}
		
		int end = interval.getUpper() - 1;
		int endIndex = Collections.binarySearch(xs, start);
		if(endIndex < -1) {
			int insertIndex = -endIndex - 1;
			int insertYValue = ys.get(insertIndex - 1) + end;
			xs.add(insertIndex, start);
			ys.add(insertIndex, insertYValue);
			endIndex = insertIndex - 1;
		}
		
		for (int i = startIndex; i < endIndex; i++) {
			ys.set(i, ys.get(i) + value);
		}
	}

}
