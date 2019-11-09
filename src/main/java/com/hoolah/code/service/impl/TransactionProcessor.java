package com.hoolah.code.service.impl;

import java.util.Optional;

import com.hoolah.code.dto.Criteria;
import com.hoolah.code.dto.Transaction;
import com.hoolah.code.service.ItemProcessor;

public class TransactionProcessor implements ItemProcessor<Transaction, Optional<Transaction>> {

	private Criteria criteria;

	@Override
	public Optional<Transaction> process(Transaction item) {
		if (isCriteriaMatched(item)) {
			return Optional.of(item);
		}

		return Optional.empty();
	}

	private boolean isCriteriaMatched(Transaction item) {
		return criteria.getMerchant().equals(item.getMerchant()) && criteria.getStartDate().compareTo(item.getDate())
				* item.getDate().compareTo(criteria.getEndDate()) > 0;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

}
