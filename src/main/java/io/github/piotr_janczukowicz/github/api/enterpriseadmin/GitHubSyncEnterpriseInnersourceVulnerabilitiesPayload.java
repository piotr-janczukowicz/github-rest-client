package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Innersource vulnerabilities to synchronize for an enterprise.
 *
 * @param vulnerabilities Array of vulnerabilities in OSV format to synchronize
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/sync-enterprise-innersource-vulnerabilities/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubSyncEnterpriseInnersourceVulnerabilitiesPayload(List<GitHubOsvVulnerability> vulnerabilities) {

    public GitHubSyncEnterpriseInnersourceVulnerabilitiesPayload {
        vulnerabilities = List.copyOf(
                requireSizeAtMost(requireNonEmpty(vulnerabilities, "vulnerabilities"), 100, "vulnerabilities"));
    }
}
