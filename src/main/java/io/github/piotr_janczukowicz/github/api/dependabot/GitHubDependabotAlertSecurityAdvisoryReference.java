package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * A link to additional advisory information.
 *
 * @param url The URL of the reference.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/dependabot-alert-security-advisory/properties/references/items")
public record GitHubDependabotAlertSecurityAdvisoryReference(String url) {

    public GitHubDependabotAlertSecurityAdvisoryReference {
        requireNonNull(url, "url");
    }
}
