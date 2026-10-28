package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * A CWE weakness assigned to the advisory.
 *
 * @param cweId The unique CWE ID.
 * @param name The short, plain text name of the CWE.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/dependabot-alert-security-advisory/properties/cwes/items")
public record GitHubDependabotAlertSecurityAdvisoryCwe(String cweId, String name) {

    public GitHubDependabotAlertSecurityAdvisoryCwe {
        requireNonNull(cweId, "cweId");
        requireNonNull(name, "name");
    }
}
