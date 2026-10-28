package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The components of a SCIM user name to provision.
 *
 * @param formatted The full name, including all middle names, titles, and suffixes, formatted for display.
 * @param familyName The family name of the user.
 * @param givenName The given name of the user.
 * @param middleName The middle name(s) of the user.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/user-name")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubUserName(
        @Nullable String formatted,
        @JsonProperty("familyName") String familyName,
        @JsonProperty("givenName") String givenName,
        @JsonProperty("middleName") @Nullable String middleName) {

    public GitHubUserName {
        requireNonNull(familyName, "familyName");
        requireNonNull(givenName, "givenName");
    }
}
