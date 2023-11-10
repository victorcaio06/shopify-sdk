package com.shopify;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.mappers.ShopifySdkObjectMapper;
import com.shopify.model.Image;
import com.shopify.model.Metafield;
import com.shopify.model.MetafieldType;
import com.shopify.model.Shop;
import com.shopify.model.ShopifyAddress;
import com.shopify.model.ShopifyAttribute;
import com.shopify.model.ShopifyCustomCollection;
import com.shopify.model.ShopifyCustomer;
import com.shopify.model.ShopifyCustomerUpdateRequest;
import com.shopify.model.ShopifyFulfillment;
import com.shopify.model.ShopifyFulfillmentCreationRequest;
import com.shopify.model.ShopifyFulfillmentUpdateRequest;
import com.shopify.model.ShopifyGetCustomersRequest;
import com.shopify.model.ShopifyGiftCard;
import com.shopify.model.ShopifyGiftCardCreationRequest;
import com.shopify.model.ShopifyInventoryLevel;
import com.shopify.model.ShopifyLineItem;
import com.shopify.model.ShopifyLocation;
import com.shopify.model.ShopifyOrder;
import com.shopify.model.ShopifyOrderCreationRequest;
import com.shopify.model.ShopifyOrderRisk;
import com.shopify.model.ShopifyOrderShippingAddressUpdateRequest;
import com.shopify.model.ShopifyPage;
import com.shopify.model.ShopifyProduct;
import com.shopify.model.ShopifyProductUpdateRequest;
import com.shopify.model.ShopifyProducts;
import com.shopify.model.ShopifyRecurringApplicationCharge;
import com.shopify.model.ShopifyRecurringApplicationChargeCreationRequest;
import com.shopify.model.ShopifyRefund;
import com.shopify.model.ShopifyRefundCreationRequest;
import com.shopify.model.ShopifyRefundLineItem;
import com.shopify.model.ShopifyRefundRoot;
import com.shopify.model.ShopifyRefundShippingDetails;
import com.shopify.model.ShopifyShippingLine;
import com.shopify.model.ShopifyTaxLine;
import com.shopify.model.ShopifyTransaction;
import com.shopify.model.ShopifyVariant;
import com.shopify.model.ShopifyVariantMetafieldCreationRequest;
import com.shopify.model.ShopifyVariantRequest;
import com.shopify.model.ShopifyVariantUpdateRequest;

public class ShopifySdkDriver {

	private static final String HEADER_DELIMITER = "--------------";
	private static final String TEST_DELIMITER = "===============================================";
	private static final char SPACE = ' ';
	private static final String SHOP_SUBDOMAIN = System.getenv("SHOP_SUBDOMAIN");
	private static final String ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");

	private ShopifySdk shopifySdk;

	@Before
	public void setUp() {
		shopifySdk = ShopifySdk.newBuilder().withSubdomain(SHOP_SUBDOMAIN).withAccessToken(ACCESS_TOKEN)
				.withMaximumRequestRetryTimeout(5, TimeUnit.SECONDS)
				.withMaximumRequestRetryRandomDelay(5, TimeUnit.SECONDS).withApiVersion("2020-07").build();
	}

	@Test
	public void givenSomeShopifyVariantMetafieldCreationRequestWhenCreatingMetafieldThenReturnMetafield() {
		final ShopifyVariantMetafieldCreationRequest shopifyVariantMetafieldCreationRequest = ShopifyVariantMetafieldCreationRequest
				.newBuilder().withVariantId("31905723148").withNamespace("channelape").withKey("test_creation")
				.withValue("updated").withValueType(MetafieldType.SINGLE_LINE_TEXT).build();
		final Metafield metafield = shopifySdk.createVariantMetafield(shopifyVariantMetafieldCreationRequest);

		System.out.println("---------- Metafield -------------");
		System.out.println("ID " + metafield.getId());
	}

	@Test
	public void givenSomeShopifyVariantIdWhenRetrievingMetafieldsThenReturnMetafields() {
		final List<Metafield> actualMetafields = shopifySdk.getVariantMetafields("31905723148");

		assertFalse(actualMetafields.isEmpty());
	}

	@Test
	public void givenSomeOrderWithComplexPropertiesThenExpectGetToWork() {
		final ShopifyOrder actualOrder = shopifySdk.getOrder("5350004064317");

		assertEquals("5350004064317", actualOrder.getId());
		assertEquals("123", actualOrder.getLineItems().get(0).getProperties().get(0).getName());
		assertEquals("123", actualOrder.getLineItems().get(0).getProperties().get(0).getValue());
	}

