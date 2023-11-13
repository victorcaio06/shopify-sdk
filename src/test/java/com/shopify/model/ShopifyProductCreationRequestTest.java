package com.shopify.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

public class ShopifyProductCreationRequestTest {

	private static final String SOME_SKU = "10004";
	private static final String SOME_BARCODE = "144141141";
	private static final String SOME_TITLE = "Some Title";
	private static final String SOME_METAFIELDS_GLOBAL_TITLE_TAG = "SEO Title";
	private static final String SOME_METAFIELDS_GLOBAL_DESCRIPTION_TAG = "SEO Description";
	private static final long SOME_QUANTITY = 25;
	private static final String SOME_FIRST_OPTION_NAME = "Color";
	private static final String SOME_SECOND_OPTION_NAME = "Flavor";
	private static final String SOME_THIRD_OPTION_NAME = "Size";
	private static final String SOME_PRODUCT_TYPE = "Energy";
	private static final String SOME_BODY_HTML = "Some description";
	private static final String SOME_VENDOR = "Clif";
	private static final Set<String> SOME_TAGS = new HashSet<>(Arrays.asList("EuropaSports"));
	private static final String SOME_SECOND_IMAGE_SOURCE = "image9191919";
	private static final String SOME_FIRST_IMAGE_SOURCE = "image2133";

	@Test
	public void testShopifyProductCreationRequestWithValuesSetAndPublished() {
		// Dados de teste
		final ShopifyProductCreationRequest actualShopifyProductCreationRequest = buildShopifyProductCreationRequestWithValuesSetAndPublished();
	
		// Verificações
		assertShopifyProductCreationRequestWithValuesSetAndPublished(actualShopifyProductCreationRequest);
	
		// Verificações específicas para ShopifyProduct
		assertShopifyProductValuesWithValuesSetAndPublished(actualShopifyProductCreationRequest.getRequest());
	}
	
	private ShopifyProductCreationRequest buildShopifyProductCreationRequestWithValuesSetAndPublished() {
		// Lógica para construir e retornar a instância de ShopifyProductCreationRequest com valores definidos
		// ...
	
		return ShopifyProductCreationRequest.newBuilder()
				.withTitle(SOME_TITLE)
				.withMetafieldsGlobalTitleTag(SOME_METAFIELDS_GLOBAL_TITLE_TAG)
				.withProductType(SOME_PRODUCT_TYPE)
				.withBodyHtml(SOME_BODY_HTML)
				.withMetafieldsGlobalDescriptionTag(SOME_METAFIELDS_GLOBAL_DESCRIPTION_TAG)
				.withVendor(SOME_VENDOR)
				.withTags(SOME_TAGS)
				.withSortedOptionNames(Arrays.asList(SOME_FIRST_OPTION_NAME, SOME_SECOND_OPTION_NAME, SOME_THIRD_OPTION_NAME))
				.withImageSources(Arrays.asList(SOME_FIRST_IMAGE_SOURCE, SOME_SECOND_IMAGE_SOURCE))
				.withVariantCreationRequests(Arrays.asList(
						buildShopifyVariantCreationRequest("Red", "Lemon", "24 ea", SOME_FIRST_IMAGE_SOURCE),
						buildShopifyVariantCreationRequest("Green", "Strawberry", "11-t5", null),
						buildShopifyVariantCreationRequest("Pink", "Watermelon", "40 count", "imageNotFound444")
				))
				.withPublished(true)
				.build();
	}
	
	private ShopifyVariantCreationRequest buildShopifyVariantCreationRequest(String firstOption, String secondOption, String thirdOption, String imageSource) {
		// Lógica para construir e retornar a instância de ShopifyVariantCreationRequest
		// ...
	
		return ShopifyVariantCreationRequest.newBuilder()
				.withPrice(BigDecimal.TEN)
				.withCompareAtPrice(BigDecimal.TEN)
				.withSku(SOME_SKU)
				.withBarcode(SOME_BARCODE)
				.withWeight(BigDecimal.ZERO)
				.withAvailable(SOME_QUANTITY)
				.withFirstOption(firstOption)
				.withSecondOption(secondOption)
				.withThirdOption(thirdOption)
				.withImageSource(imageSource)
				.withDefaultInventoryManagement()
				.withDefaultInventoryPolicy()
				.withDefaultFulfillmentService()
				.withRequiresShippingDefault()
				.withTaxableDefault()
				.build();
	}
	
