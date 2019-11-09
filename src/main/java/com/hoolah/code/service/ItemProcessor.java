package com.hoolah.code.service;

public interface ItemProcessor<I, O> {
	O process(I item);
}
