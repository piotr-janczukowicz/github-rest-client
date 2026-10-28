package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Details pertaining to the package version that patches this vulnerability.
 *
 * @param identifier The package version that patches this vulnerability.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/dependabot-alert-security-vulnerability/properties/first_patched_version")
public record GitHubDependabotAlertFirstPatchedVersion(String identifier) {

    public GitHubDependabotAlertFirstPatchedVersion {
        requireNonNull(identifier, "identifier");
    }
}
