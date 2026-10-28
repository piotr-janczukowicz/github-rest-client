package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * An email address to provision for a SCIM user.
 *
 * @param value The email address.
 * @param type The type of email address.
 * @param primary Whether this email address is the primary address.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/user-emails/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubUserEmail(String value, String type, boolean primary) {

    public GitHubUserEmail {
        requireNonNull(value, "value");
        requireNonNull(type, "type");
    }
}
