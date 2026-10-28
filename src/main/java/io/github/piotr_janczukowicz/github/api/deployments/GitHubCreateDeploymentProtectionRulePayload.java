package io.github.piotr_janczukowicz.github.api.deployments;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create a custom deployment protection rule.
 *
 * @param integrationId The ID of the custom app that will be enabled on the environment.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-deployment-protection-rule/requestBody")
public record GitHubCreateDeploymentProtectionRulePayload(
        @Nullable Long integrationId) {}
