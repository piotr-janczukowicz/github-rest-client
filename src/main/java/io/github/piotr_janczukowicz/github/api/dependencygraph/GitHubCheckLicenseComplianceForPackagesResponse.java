package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;

/**
 * License compliance results keyed by the original submitted package URLs.
 *
 * @param purls License compliance result for each submitted package URL
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/dependency-graph/check-license-compliance-for-packages/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCheckLicenseComplianceForPackagesResponse(
        Map<String, GitHubCheckLicenseComplianceForPackagesPurlResult> purls) {

    public GitHubCheckLicenseComplianceForPackagesResponse {
        requireNonNull(purls, "purls");
    }
}