	@Test
public void givenValidOrderIdWhenRetrievingOrderThenReturnShopifyOrder() {
    final String orderId = "661078999099";
    final ShopifyOrder actualShopifyOrder = shopifySdk.getOrder(orderId);

    assertOrderDetails(actualShopifyOrder);
    assertShippingAddress(actualShopifyOrder.getShippingAddress());
    assertBillingAddress(actualShopifyOrder.getBillingAddress());
    assertRefunds(actualShopifyOrder.getRefunds());
    assertTaxLines(actualShopifyOrder.getTaxLines());
    assertLineItemTaxLines(actualShopifyOrder.getLineItems());
}

private void assertOrderDetails(ShopifyOrder actualShopifyOrder) {
    assertEquals("661078999099", actualShopifyOrder.getId());
    assertEquals("ryan.kazokas@gmail.com", actualShopifyOrder.getEmail());
    // ... (restante dos asserts de detalhes da ordem)
}

private void assertShippingAddress(ShopifyAddress shippingAddress) {
    assertEquals("224 Wyoming Avenue", shippingAddress.getAddress1());
    assertEquals("Suite 100", shippingAddress.getAddress2());
    // ... (restante dos asserts do endereço de envio)
}

private void assertBillingAddress(ShopifyAddress billingAddress) {
    assertEquals("address", billingAddress.getAddress1());
    // ... (restante dos asserts do endereço de faturamento)
}

private void assertRefunds(List<ShopifyRefund> refunds) {
    assertEquals(2, refunds.size());
    // ... (restante dos asserts para cada reembolso)
}

private void assertTaxLines(List<ShopifyTaxLine> taxLines) {
    assertEquals(1, taxLines.size());
    // ... (restante dos asserts para cada linha de imposto)
}

private void assertLineItemTaxLines(List<ShopifyLineItem> lineItems) {
    for (ShopifyLineItem lineItem : lineItems) {
        assertEquals(2, lineItem.getTaxLines().size());
        // ... (restante dos asserts para cada linha de imposto de item de linha)
    }
}


	@Test
	public void givenValidOrderIdWithRefundTransactionsAndAdjustmentAndNoRefundLineItemsWhenRetrievingOrderThenReturnShopifyOrder() {
		final String orderId = "2934166880317";

		final ShopifyOrder actualShopifyOrder = shopifySdk.getOrder(orderId);

		assertEquals("humding-6593", actualShopifyOrder.getName());
		assertEquals(1, actualShopifyOrder.getRefunds().size());

		assertEquals("702404231229", actualShopifyOrder.getRefunds().get(0).getId());

		assertEquals(0, actualShopifyOrder.getRefunds().get(0).getRefundLineItems().size());

		assertEquals("3714516516925", actualShopifyOrder.getRefunds().get(0).getTransactions().get(0).getId());
		assertEquals("3621807685693", actualShopifyOrder.getRefunds().get(0).getTransactions().get(0).getParentId());
		assertEquals(Currency.getInstance("USD"),
				actualShopifyOrder.getRefunds().get(0).getTransactions().get(0).getCurrency());
		assertEquals("manual", actualShopifyOrder.getRefunds().get(0).getTransactions().get(0).getGateway());
		assertEquals("refund", actualShopifyOrder.getRefunds().get(0).getTransactions().get(0).getKind());
		assertTrue(BigDecimal.valueOf(25.00)
				.compareTo(actualShopifyOrder.getRefunds().get(0).getTransactions().get(0).getAmount()) == 0);

		assertEquals("130293006397", actualShopifyOrder.getRefunds().get(0).getAdjustments().get(0).getId());
		assertEquals("702404231229", actualShopifyOrder.getRefunds().get(0).getAdjustments().get(0).getRefundId());
		assertEquals("refund_discrepancy", actualShopifyOrder.getRefunds().get(0).getAdjustments().get(0).getKind());
		assertEquals("Refund discrepancy", actualShopifyOrder.getRefunds().get(0).getAdjustments().get(0).getReason());
		assertTrue(BigDecimal.valueOf(-25.00)
				.compareTo(actualShopifyOrder.getRefunds().get(0).getAdjustments().get(0).getAmount()) == 0);
		assertTrue(BigDecimal.valueOf(0.00)
				.compareTo(actualShopifyOrder.getRefunds().get(0).getAdjustments().get(0).getTaxAmount()) == 0);

	}

	@Test
	public void givenValidOrderIdWhenCancelingOrderThenCancelShopifyOrder() {
		final String orderId = "5388977100";
		final String reason = null;

		final ShopifyOrder actualShopifyOrder = shopifySdk.cancelOrder(orderId, reason);

		assertNotNull(actualShopifyOrder.getCancelledAt());
	}

	@Test
	public void givenValidOrderIdWhenRetrievingOrderRisksThenReturnShopifyOrderRisks() {
		final String orderId = "5982557452";

		final List<ShopifyOrderRisk> actualOrderRisks = shopifySdk.getOrderRisks(orderId);

		assertFalse(actualOrderRisks.isEmpty());
	}

