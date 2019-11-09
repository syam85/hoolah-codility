package com.hoolah.code;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.hoolah.code.dto.Criteria;
import com.hoolah.code.dto.Transaction;
import com.hoolah.code.service.impl.CSVFileItemReader;
import com.hoolah.code.service.impl.Executor;
import com.hoolah.code.service.impl.TransactionMapper;
import com.hoolah.code.service.impl.TransactionProcessor;
import com.hoolah.code.service.impl.TxSummaryWriter;
import com.hoolah.code.util.DateUtils;

public class StatusticsGenerator {

	public static void main(String[] args) throws Exception {

		Options options = new Options();

		Option fileOption = new Option("fp", "file", true, "input file path");
		fileOption.setRequired(true);
		options.addOption(fileOption);

		Option delimiterOption = new Option("dl", "delimiter", true, "delimiter");
		delimiterOption.setRequired(true);
		options.addOption(delimiterOption);

		Option fromDate = new Option("fd", "from date", true, "from date");
		fromDate.setRequired(true);
		options.addOption(fromDate);

		Option toDate = new Option("td", "to date", true, "to date");
		toDate.setRequired(true);
		options.addOption(toDate);

		Option merchantNameOption = new Option("mn", "merhchant name", true, "marchant name");
		merchantNameOption.setRequired(true);
		options.addOption(merchantNameOption);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;

		Criteria request = new Criteria();
		try {
			cmd = parser.parse(options, args);
			request.setFile(new File(cmd.getOptionValue("fp")));
			request.setStartDate(DateUtils.convertDate(cmd.getOptionValue("fd")));
			request.setEndDate(DateUtils.convertDate(cmd.getOptionValue("td")));
			request.setMerchant(cmd.getOptionValue("mn"));
			request.setDelimiter(cmd.getOptionValue("dl"));
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("StatusticsGenerator", options);
			System.exit(1);
		}

		Executor executor = new Executor();

		CSVFileItemReader<Transaction> itemReader = new CSVFileItemReader<Transaction>();
		itemReader.setResource(request.getFile());
		itemReader.setLineMapper(new TransactionMapper(request.getDelimiter()));
		executor.setItemReader(itemReader);

		TransactionProcessor transactionProcessor = new TransactionProcessor();
		transactionProcessor.setCriteria(request);
		executor.setItemProcessor(transactionProcessor);

		TxSummaryWriter itemWriter = new TxSummaryWriter();
		itemWriter.setOut(System.out);
		executor.setItemWriter(itemWriter);

		executor.execute();

	}

}
