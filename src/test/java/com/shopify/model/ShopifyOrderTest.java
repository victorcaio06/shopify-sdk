package com.shopify.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

public class ShopifyOrderTest {

	private static final String SOME_USER_ID = "123123";
	private static final int SOME_TOTAL_WEIGHT = 123;
	private static final BigDecimal SOME_TOTAL_TAX = new BigDecimal(42.03);
	private static final BigDecimal SOME_TOTAL_PRICE = new BigDecimal(12.00);
	private static final BigDecimal SOME_TOTA_LINE_ITEMS_PRICE = new BigDecimal(41.00);
	private static final BigDecimal SOME_TOTAL_DISCOUNTS = new BigDecimal(42.00);
	private static final String SOME_TOKEN = "token";
	private static final List<ShopifyTaxLine> SOME_TAX_LINES = Collections.emptyList();
	private static final String SOME_TAGS = "some Tags";
	private static final String HTTP_REFERRED_SITE = "http://referred-site";
	private static final String SOME_PROCESSING_METHOD = "SomeProcessingMethod";
	private static final String SOME_STATUS = "OPEN";
	private static final String SOMEORDER_NUMBER = "someorderNumber";
	private static final List<ShopifyAttribute> SOME_NOTE_ATTRIBUTES = Collections.emptyList();
	private static final String SOME_NOTES = "SomeNOtes";
	private static final String SOME_ORDER_NAME = "some order name";
	private static final String SOME_LOCATION_ID = "123";
	private static final List<ShopifyLineItem> SOME_LINE_ITEMS = Collections.emptyList();
	private static final String SOME_LANDING_SITE = "some landing site";
	private static final String SOME_ID = "someId";
	private static final String SOME_FULFILLMENT_STATUS = "Fulfilled";
	private static final List<ShopifyFulfillment> SOME_FULFILLMENTS = Collections.emptyList();
	private static final String SOME_FINANCIAL_STATUS = "some financail status";
	private static final String SOME_EMAIL = "rkazokas@channelape.com";
	private static final Currency SOME_CURRENCY = Currency.getInstance("USD");
	private static final DateTime SOME_DATE = new DateTime();
	private static final String SOME_CART_TOKEN = "someCartToken";
	private static final String SOME_CANCELLED_REASON = "SomeCancelledReason";
	private static final ShopifyAddress SOME_BILLING_ADDRESS = new ShopifyAddress();
	private static final String SOME_BROWSER_IP = "SomeBrowserIP";

	@Test
	public void givenSomeValuesWhenSettingOrderValuesWhenCreatingShopifyOrderThenExpectCorrectValues() throws Exception {
		final ShopifyOrder shopifyOrder = buildShopifyOrder();
	
		assertShopifyOrderValues(shopifyOrder);
	}
	
	private ShopifyOrder buildShopifyOrder() {
		final ShopifyOrder shopifyOrder = new ShopifyOrder();
		shopifyOrder.setBrowserIp(SOME_BROWSER_IP);
		shopifyOrder.setBillingAddress(SOME_BILLING_ADDRESS);
		shopifyOrder.setBuyerAcceptsMarketing(true);
		shopifyOrder.setCancelledAt(SOME_DATE);
		// ... (continue com os demais setters)
		return shopifyOrder;
	}
	
	private void assertShopifyOrderValues(ShopifyOrder shopifyOrder) {
		assertEquals(SOME_BROWSER_IP, shopifyOrder.getBrowserIp());
		assertEquals(SOME_BILLING_ADDRESS, shopifyOrder.getBillingAddress());
		assertEquals(true, shopifyOrder.isBuyerAcceptsMarketing());
		assertEquals(SOME_DATE, shopifyOrder.getCancelledAt());
		// ... (continue com os demais asserts)
	}
	

}
