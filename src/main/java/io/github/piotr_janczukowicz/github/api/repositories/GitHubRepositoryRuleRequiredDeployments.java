package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** required_deployments rule. */
@CatalogSchema("components/schemas/repository-rule-required-deployments")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleRequiredDeployments(
        GitHubRepositoryRuleRequiredDeploymentsType type,
        @Nullable GitHubRepositoryRuleRequiredDeploymentsParameters parameters)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleRequiredDeployments {
        requireNonNull(type, "type");
    }
}
