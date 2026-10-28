package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The license compliance result of one package URL.
 *
 * @param status The package's license compliance result
 * @param license The resolved SPDX license expression, or null if unresolved
 * @param reason The reason for the compliance result
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/dependency-graph/check-license-compliance-for-packages/200/properties/purls/additionalProperties")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCheckLicenseComplianceForPackagesPurlResult(
        GitHubCheckLicenseComplianceForPackagesPurlResultStatus status,
        @Nullable String license,
        GitHubCheckLicenseComplianceForPackagesPurlResultReason reason) {

    public GitHubCheckLicenseComplianceForPackagesPurlResult {
        requireNonNull(status, "status");
        requireNonNull(reason, "reason");
    }
}
