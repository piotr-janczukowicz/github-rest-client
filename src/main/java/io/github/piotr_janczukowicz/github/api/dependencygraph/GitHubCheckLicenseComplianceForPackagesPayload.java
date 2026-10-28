package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Package URLs to check against the license compliance policy.
 *
 * @param purls The package URLs to check, at most 40
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/dependency-graph/check-license-compliance-for-packages/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCheckLicenseComplianceForPackagesPayload(List<String> purls) {

    public GitHubCheckLicenseComplianceForPackagesPayload {
        requireNonNull(purls, "purls");
        purls = List.copyOf(requireSizeAtMost(purls, 40, "purls"));
    }
}
