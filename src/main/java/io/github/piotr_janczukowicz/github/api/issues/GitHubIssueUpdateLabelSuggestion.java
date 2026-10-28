package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Pending label suggestion on an updated issue.
 *
 * @param name Name
 * @param rationale Rationale
 * @param suggest Suggest
 * @param confidence Confidence
 * @param ignored Ignored
 * @param ignoredReason Ignored reason
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/issues/update/200/properties/suggestions/properties/labels/items")
public record GitHubIssueUpdateLabelSuggestion(
        @Nullable String name,
        @Nullable String rationale,
        @Nullable Boolean suggest,
        @Nullable GitHubIssueUpdateConfidence confidence,
        @Nullable Boolean ignored,
        @Nullable GitHubIssueUpdateIgnoredReason ignoredReason) {}
