package com.shopify.exceptions;

import java.io.Serializable;
import com.shopify.enums.Type;

public class ShopifyErrorCode implements Serializable {

	private static final long serialVersionUID = -3870975240510101019L;

	private final Type type;
	private final String message;

	public ShopifyErrorCode(final Type type, final String message) {
		this.type = type;
		this.message = message;
	}

	public Type getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}
}
