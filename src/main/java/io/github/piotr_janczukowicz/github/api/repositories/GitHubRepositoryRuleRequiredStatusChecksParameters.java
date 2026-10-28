package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/** Parameters of the required status checks rule: the checks that must pass and whether branches must be up to date. */
@CatalogSchema("components/schemas/repository-rule-required-status-checks/properties/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleRequiredStatusChecksParameters(
        @Nullable Boolean doNotEnforceOnCreate,
        List<GitHubRepositoryRuleParamsStatusCheckConfiguration> requiredStatusChecks,
        boolean strictRequiredStatusChecksPolicy) {
    public GitHubRepositoryRuleRequiredStatusChecksParameters {
        requiredStatusChecks = List.copyOf(requireNonNull(requiredStatusChecks, "requiredStatusChecks"));
    }
}
