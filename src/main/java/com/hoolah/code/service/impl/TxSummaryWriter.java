package com.hoolah.code.service.impl;

import java.io.PrintStream;

import com.hoolah.code.dto.TxSummary;
import com.hoolah.code.service.ItemWriter;

public class TxSummaryWriter implements ItemWriter<TxSummary> {

	private PrintStream out;

	@Override
	public void write(TxSummary summary) {
		out.println(String.format("Number of transactions = %d", summary.getTxCount()));
		out.println(String.format("Average Transaction Value = %.2f", summary.getAverage()));
	}

	public void setOut(PrintStream out) {
		this.out = out;
	}

}
