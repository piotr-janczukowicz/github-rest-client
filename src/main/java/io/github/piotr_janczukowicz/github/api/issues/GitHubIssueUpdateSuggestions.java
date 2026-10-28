package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Pending suggestions returned with an updated issue.
 *
 * @param type Type suggestions
 * @param issueFieldValues Issue field value suggestions
 * @param labels Label suggestions
 * @param assignees Assignee suggestions
 * @param state State suggestions
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/issues/update/200/properties/suggestions")
public record GitHubIssueUpdateSuggestions(
        @Nullable List<GitHubIssueUpdateTypeSuggestion> type,
        @Nullable List<GitHubIssueUpdateFieldSuggestion> issueFieldValues,
        @Nullable List<GitHubIssueUpdateLabelSuggestion> labels,
        @Nullable List<GitHubIssueUpdateAssigneeSuggestion> assignees,
        @Nullable List<GitHubIssueUpdateStateSuggestion> state) {}
