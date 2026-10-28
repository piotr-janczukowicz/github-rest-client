package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** copilot_code_review rule. */
@CatalogSchema("components/schemas/repository-rule-copilot-code-review")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleCopilotCodeReview(
        GitHubRepositoryRuleCopilotCodeReviewType type,
        @Nullable GitHubRepositoryRuleCopilotCodeReviewParameters parameters)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleCopilotCodeReview {
        requireNonNull(type, "type");
    }
}
