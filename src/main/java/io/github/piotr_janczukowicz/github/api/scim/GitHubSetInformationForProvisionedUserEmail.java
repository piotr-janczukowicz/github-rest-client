package io.github.piotr_janczukowicz.github.api.scim;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * An email address of a SCIM user.
 *
 * @param type The type of email address
 * @param value The email address
 * @param primary Whether this is the primary email address
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/scim/set-information-for-provisioned-user/requestBody/properties/emails/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubSetInformationForProvisionedUserEmail(
        @Nullable String type, String value, @Nullable Boolean primary) {

    public GitHubSetInformationForProvisionedUserEmail {
        requireNonNull(value, "value");
    }
}
