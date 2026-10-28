package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * A GitHub App that is providing a custom deployment protection rule.
 *
 * @param id The unique identifier of the deployment protection rule integration.
 * @param slug The slugified name of the deployment protection rule integration.
 * @param integrationUrl The URL for the endpoint to get details about the app.
 * @param nodeId The node ID for the deployment protection rule integration.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/custom-deployment-rule-app")
@RecordBuilder
public record GitHubCustomDeploymentRuleApp(long id, String slug, String integrationUrl, String nodeId) {

    public GitHubCustomDeploymentRuleApp {
        requireNonNull(slug, "slug");
        requireNonNull(integrationUrl, "integrationUrl");
        requireNonNull(nodeId, "nodeId");
    }
}
