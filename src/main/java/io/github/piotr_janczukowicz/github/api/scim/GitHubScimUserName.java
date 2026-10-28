package io.github.piotr_janczukowicz.github.api.scim;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The components of a SCIM user's name.
 *
 * @param givenName The given name of the user
 * @param familyName The family name of the user
 * @param formatted The full name of the user, formatted for display
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/scim-user/properties/name")
@Flavors(GitHubFlavor.GHEC)
public record GitHubScimUserName(
        @JsonProperty("givenName") @Nullable String givenName,
        @JsonProperty("familyName") @Nullable String familyName,
        @Nullable String formatted) {}
