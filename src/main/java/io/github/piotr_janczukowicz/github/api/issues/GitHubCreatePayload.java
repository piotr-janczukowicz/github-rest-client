package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create issue request body.
 *
 * @param title issue title
 * @param body issue body
 * @param milestone milestone reference
 * @param labels labels to associate with the issue
 * @param assignees assignee logins
 * @param issueFieldValues custom field values
 * @param type issue type name
 * @param parentIssueId parent issue id for sub-issue creation
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/issues/create/requestBody")
public record GitHubCreatePayload(
        @CatalogSimpleXor("string|integer") String title,
        @Nullable String body,
        @Nullable GitHubIssueMilestoneReference milestone,
        @Nullable List<GitHubCreateIssueLabel> labels,
        @Nullable List<String> assignees,
        @Nullable List<GitHubCreateIssueFieldValue> issueFieldValues,
        @Nullable String type,
        @Nullable Long parentIssueId) {

    public GitHubCreatePayload {
        requireNonNull(title, "title");
        if (labels != null) {
            labels = List.copyOf(labels);
        }
        if (assignees != null) {
            assignees = List.copyOf(assignees);
        }
        if (issueFieldValues != null) {
            issueFieldValues = List.copyOf(issueFieldValues);
        }
    }
}