	@Test
	public void givenShopifyFulfillmentCreationRequestWhenFulfillingShopifyOrderThenReturnShopifyFulfillment() {
		final ShopifyLineItem shopifyLineItem = new ShopifyLineItem();
		shopifyLineItem.setId("1575362756667");
		shopifyLineItem.setQuantity(1);
		final List<ShopifyLineItem> lineItems = Arrays.asList(shopifyLineItem);
		final ShopifyFulfillmentCreationRequest shopifyFulfillmentCreationRequest = ShopifyFulfillmentCreationRequest
				.newBuilder().withOrderId("649449373755").withTrackingCompany("USPS").withTrackingNumber("1338")
				.withNotifyCustomer(true).withLineItems(lineItems).withLocationId("18407653435")
				.withTrackingUrls(Arrays.asList("https://ups.com/1234", "https://ups.com/432423")).build();

		final ShopifyFulfillment shopifyFulfillment = shopifySdk.createFulfillment(shopifyFulfillmentCreationRequest);

		assertNotNull(shopifyFulfillment.getId());
		assertEquals("649449373755", shopifyFulfillment.getOrderId());
		assertEquals("USPS", shopifyFulfillment.getTrackingCompany());
		assertEquals("1338", shopifyFulfillment.getTrackingNumber());
		assertEquals("18407653435", shopifyFulfillment.getLocationId());
		assertTrue(Arrays.asList("https://ups.com/1234", "https://ups.com/432423")
				.containsAll(shopifyFulfillment.getTrackingUrls()));
		System.out.println("Created Shopify Fulfillment ID " + shopifyFulfillment.getId());
	}

	@Test
	public void givenShopifyFulfillmentUpdateRequestWhenFulfillingShopifyOrderThenReturnShopifyFulfillment() {

		final ShopifyOrder shopifyOrder = shopifySdk.getOrder("649449373755");

		final ShopifyFulfillment currentShopifyFulfillment = shopifyOrder.getFulfillments().stream()
				.filter(f -> "634179616827".equals(f.getId())).findFirst().get();

		final ShopifyFulfillmentUpdateRequest shopifyFulfillmentUpdateRequest = ShopifyFulfillmentUpdateRequest
				.newBuilder().withCurrentShopifyFulfillment(currentShopifyFulfillment).withTrackingCompany("FedEx")
				.withTrackingNumber("1339").withNotifyCustomer(true)
				.withLineItems(currentShopifyFulfillment.getLineItems()).withLocationId("18407653435")
				.withTrackingUrls(Arrays.asList("123.com")).build();

		final ShopifyFulfillment shopifyFulfillment = shopifySdk.updateFulfillment(shopifyFulfillmentUpdateRequest);

		assertNotNull(shopifyFulfillment.getId());
		assertEquals("FedEx", shopifyFulfillment.getTrackingCompany());
		assertEquals("649449373755", shopifyFulfillment.getOrderId());
		assertEquals("1339", shopifyFulfillment.getTrackingNumber());
		assertEquals("18407653435", shopifyFulfillment.getLocationId());
		assertTrue(Arrays.asList("http://123.com").containsAll(shopifyFulfillment.getTrackingUrls()));
		System.out.println("Updated Shopify Fulfillment ID " + shopifyFulfillment.getId());
	}

	@Test
	public void givenShopifyOrderIdWhenClosingShopifyOrderThenReturnShopifyOrder() {
		final String orderId = "692381450299";

		final ShopifyOrder actualShopifyOrder = shopifySdk.closeOrder(orderId);

		assertEquals(orderId, actualShopifyOrder.getId());
		assertNotNull(actualShopifyOrder.getClosedAt());
	}

	@Test
	public void givenSomePageWhenQueryingOrdersThenReturnShopifyOrders() {
		final ShopifyPage<ShopifyOrder> actualOrdersFirstPage = shopifySdk.getOrders(250);
		assertEquals(250, actualOrdersFirstPage.size());
		assertNotNull(actualOrdersFirstPage.getNextPageInfo());
		assertNull(actualOrdersFirstPage.getPreviousPageInfo());

		String nextPageInfo = actualOrdersFirstPage.getNextPageInfo();
		while (nextPageInfo != null) {
			System.out.println("Getting next orders with page info: " + nextPageInfo);
			final ShopifyPage<ShopifyOrder> orders = shopifySdk.getOrders(nextPageInfo, 250);
			nextPageInfo = orders.getNextPageInfo();

		}

	}

	@Test
	public void givenSomeQueryWhenGettingCustomersFromMultiplePagesThenRetrieveCustomers() {
		final ShopifyGetCustomersRequest shopifyGetCustomersRequest = ShopifyGetCustomersRequest.newBuilder()
				.withCreatedAtMin(DateTime.now(DateTimeZone.UTC).minusYears(4)).withLimit(10).build();
		final ShopifyPage<ShopifyCustomer> actualCustomersPage = shopifySdk.getCustomers(shopifyGetCustomersRequest);
		assertEquals(10, actualCustomersPage.size());
		assertNotNull(actualCustomersPage.getNextPageInfo());
		assertNull(actualCustomersPage.getPreviousPageInfo());

		String nextPageInfo = actualCustomersPage.getNextPageInfo();
		while (nextPageInfo != null) {
			System.out.println("Getting next customers with page info: " + nextPageInfo);
			final ShopifyGetCustomersRequest paginatedGetRequest = ShopifyGetCustomersRequest.newBuilder()
					.withPageInfo(nextPageInfo).withLimit(10).build();
			final ShopifyPage<ShopifyCustomer> paginatedCustomers = shopifySdk.getCustomers(paginatedGetRequest);
			nextPageInfo = paginatedCustomers.getNextPageInfo();

		}

	}

