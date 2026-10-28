package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Pending state suggestion on an updated issue.
 *
 * @param value Value
 * @param stateReason State reason
 * @param duplicateIssueId Duplicate issue id
 * @param rationale Rationale
 * @param suggest Suggest
 * @param confidence Confidence
 * @param ignored Ignored
 * @param ignoredReason Ignored reason
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/issues/update/200/properties/suggestions/properties/state/items")
public record GitHubIssueUpdateStateSuggestion(
        @Nullable String value,
        @Nullable String stateReason,
        @Nullable Long duplicateIssueId,
        @Nullable String rationale,
        @Nullable Boolean suggest,
        @Nullable GitHubIssueUpdateConfidence confidence,
        @Nullable Boolean ignored,
        @Nullable GitHubIssueUpdateIgnoredReason ignoredReason) {}
