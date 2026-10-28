package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** license_compliance_scanning rule. */
@CatalogSchema("components/schemas/repository-rule-license-compliance-scanning")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepositoryRuleLicenseComplianceScanning(GitHubRepositoryRuleLicenseComplianceScanningType type)
        implements GitHubRepositoryRule {

    public GitHubRepositoryRuleLicenseComplianceScanning {
        requireNonNull(type, "type");
    }
}
