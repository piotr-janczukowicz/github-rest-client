package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * An advisory identifier.
 *
 * @param type The type of advisory identifier.
 * @param value The value of the advisory identifer.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/dependabot-alert-security-advisory/properties/identifiers/items")
public record GitHubDependabotAlertSecurityAdvisoryIdentifier(
        GitHubDependabotAlertSecurityAdvisoryIdentifierType type, String value) {

    public GitHubDependabotAlertSecurityAdvisoryIdentifier {
        requireNonNull(type, "type");
        requireNonNull(value, "value");
    }
}
