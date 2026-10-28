package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Parameters of the Copilot code review rule: whether Copilot reviews draft pull requests and new pushes. */
@CatalogSchema("components/schemas/repository-rule-copilot-code-review/properties/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleCopilotCodeReviewParameters(
        @Nullable Boolean reviewDraftPullRequests, @Nullable Boolean reviewOnPush) {}
