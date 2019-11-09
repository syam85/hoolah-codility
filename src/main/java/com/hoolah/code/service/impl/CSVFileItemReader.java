package com.hoolah.code.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

import com.hoolah.code.service.ItemReader;
import com.hoolah.code.service.LineMapper;

public class CSVFileItemReader<T> implements ItemReader<T> {

	public static final String DEFAULT_CHARSET = Charset.defaultCharset().name();

	private BufferedReader reader;

	private File resource;

	private LineMapper<T> lineMapper;

	private boolean noInput = false;

	private String[] comments = new String[] { "#" };

	private long lineCount = 0;

	private boolean skipHeader = true;

	public void setLineMapper(LineMapper<T> lineMapper) {
		this.lineMapper = lineMapper;
	}

	@Override
	public T read() {
		if (noInput) {
			return null;
		}
		String line = readLine();
		if (line == null) {
			return null;
		} else {
			try {
				return lineMapper.mapLine(line);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException();
			}
		}
	}

	private boolean isOpen() {
		return reader != null;
	}

	private String readLine() {
		if (!isOpen()) {
			doOpen();
		}
		String line = null;
		try {
			line = this.reader.readLine();
			if (line == null) {
				if (!isOpen()) {
					doClose();
				}
				return null;
			}

			while (isComment(line)) {
				line = reader.readLine();
				if (line == null) {
					return null;
				}
			}

			if (lineCount == 0) {
				lineCount++;
				if (isSkipHeader()) {
					return readLine();
				}
			}

		} catch (IOException e) {
			noInput = true;
			throw new RuntimeException("Unable to read from resource: [" + resource + "], e, line, lineCount");
		}

		return line;
	}

	protected void doOpen() {
		noInput = true;
		if (!resource.exists()) {
			throw new IllegalStateException("Please provide input file");
		}

		try {
			reader = new BufferedReader(new FileReader(resource));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		noInput = false;
	}

	private boolean isComment(String line) {
		for (String prefix : comments) {
			if (line.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

	protected void doClose() throws IOException {
		if (reader != null) {
			reader.close();
		}
	}

	public void setResource(File resource) {
		this.resource = resource;
	}

	public boolean isSkipHeader() {
		return skipHeader;
	}

	public void setSkipHeader(boolean skipHeader) {
		this.skipHeader = skipHeader;
	}

}
