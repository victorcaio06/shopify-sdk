package com.shopify.model;

import java.util.HashMap;
import java.util.Map;

public enum MetafieldType {

	SINGLE_LINE_TEXT("single_line_text_field"),
	MULTI_LINE_TEXT("multi_line_text_field"),
	PAGE_REFERENCE("page_reference"),
	PRODUCT_REFERENCE("product_reference"),
	FILE_REFERENCE("file_reference"),
	NUMBER_INTEGER("number_integer"),
	DECIMAL("number_decimal"),
	DATE("date"),
	DATE_AND_TIME("date_time"),
	URL("url"),
	JSON_STRING("json"),
	BOOLEAN("boolean"),
	WEIGHT("weight"),
	VOLUME("volume"),
	DIMENSION("dimension"),
	RATING("rating");

	static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for %s";
	private static final String STRING_TYPE = "string";
	private static final String INTEGER_TYPE = "integer";
	private final String type;

	private MetafieldType(final String type) {
		this.type = type;
	}

	private static final Map<String, MetafieldType> TYPE_MAP = new HashMap<>();

static {
    TYPE_MAP.put(SINGLE_LINE_TEXT.toString(), MetafieldType.SINGLE_LINE_TEXT);
    TYPE_MAP.put(STRING_TYPE.toLowerCase(), MetafieldType.SINGLE_LINE_TEXT);
    TYPE_MAP.put(NUMBER_INTEGER.toString(), MetafieldType.NUMBER_INTEGER);
    TYPE_MAP.put(INTEGER_TYPE.toLowerCase(), MetafieldType.NUMBER_INTEGER);
    TYPE_MAP.put(MULTI_LINE_TEXT.toString(), MetafieldType.MULTI_LINE_TEXT);
    TYPE_MAP.put(PAGE_REFERENCE.toString(), MetafieldType.PAGE_REFERENCE);
    TYPE_MAP.put(PRODUCT_REFERENCE.toString(), MetafieldType.PRODUCT_REFERENCE);
    TYPE_MAP.put(FILE_REFERENCE.toString(), MetafieldType.FILE_REFERENCE);
    TYPE_MAP.put(DECIMAL.toString(), MetafieldType.DECIMAL);
    TYPE_MAP.put(DATE.toString(), MetafieldType.DATE);
    TYPE_MAP.put(DATE_AND_TIME.toString(), MetafieldType.DATE_AND_TIME);
    TYPE_MAP.put(URL.toString(), MetafieldType.URL);
    TYPE_MAP.put(JSON_STRING.toString(), MetafieldType.JSON_STRING);
    TYPE_MAP.put(BOOLEAN.toString(), MetafieldType.BOOLEAN);
    TYPE_MAP.put(WEIGHT.toString(), MetafieldType.WEIGHT);
    TYPE_MAP.put(VOLUME.toString(), MetafieldType.VOLUME);
    TYPE_MAP.put(DIMENSION.toString(), MetafieldType.DIMENSION);
    TYPE_MAP.put(RATING.toString(), MetafieldType.RATING);
}

public static MetafieldType toEnum(final String type) {
    MetafieldType metafieldType = TYPE_MAP.get(type);
    if (metafieldType != null) {
        return metafieldType;
    }
    throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, type));
}


	@Override
	public String toString() {
		return type;
	}

}
