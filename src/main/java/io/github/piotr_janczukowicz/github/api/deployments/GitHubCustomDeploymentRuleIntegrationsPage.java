package io.github.piotr_janczukowicz.github.api.deployments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A list of custom deployment rule integrations available for this environment.
 *
 * @param totalCount The total number of custom deployment protection rule integrations available for this environment.
 * @param availableCustomDeploymentRuleIntegrations Available custom deployment rule integrations
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/repos/list-custom-deployment-rule-integrations/200")
@RecordBuilder
public record GitHubCustomDeploymentRuleIntegrationsPage(
        @Nullable Integer totalCount,

        @JsonProperty("available_custom_deployment_protection_rule_integrations") @Nullable List<GitHubCustomDeploymentRuleApp> availableCustomDeploymentRuleIntegrations) {}
