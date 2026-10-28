package io.github.piotr_janczukowicz.github.api.scim;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Named user attributes to update. An attribute left {@code null} is omitted from the request;
 * {@code OptionalNull.ofNull()} sends an explicit JSON {@code null}, which clears the attribute.
 *
 * @param active The active status of the User
 * @param userName Configured by the admin. Could be an email, login, or username
 * @param externalId The ID of the User
 * @param givenName The given name of the user
 * @param familyName The family name of the user
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/scim/update-attribute-for-user/requestBody/properties/Operations/items/properties/value/oneOf/0")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateAttributeForUserValueObject(
        @Nullable OptionalNull<Boolean> active,
        @JsonProperty("userName") @Nullable OptionalNull<String> userName,
        @JsonProperty("externalId") @Nullable OptionalNull<String> externalId,
        @JsonProperty("givenName") @Nullable OptionalNull<String> givenName,
        @JsonProperty("familyName") @Nullable OptionalNull<String> familyName) {}