	@Test
	public void givenSomePageAndMinimumDateWhenQueryingOrdersThenReturnShopifyOrders() {
		final ShopifyPage<ShopifyOrder> actualOrdersFirstPage = shopifySdk.getOrders(new DateTime().minusYears(4), 250);
		assertEquals(250, actualOrdersFirstPage.size());

		String nextPageInfo = actualOrdersFirstPage.getNextPageInfo();
		while (nextPageInfo != null) {
			System.out.println("Getting next orders with page info: " + nextPageInfo);
			final ShopifyPage<ShopifyOrder> orders = shopifySdk.getOrders(nextPageInfo, 250);
			nextPageInfo = orders.getNextPageInfo();

		}

	}

	@Test
	public void givenSomeShopifyRecurringApplicationChargeCreationRequestWhenCreatingRecurringChargeThenReturnShopifyRecurringApplicationCharge() {
		final String expectedName = "ChannelApe";
		final String expectedTerms = "Price varies by integrations installed.";
		final BigDecimal expectedPrice = new BigDecimal("49.99");
		final BigDecimal expectedCappedAmount = new BigDecimal("149.99");
		final String expectedReturnUrl = "https://dev.channelape.com/channel/shopify/integrate";
		final int expectedTrialDays = 14;
		final ShopifyRecurringApplicationChargeCreationRequest shopifyRecurringApplicationChargeCreationRequest = ShopifyRecurringApplicationChargeCreationRequest
				.newBuilder().withName(expectedName).withTerms(expectedTerms).withPrice(expectedPrice)
				.withCappedAmount(expectedCappedAmount).withReturnUrl(expectedReturnUrl)
				.withTrialDays(expectedTrialDays).withTest(true).build();

		final ShopifyRecurringApplicationCharge actualShopifyRecurringApplicationCharge = shopifySdk
				.createRecurringApplicationCharge(shopifyRecurringApplicationChargeCreationRequest);

		assertEquals(expectedName, actualShopifyRecurringApplicationCharge.getName());
		assertEquals(expectedPrice, actualShopifyRecurringApplicationCharge.getPrice());
		assertEquals(expectedCappedAmount, actualShopifyRecurringApplicationCharge.getCappedAmount());
		assertEquals(expectedReturnUrl, actualShopifyRecurringApplicationCharge.getReturnUrl());
		assertEquals(expectedTrialDays, actualShopifyRecurringApplicationCharge.getTrialDays());
		final String expectedStatus = "pending";
		assertEquals(expectedStatus, actualShopifyRecurringApplicationCharge.getStatus());
		assertTrue(actualShopifyRecurringApplicationCharge.isTest());

		System.out.println("id " + actualShopifyRecurringApplicationCharge.getId());
		System.out.println("status " + actualShopifyRecurringApplicationCharge.getStatus());
		System.out.println("confirmation_url " + actualShopifyRecurringApplicationCharge.getConfirmationUrl());
	}

	@Test
	public void givenSomeChargeIdWhenRetrievingRecurringChargeThenReturnShopifyRecurringApplicationCharge() {
		final String chargeId = "2590029";
		final ShopifyRecurringApplicationCharge actualShopifyRecurringApplicationCharge = shopifySdk
				.getRecurringApplicationCharge(chargeId);
		assertEquals(chargeId, actualShopifyRecurringApplicationCharge.getId());
		System.out.println("id " + actualShopifyRecurringApplicationCharge.getId());
		System.out.println("status " + actualShopifyRecurringApplicationCharge.getStatus());
	}

	@Test
	public void givenSomeChargeIdWhenActivatingRecurringChargeThenReturnShopifyRecurringApplicationCharge() {
		final String chargeId = "2590029";
		final ShopifyRecurringApplicationCharge actualShopifyRecurringApplicationCharge = shopifySdk
				.activateRecurringApplicationCharge(chargeId);
		assertEquals(chargeId, actualShopifyRecurringApplicationCharge.getId());
		System.out.println("id " + actualShopifyRecurringApplicationCharge.getId());
		System.out.println("status " + actualShopifyRecurringApplicationCharge.getStatus());
	}

	@Test
	public void whenDeletingAllProductsThenEnsureStoreIsEmpty() {
		final ShopifyProducts shopifyProducts = shopifySdk.getProducts();
		shopifyProducts.values().parallelStream().map(ShopifyProduct::getId).forEach(shopifySdk::deleteProduct);

		final int productCount = shopifySdk.getProductCount();
		outputHeader("Product Count");
		output(productCount);
		assertTrue(productCount == 0);
	}

