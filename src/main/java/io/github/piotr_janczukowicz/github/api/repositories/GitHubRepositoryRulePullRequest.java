package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** pull_request rule. */
@CatalogSchema("components/schemas/repository-rule-pull-request")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRulePullRequest(
        GitHubRepositoryRulePullRequestType type, @Nullable GitHubRepositoryRulePullRequestParameters parameters)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRulePullRequest {
        requireNonNull(type, "type");
    }
}
