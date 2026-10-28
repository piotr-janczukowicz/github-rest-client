package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** CodeScanningTool. */
@CatalogSchema("components/schemas/repository-rule-params-code-scanning-tool")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleParamsCodeScanningTool(
        GitHubRepositoryRuleCodeScanningAlertsThreshold alertsThreshold,
        GitHubRepositoryRuleCodeScanningSecurityAlertsThreshold securityAlertsThreshold,
        String tool) {
    public GitHubRepositoryRuleParamsCodeScanningTool {
        requireNonNull(alertsThreshold, "alertsThreshold");
        requireNonNull(securityAlertsThreshold, "securityAlertsThreshold");
        requireNonNull(tool, "tool");
    }
}