	@Test
	public void whenRetrievingProductCountThenReturnCorrectInteger() {
		final int productCount = shopifySdk.getProductCount();
		outputHeader("Product Count");
		output(productCount);
		assertTrue(productCount > 0);
	}

	@Test
	public void whenRetrievingTransactionsForAnOrderThenExpectNotNull() {
		final List<ShopifyTransaction> transactions = shopifySdk.getOrderTransactions("750362787951");
		assertNotNull(transactions);
		assertEquals(1, transactions.size());
		assertEquals(true, transactions.get(0).getReceipt().isApplePay());
	}

	@Test
	public void givenValidShopifyProductUpdateRequestWhenUpdatingProductThenReturnUpdatedShopifyProduct() {
		final String productId = "1696786939963";
		final ShopifyProduct currentShopifyProduct = shopifySdk.getProduct(productId);

		final String expectedTitle = "Pink Shirt - Wow";
		final String expectedBodyHtml = "This such a stylish shirt, Update from client Driver.";
		final List<String> imageSources = Arrays.asList(
				"https://ae01.alicdn.com/kf/HTB1gE7JbSFRMKJjy0Fhq6x.xpXaw/2018-Summer-Mens-Dress-Shirts-Cotton-Solid-Casual-Shirt-Men-Slim-Fit-Plus-Size-Long-sleeve.jpg_640x640.jpg",
				"https://5.imimg.com/data5/XS/DT/MY-3747740/mens-shirts-500x500.jpg");
		final List<ShopifyVariantRequest> variantRequests = currentShopifyProduct.getVariants().stream()
				.map(currentShopifyVariant -> {
					final String imageSource = imageSources.get(currentShopifyVariant.getPosition() - 1);
					return ShopifyVariantUpdateRequest.newBuilder().withCurrentShopifyVariant(currentShopifyVariant)
							.withPrice(new BigDecimal(new Random().nextDouble() * 100)).withSameCompareAtPrice()
							.withSameSku().withSameBarcode().withSameWeight().withAvailable(4L).withSameFirstOption()
							.withSameSecondOption().withSameThirdOption().withImageSource(imageSource)
							.withSameInventoryManagement().withSameInventoryPolicy().withSameFulfillmentService()
							.withSameRequiresShipping().withSameTaxable().withSameInventoryItemId().build();
				}).collect(Collectors.toList());

		final ShopifyProductUpdateRequest shopifyProductUpdateRequest = ShopifyProductUpdateRequest.newBuilder()
				.withCurrentShopifyProduct(currentShopifyProduct).withTitle(expectedTitle)
				.withSameMetafieldsGlobalTitleTag().withSameProductType().withBodyHtml(expectedBodyHtml)
				.withSameMetafieldsGlobalDescriptionTag().withSameVendor().withSameTags().withSameOptions()
				.withImageSources(imageSources).withVariantRequests(variantRequests).withPublished(true).build();

		final ShopifyProduct updatedShopifyProduct = shopifySdk.updateProduct(shopifyProductUpdateRequest);

		assertEquals(expectedTitle, updatedShopifyProduct.getTitle());
		assertEquals(expectedBodyHtml, updatedShopifyProduct.getBodyHtml());
		assertEquals(2, updatedShopifyProduct.getImages().size());
		final ShopifyVariant firstShopifyProductVariant = variantRequests.get(0).getRequest();
		final ShopifyVariant actualFirstUpdateShopifyProductVariant = updatedShopifyProduct.getVariants().get(0);
		assertShopifyProductVariant(firstShopifyProductVariant, actualFirstUpdateShopifyProductVariant);

		final ShopifyVariant secondShopifyProductVariant = variantRequests.get(1).getRequest();
		final ShopifyVariant actualSecondUpdateShopifyProductVariant = updatedShopifyProduct.getVariants().get(1);
		assertShopifyProductVariant(secondShopifyProductVariant, actualSecondUpdateShopifyProductVariant);

	}

