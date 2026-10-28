package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Deployment protection rule.
 *
 * @param id The unique identifier for the deployment protection rule.
 * @param nodeId The node ID for the deployment protection rule.
 * @param enabled Whether the deployment protection rule is enabled for the environment.
 * @param app App
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/deployment-protection-rule")
@RecordBuilder
public record GitHubDeploymentProtectionRule(
        long id, String nodeId, boolean enabled, GitHubCustomDeploymentRuleApp app) {

    public GitHubDeploymentProtectionRule {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(app, "app");
    }
}
