package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Pending assignee suggestion on an updated issue.
 *
 * @param login Login
 * @param rationale Rationale
 * @param suggest Suggest
 * @param confidence Confidence
 * @param ignored Ignored
 * @param ignoredReason Ignored reason
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/issues/update/200/properties/suggestions/properties/assignees/items")
public record GitHubIssueUpdateAssigneeSuggestion(
        @Nullable String login,
        @Nullable String rationale,
        @Nullable Boolean suggest,
        @Nullable GitHubIssueUpdateConfidence confidence,
        @Nullable Boolean ignored,
        @Nullable GitHubIssueUpdateIgnoredReason ignoredReason) {}