	private void assertShopifyProductVariant(final ShopifyVariant firstShopifyProductVariant,
			final ShopifyVariant actualFirstUpdateShopifyProductVariant) {
		assertEquals(firstShopifyProductVariant.getBarcode(), actualFirstUpdateShopifyProductVariant.getBarcode());
		assertEquals(firstShopifyProductVariant.getCompareAtPrice(),
				actualFirstUpdateShopifyProductVariant.getCompareAtPrice());
		assertEquals(firstShopifyProductVariant.getFulfillmentService(),
				actualFirstUpdateShopifyProductVariant.getFulfillmentService());
		assertEquals(firstShopifyProductVariant.getGrams(), actualFirstUpdateShopifyProductVariant.getGrams());
		assertNotNull(actualFirstUpdateShopifyProductVariant.getImageId());
		assertEquals(firstShopifyProductVariant.getId(), actualFirstUpdateShopifyProductVariant.getId());
		assertEquals(firstShopifyProductVariant.getInventoryItemId(),
				actualFirstUpdateShopifyProductVariant.getInventoryItemId());
		assertEquals(firstShopifyProductVariant.getInventoryManagement(),
				actualFirstUpdateShopifyProductVariant.getInventoryManagement());
		assertEquals(firstShopifyProductVariant.getInventoryPolicy(),
				actualFirstUpdateShopifyProductVariant.getInventoryPolicy());
		assertEquals(firstShopifyProductVariant.getInventoryQuantity(),
				actualFirstUpdateShopifyProductVariant.getInventoryQuantity());
		assertEquals(firstShopifyProductVariant.getOption1(), actualFirstUpdateShopifyProductVariant.getOption1());
		assertEquals(firstShopifyProductVariant.getOption2(), actualFirstUpdateShopifyProductVariant.getOption2());
		assertEquals(firstShopifyProductVariant.getOption3(), actualFirstUpdateShopifyProductVariant.getOption3());
		assertEquals(firstShopifyProductVariant.getPosition(), actualFirstUpdateShopifyProductVariant.getPosition());
		assertEquals(firstShopifyProductVariant.getProductId(), actualFirstUpdateShopifyProductVariant.getProductId());
		assertEquals(firstShopifyProductVariant.getSku(), actualFirstUpdateShopifyProductVariant.getSku());
		assertEquals(firstShopifyProductVariant.getTitle(), actualFirstUpdateShopifyProductVariant.getTitle());
	}

	@Test
	public void whenRetrievingProductsThenReturnExpectedProductsAndVariantsAndImages() {
		final ShopifyProducts products = shopifySdk.getProducts();
		outputHeader("Products");

		output("Number of Products: " + products.size());
		products.values().forEach(product -> {
			outputHeader("Product", 1);

			output("ID " + product.getId(), 1);
			output("Body HTML " + product.getBodyHtml(), 1);
			output("Tags " + product.getTags(), 1);
			output("Vendor " + product.getVendor(), 1);
			output("Product Type " + product.getProductType(), 1);

			final List<ShopifyVariant> variants = product.getVariants();
			outputHeader("Variants", 1);
			output("Number of Variants: " + variants.size(), 1);
			variants.forEach(variant -> {
				outputHeader("Variant", 2);

				output("Product ID " + variant.getProductId(), 2);
				output("ID " + variant.getId(), 2);
				output("SKU " + variant.getSku(), 2);
				output("Barcode " + variant.getBarcode(), 2);
				output("Grams " + variant.getGrams(), 2);
				output("Price " + variant.getPrice().toString(), 2);
				output("Inventory Mangement " + variant.getInventoryManagement(), 2);
				output("Inventory Policy " + variant.getInventoryPolicy(), 2);
			});

			outputHeader("Current Image", 1);
			output(product.getImage(), 1);

			final List<Image> images = product.getImages();
			outputHeader("Images", 1);
			output("Number of Images " + images.size(), 1);
			images.forEach(image -> {
				output(image, 2);
			});
		});
	}

	@Test
	public void whenRetrievingShopThenReturnExpectedIdAndName() {
		final Shop shop = shopifySdk.getShop().getShop();
		outputHeader("Shop");
		output("ID " + shop.getId());
		output("Name " + shop.getName());
	}

	@Test
	public void whenRevokingInvalidOAuthTokenThenReturnFalse() {
		shopifySdk = ShopifySdk.newBuilder().withSubdomain(SHOP_SUBDOMAIN).withAccessToken("ASDF").build();
		outputHeader("Revoke OAuth Token");
		assertFalse(shopifySdk.revokeOAuthToken());
	}

	@Test
	public void whenRevokingValidOAuthTokenThenReturnTrue() {
		outputHeader("Revoke OAuth Token");
		assertTrue(shopifySdk.revokeOAuthToken());
	}

	@Test
	public void givenValidRequestWhenRetrievingLocationsThenReturnShopifyOrderRisks() {

		final List<ShopifyLocation> actualLocations = shopifySdk.getLocations();

		assertFalse(actualLocations.isEmpty());
	}

	@Test
	public void givenSomeRequestToCalculateRefundWhenCalculatingRefundThenCalculateRefund() throws Exception {
		final ShopifyRefundRoot shopifyRefundRoot = new ShopifyRefundRoot();
		final ShopifyRefund shopifyRefund = new ShopifyRefund();
		shopifyRefund.setOrderId("649446195259");
		final ShopifyRefundShippingDetails shopifyRefundShippingDetails = new ShopifyRefundShippingDetails();
		shopifyRefundShippingDetails.setFullRefund(true);

		final ShopifyRefundLineItem shopifyRefundLineItem = new ShopifyRefundLineItem();
		shopifyRefundLineItem.setLineItemId("1575356334139");
		shopifyRefundLineItem.setQuantity(3);
		shopifyRefundLineItem.setRestockType("no_restock");
		shopifyRefund.setRefundLineItems(Arrays.asList(shopifyRefundLineItem));
		shopifyRefund.setShipping(shopifyRefundShippingDetails);

		shopifyRefundRoot.setRefund(shopifyRefund);

		final ShopifyRefundCreationRequest shopifyCreationRequest = new ShopifyRefundCreationRequest();
		shopifyCreationRequest.setRequest(shopifyRefund);

		final ShopifyRefund actualShopifyRefund = shopifySdk.refund(shopifyCreationRequest);
		assertNotNull(actualShopifyRefund);
	}

