package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Issue Event Intent.
 *
 * @param rationale The reasoning the agent provided for the change.
 * @param confidence The confidence level the agent had when performing this action.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/issue-event-intent")
@RecordBuilder
public record GitHubIssueEventIntent(
        @Nullable String rationale, @Nullable GitHubIssueSuggestionConfidence confidence) {}
