package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import org.jspecify.annotations.Nullable;

/**
 * License information of a GitHub Enterprise Server instance.
 *
 * @param seats Number of seats, or {@code unlimited}; GitHub sends a string or an integer, read as text
 * @param seatsUsed Number of seats in use
 * @param seatsAvailable Number of seats left, or {@code unlimited}; GitHub sends a string or an integer, read as text
 * @param kind Kind of the license
 * @param daysUntilExpiration Days until the license expires
 * @param expireAt Expiration of the license
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/license-info")
@Flavors(GitHubFlavor.GHES)
public record GitHubLicenseInfo(
        @CatalogSimpleXor("string|integer") @Nullable String seats,
        @Nullable Integer seatsUsed,
        @CatalogSimpleXor("string|integer") @Nullable String seatsAvailable,
        @Nullable String kind,
        @Nullable Integer daysUntilExpiration,
        @Nullable String expireAt) {}
