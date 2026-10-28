package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.issues.GitHubIssue;
import io.github.piotr_janczukowicz.github.api.issues.GitHubLabel;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Issues event payload.
 *
 * @param action The action that was performed
 * @param issue The issue
 * @param assignee The user assigned or unassigned
 * @param assignees The users assigned to the issue
 * @param label The label added or removed
 * @param labels The labels of the issue
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/issues-event")
@RecordBuilder
public record GitHubIssuesEvent(
        String action,
        GitHubIssue issue,
        @Nullable GitHubSimpleUser assignee,
        @Nullable List<GitHubSimpleUser> assignees,
        @Nullable GitHubLabel label,
        @Nullable List<GitHubLabel> labels)
        implements GitHubEventPayload {

    public GitHubIssuesEvent {
        requireNonNull(action, "action");
        requireNonNull(issue, "issue");
    }
}
