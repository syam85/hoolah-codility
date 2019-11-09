package com.hoolah.code.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	private static ThreadLocal<SimpleDateFormat> simpleDateFormat = new ThreadLocal<SimpleDateFormat>() {
		@Override
		public SimpleDateFormat get() {
			return new SimpleDateFormat("MM/dd/yyyy kk:mm:ss");
		}
	};

	private DateUtils() {

	}

	public static Date convertDate(String date) {
		try {
			return simpleDateFormat.get().parse(date);
		} catch (ParseException parseException) {
			throw new RuntimeException("Unparsable date" + date + parseException.getMessage());
		}
	}

	public static boolean isValidDate(String date) {
		try {
			simpleDateFormat.get().parse(date);
		} catch (ParseException parseException) {
			return false;
		}

		return true;
	}

}