	@Test
	public void givenSomeGiftCardCreationRequestWhenCreatingGiftCardThenCreateGiftCard() throws Exception {
		final ShopifyGiftCardCreationRequest giftCard = ShopifyGiftCardCreationRequest.newBuilder()
				.withInitialValue(new BigDecimal(25.00)).withCode("ABCJFKLDSJZZ4CAPE").withCurrency("USD").build();

		final ObjectMapper mapper = ShopifySdkObjectMapper.buildMapper();
		final String dtoAsString = mapper.writeValueAsString(giftCard);
		System.out.println(dtoAsString);
		final ShopifyGiftCard shopifyGiftCard = shopifySdk.createGiftCard(giftCard);
		assertNotNull(shopifyGiftCard);
	}

	@Test
	public void givenValidRequestWhenUpdatingInventoryLevelsThenReturnShopifyInventoryLevel() {

		final ShopifyInventoryLevel actualInventoryLevel = shopifySdk.updateInventoryLevel("362476234", "8782373482",
				666);

		assertNotNull(actualInventoryLevel);
	}

	@Test
	public void givenSomeUpdatedAtMinWhenRetrievingUpdatedOrdersThenExpectUpdatedOrders()
			throws JsonProcessingException {
		final ShopifyPage<ShopifyOrder> actualShopifyOrders = shopifySdk.getUpdatedOrdersCreatedBefore(
				DateTime.now(DateTimeZone.UTC).minusHours(24), DateTime.now(DateTimeZone.UTC),
				DateTime.now(DateTimeZone.UTC), 250);
		assertNotNull(actualShopifyOrders);
		assertTrue(actualShopifyOrders.size() > 0);
	}

	@Test
	public void givenSomeOrderWhenCreatingOrderThenCreateOrder() throws JsonProcessingException {
		final ShopifyOrderCreationRequest orderRequest = buildShopifyOrderRequest();
	
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		final String dtoAsString = mapper.writeValueAsString(orderRequest);
		System.out.println(dtoAsString);
	
		final ShopifyOrder actualShopifyOrder = shopifySdk.createOrder(orderRequest);
		assertNotNull(actualShopifyOrder);
	}
	
	private ShopifyOrderCreationRequest buildShopifyOrderRequest() {
		final ShopifyLineItem shopifyLineItem = createShopifyLineItem();
		final ShopifyCustomer shopifyCustomer = createShopifyCustomer();
		final ShopifyAddress shopifyAddress = createShopifyAddress();
		final List<ShopifyShippingLine> shopifyShippingLines = createShopifyShippingLines();
		final List<ShopifyAttribute> someNoteAttributes = createShopifyNoteAttributes();
	
		return ShopifyOrderCreationRequest.newBuilder()
				.withProcessedAt(new DateTime())
				.withName(UUID.randomUUID().toString())
				.withCustomer(shopifyCustomer)
				.withLineItems(Collections.singletonList(shopifyLineItem))
				.withShippingAddress(shopifyAddress)
				.withBillingAddress(shopifyAddress)
				.withMetafields(Collections.emptyList())
				.withShippingLines(shopifyShippingLines)
				.withFinancialStatus("pending")
				.withNote("some-note123")
				.withNoteAttributes(someNoteAttributes)
				.build();
	}
	
	private ShopifyLineItem createShopifyLineItem() {
		final ShopifyLineItem shopifyLineItem = new ShopifyLineItem();
		shopifyLineItem.setVariantId("12262219972712");
		shopifyLineItem.setQuantity(44);
		return shopifyLineItem;
	}
	
	private ShopifyCustomer createShopifyCustomer() {
		final ShopifyCustomer shopifyCustomer = new ShopifyCustomer();
		shopifyCustomer.setEmail("rkazokas@channelape.com");
		return shopifyCustomer;
	}
	
	private ShopifyAddress createShopifyAddress() {
		final ShopifyAddress shopifyAddress = new ShopifyAddress();
		shopifyAddress.setAddress1("224 Wyoming Avenue");
		shopifyAddress.setAddress2("Suite 100");
		shopifyAddress.setName("Ryan Kazokas");
		shopifyAddress.setFirstName("Ryan");
		shopifyAddress.setLastname("Kazokas");
		shopifyAddress.setCountry("United States");
		shopifyAddress.setCountryCode("US");
		shopifyAddress.setProvince("PEnnsylvania");
		shopifyAddress.setProvinceCode("PA");
		shopifyAddress.setZip("92387423");
		return shopifyAddress;
	}
	
