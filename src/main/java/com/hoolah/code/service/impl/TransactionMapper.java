package com.hoolah.code.service.impl;

import com.hoolah.code.dto.Transaction;
import com.hoolah.code.dto.Transaction.Type;
import com.hoolah.code.service.LineMapper;
import com.hoolah.code.util.DateUtils;

public class TransactionMapper implements LineMapper<Transaction> {

	private static final String DEFAULT_DELIMETER = ",";

	private String delimeter;

	public TransactionMapper() {
		delimeter = DEFAULT_DELIMETER;
	}

	public TransactionMapper(String delimeter) {
		this.delimeter = delimeter;
	}

	@Override
	public Transaction mapLine(String line) {
		String[] data = line.split(delimeter);
		Transaction theTransaction = new Transaction();
		theTransaction.setId(data[0].trim());
		theTransaction.setDate(DateUtils.convertDate(data[1].trim()));
		theTransaction.setAmount(Double.valueOf(data[2].trim()));
		theTransaction.setMerchant(data[3].trim());
		theTransaction.setType(Type.valueOf(data[4].trim()));
		theTransaction.setRelatedTransaction(data.length == 6 ? data[5].trim() : null);
		return theTransaction;
	}

}
