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
 * @param value The email address
 * @param primary Whether this is the primary email address
 * @param type The type of email address
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/scim-user/properties/emails/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubScimUserEmail(
        String value, @Nullable Boolean primary, @Nullable String type) {

    public GitHubScimUserEmail {
        requireNonNull(value, "value");
    }
}
