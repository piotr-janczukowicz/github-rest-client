package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/** Parameters of the required deployments rule: the environments that must be deployed successfully before merging. */
@CatalogSchema("components/schemas/repository-rule-required-deployments/properties/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleRequiredDeploymentsParameters(List<String> requiredDeploymentEnvironments) {
    public GitHubRepositoryRuleRequiredDeploymentsParameters {
        requiredDeploymentEnvironments =
                List.copyOf(requireNonNull(requiredDeploymentEnvironments, "requiredDeploymentEnvironments"));
    }
}