	private void assertShopifyProductCreationRequestWithValuesSetAndPublished(ShopifyProductCreationRequest creationRequest) {
		// Verificações gerais para ShopifyProductCreationRequest
		assertTrue(creationRequest.hasChanged());
		assertTrue(creationRequest.hasVariantImagePosition(3));
		final int actualVariantImagePosition = creationRequest.getVariantImagePosition(3);
		assertTrue(actualVariantImagePosition == 1 || actualVariantImagePosition == 2);
	}
	
	private void assertShopifyProductValuesWithValuesSetAndPublished(ShopifyProduct actual) {
		// Verificações específicas para ShopifyProduct
		assertEquals(SOME_TITLE, actual.getTitle());
		assertEquals(SOME_METAFIELDS_GLOBAL_TITLE_TAG, actual.getMetafieldsGlobalTitleTag());
		assertEquals(SOME_METAFIELDS_GLOBAL_DESCRIPTION_TAG, actual.getMetafieldsGlobalDescriptionTag());
		assertEquals(SOME_BODY_HTML, actual.getBodyHtml());
		assertEquals(SOME_VENDOR, actual.getVendor());
		assertEquals(SOME_TAGS, actual.getTags());
		assertEquals(SOME_PRODUCT_TYPE, actual.getProductType());
	
		// Verificações para as variantes, opções, imagens, etc.
		// ...
	}
	

	@Test
	public void givenValuesSetAndUnpublishedWhenBuildingShopifyProductCreationRequestThenExpectCorrectValues() {
		final ShopifyVariantCreationRequest firstVariantCreationRequest = buildShopifyVariant("Red", "Lemon", "24 ea", SOME_FIRST_IMAGE_SOURCE);
		final ShopifyVariantCreationRequest secondVariantCreationRequest = buildShopifyVariant("Green", "Strawberry", "11-t5", null);
		final ShopifyVariantCreationRequest thirdVariantCreationRequest = buildShopifyVariant("Pink", "Watermelon", "40 count", "imageNotFound444");
	
		final ShopifyProductCreationRequest actualShopifyProductCreationRequest = ShopifyProductCreationRequest.newBuilder()
				.withTitle(SOME_TITLE)
				.withMetafieldsGlobalTitleTag(SOME_METAFIELDS_GLOBAL_TITLE_TAG)
				.withProductType(SOME_PRODUCT_TYPE)
				.withBodyHtml(SOME_BODY_HTML)
				.withMetafieldsGlobalDescriptionTag(SOME_METAFIELDS_GLOBAL_DESCRIPTION_TAG)
				.withVendor(SOME_VENDOR)
				.withTags(SOME_TAGS)
				.withSortedOptionNames(Arrays.asList(SOME_FIRST_OPTION_NAME, SOME_SECOND_OPTION_NAME, SOME_THIRD_OPTION_NAME))
				.withImageSources(Arrays.asList(SOME_FIRST_IMAGE_SOURCE, SOME_SECOND_IMAGE_SOURCE))
				.withVariantCreationRequests(Arrays.asList(firstVariantCreationRequest, secondVariantCreationRequest, thirdVariantCreationRequest))
				.withPublished(false)
				.build();
	
		final ShopifyProduct actualShopifyProduct = actualShopifyProductCreationRequest.getRequest();
	
		// Verificações...
	}
	
	private ShopifyVariantCreationRequest buildShopifyVariant(String firstOption, String secondOption, String thirdOption, String imageSource) {
		return ShopifyVariantCreationRequest.newBuilder()
				.withPrice(BigDecimal.TEN)
				.withCompareAtPrice(BigDecimal.TEN)
				.withSku(SOME_SKU)
				.withBarcode(SOME_BARCODE)
				.withWeight(BigDecimal.ZERO)
				.withAvailable(SOME_QUANTITY)
				.withFirstOption(firstOption)
				.withSecondOption(secondOption)
				.withThirdOption(thirdOption)
				.withImageSource(imageSource)
				.withDefaultInventoryManagement()
				.withDefaultInventoryPolicy()
				.withDefaultFulfillmentService()
				.withRequiresShippingDefault()
				.withTaxableDefault()
				.build();
	}
	
}