	private List<ShopifyShippingLine> createShopifyShippingLines() {
		final ShopifyShippingLine shopifyShippingLine = new ShopifyShippingLine();
		shopifyShippingLine.setId("123");
		shopifyShippingLine.setPrice(new BigDecimal(42.11));
		shopifyShippingLine.setSource("some-source");
		shopifyShippingLine.setTitle("some-title");
		shopifyShippingLine.setCode("sc");
		return Collections.singletonList(shopifyShippingLine);
	}
	
	private List<ShopifyAttribute> createShopifyNoteAttributes() {
		final ShopifyAttribute shopifyAttribute1 = new ShopifyAttribute();
		shopifyAttribute1.setName("some-name1");
		shopifyAttribute1.setValue("some-value1");
	
		final ShopifyAttribute shopifyAttribute2 = new ShopifyAttribute();
		shopifyAttribute2.setName("some-name2");
		shopifyAttribute2.setValue("some-value2");
	
		final ShopifyAttribute shopifyAttribute3 = new ShopifyAttribute();
		shopifyAttribute3.setName("some-name3");
		shopifyAttribute3.setValue("some-value3");
	
		return Arrays.asList(shopifyAttribute1, shopifyAttribute2, shopifyAttribute3);
	}
	

	@Test
	public void givenSomeValuesExistWhenRetrievingCustomCollectionsThenRetrieveCustomCollections() {
		final List<ShopifyCustomCollection> retrievedCustomCollections = shopifySdk.getCustomCollections();
		assertEquals(3, retrievedCustomCollections.size());
	}

	@Test
	public void givenSomeValuesWhenUpdatingAnOrderThenExpectValuesToBeUpdatedOnOrder() throws JsonProcessingException {

		final ShopifyOrderShippingAddressUpdateRequest shopifyOrderUpdateRequest = ShopifyOrderShippingAddressUpdateRequest
				.newBuilder().withId("1124214472765").withAddress1("Testing From SDK Driver2").withAddress2("Suite 100")
				.withCity("Scranton").withProvince("Pennsylvania").withProvinceCode("PA").withZip("18503")
				.withCountry("United States").withCountryCode("US").withPhone("9829374293874").withFirstName("Ryan")
				.withLastName("Kazokas").withCompany("ChannelApe").withLatitude(null).withLongitude(null).build();

		final ShopifyOrder updateOrder = shopifySdk.updateOrderShippingAddress(shopifyOrderUpdateRequest);
		assertEquals("Testing From SDK Driver2", updateOrder.getShippingAddress().getAddress1());
	}

	@Test
	public void givenSomeValuesWhenUpdatingACustomerThenExpectValuesToBeUpdatedOnCustomer()
			throws JsonProcessingException {

		final ShopifyCustomerUpdateRequest shopifyOrderUpdateRequest = ShopifyCustomerUpdateRequest.newBuilder()
				.withId("6780238412").withFirstName("RyanTest").withLastName("Kazokas123")
				.withEmail("rkazokas@channelape.com").withPhone("5702392904").build();

		final ShopifyCustomer updatedCustomer = shopifySdk.updateCustomer(shopifyOrderUpdateRequest);
		assertEquals("RyanTest", updatedCustomer.getFirstName());
	}

	@Test
	public void givenSomeErrorOccurrsWhenCreatingFulfillmentThenExpectCorrectErrors() {
		try {
			shopifySdk.createFulfillment(ShopifyFulfillmentCreationRequest.newBuilder().withOrderId("2854620102717")
					.withTrackingCompany("UPS").withTrackingNumber("ABC-123").withNotifyCustomer(false)
					.withLineItems(new LinkedList<>()).withLocationId("5523767400")
					.withTrackingUrls(Arrays.asList("http://google.com/123")).build());
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	@After
	public void after() {
		System.out.println(TEST_DELIMITER);
	}

	private void outputHeader(final String header) {
		outputHeader(header, 0);
	}

	private void outputHeader(final String header, final int indent) {
		output(new StringBuilder().append(HEADER_DELIMITER).append(SPACE).append(header).append(SPACE)
				.append(HEADER_DELIMITER).toString(), indent);
	}

	private void output(final Object text) {
		output(text, 0);
	}

	private void output(final Object text, final int indent) {
		final StringBuilder tabsStringBuilder = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			tabsStringBuilder.append("\t");
		}
		System.out.println(new StringBuilder().append(tabsStringBuilder).append(text).toString());
	}

	private void output(final Image image, final int indent) {
		outputHeader("Image", indent);
		output("Product ID " + image.getProductId(), indent);
		output("ID " + image.getId(), indent);
		output("Position " + image.getPosition(), indent);
		output("Source " + image.getSource(), indent);
		output("Variant IDs " + image.getVariantIds(), indent);
	}
}
