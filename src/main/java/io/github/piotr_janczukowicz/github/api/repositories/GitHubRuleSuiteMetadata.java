package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Additional metadata of a rule evaluation: a pull request result or a required status checks result.
 *
 * @param pullRequest pull request associated with the rule evaluation
 * @param checks status checks associated with the rule evaluation
 */
@CatalogSchema({
    "components/schemas/rule-suite/properties/rule_evaluations/items/properties/metadata",
    "components/schemas/rule-suite-pull-request",
    "components/schemas/rule-suite-required-status-checks"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRuleSuiteMetadata(
        @Nullable GitHubRuleSuitePullRequestMetadata pullRequest,
        @Nullable List<GitHubRuleSuiteCheck> checks) {

    public GitHubRuleSuiteMetadata {
        if (pullRequest != null && checks != null) {
            throw new IllegalArgumentException("at most one of pullRequest or checks is allowed");
        }
    }
}
