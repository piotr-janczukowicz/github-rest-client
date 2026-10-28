package io.github.piotr_janczukowicz.github.api.scim;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

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
@CatalogSchema("operations/scim/set-information-for-provisioned-user/requestBody/properties/name")
@Flavors(GitHubFlavor.GHEC)
public record GitHubSetInformationForProvisionedUserName(
        @JsonProperty("givenName") String givenName,
        @JsonProperty("familyName") String familyName,
        @Nullable String formatted) {

    public GitHubSetInformationForProvisionedUserName {
        requireNonNull(givenName, "givenName");
        requireNonNull(familyName, "familyName");
    }
}
