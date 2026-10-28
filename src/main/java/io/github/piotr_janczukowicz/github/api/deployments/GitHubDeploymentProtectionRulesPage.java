package io.github.piotr_janczukowicz.github.api.deployments;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * List of deployment protection rules.
 *
 * @param totalCount The number of enabled custom deployment protection rules for this environment
 * @param customDeploymentProtectionRules Custom deployment protection rules
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/repos/get-all-deployment-protection-rules/200")
@RecordBuilder
public record GitHubDeploymentProtectionRulesPage(
        @Nullable Integer totalCount, @Nullable List<GitHubDeploymentProtectionRule> customDeploymentProtectionRules) {}
