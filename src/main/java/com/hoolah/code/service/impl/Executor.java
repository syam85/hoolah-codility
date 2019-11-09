package com.hoolah.code.service.impl;

import static com.hoolah.code.dto.Transaction.Type.REVERSAL;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.hoolah.code.dto.Transaction;
import com.hoolah.code.dto.TxSummary;
import com.hoolah.code.service.ItemProcessor;
import com.hoolah.code.service.ItemReader;
import com.hoolah.code.service.ItemWriter;

public class Executor {

	private ItemReader<Transaction> itemReader = null;
	private ItemProcessor<Transaction, Optional<Transaction>> itemProcessor = null;
	private ItemWriter<TxSummary> itemWriter = null;

	public void setItemReader(ItemReader<Transaction> itemreader) {
		this.itemReader = itemreader;
	}

	public void setItemProcessor(ItemProcessor<Transaction, Optional<Transaction>> itemProcessor) {
		this.itemProcessor = itemProcessor;
	}

	public void setItemWriter(ItemWriter<TxSummary> itemWriter) {
		this.itemWriter = itemWriter;
	}

	public void execute() {
		Set<String> reversalTx = new HashSet<>();
		List<Transaction> matchedTx = new ArrayList<>();

		while (true) {
			Transaction tx = itemReader.read();
			if (tx == null) {
				break;
			}
			if (reversalTx.contains(tx.getId())) {
				continue;
			}

			if (REVERSAL.equals(tx.getType())) {
				reversalTx.add(tx.getRelatedTransaction());
			}
			Optional<Transaction> optionalTx = itemProcessor.process(tx);
			if (optionalTx.isPresent()) {
				Transaction processedTx = optionalTx.get();
				if (reversalTx.contains(processedTx.getId())) {
					continue;
				}

				matchedTx.add(optionalTx.get());
			}
		}
		DoubleSummaryStatistics summaryStatistics = matchedTx.stream()
				.filter(elem -> !reversalTx.contains(elem.getId())).map(elem -> elem.getAmount())
				.mapToDouble((elem) -> elem).summaryStatistics();

		TxSummary summary = new TxSummary();
		summary.setAverage(summaryStatistics.getAverage());
		summary.setTxCount(summaryStatistics.getCount());
		itemWriter.write(summary);

	}

}
