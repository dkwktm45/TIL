package com.batch.sample.custom;

import org.springframework.batch.item.file.transform.LineAggregator;

public class CustomPassThrouhLineAggregator<T> implements LineAggregator<T> {
	@Override
	public String aggregate(T item) {
		return item.toString();
	}
}
