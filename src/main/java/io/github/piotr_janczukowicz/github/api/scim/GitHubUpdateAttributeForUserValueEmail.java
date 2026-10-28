package io.github.piotr_janczukowicz.github.api.scim;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * An email address to set on the user.
 *
 * @param value The email address
 * @param primary Whether this is the primary email address
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/scim/update-attribute-for-user/requestBody/properties/Operations/items/properties/value/oneOf/1/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateAttributeForUserValueEmail(
        @Nullable String value, @Nullable Boolean primary) {}
