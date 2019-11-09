package com.hoolah.code.converter;

public interface ArgumentConverter<X, Y> {
	public Y convertToDTO(X attribute);
}
