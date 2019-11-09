package com.hoolah.code.converter;

import java.io.File;

import com.hoolah.code.dto.Criteria;
import com.hoolah.code.util.DateUtils;

public class InputConverter implements ArgumentConverter<String[], Criteria> {

	@Override
	public Criteria convertToDTO(String[] inputs) {
		Criteria request = new Criteria();
		request.setFile(new File(inputs[0]));
		request.setDelimiter(inputs[1]);
		request.setStartDate(DateUtils.convertDate(inputs[2]));
		request.setEndDate(DateUtils.convertDate(inputs[3]));
		request.setMerchant(inputs[4]);

		return request;
	}

}
