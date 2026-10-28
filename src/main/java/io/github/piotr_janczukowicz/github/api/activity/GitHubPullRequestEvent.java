package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.issues.GitHubLabel;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Pull request event payload.
 *
 * @param action The action that was performed
 * @param number The pull request number
 * @param pullRequest The pull request
 * @param assignee The user assigned or unassigned
 * @param assignees The users assigned to the pull request
 * @param label The label added or removed
 * @param labels The labels of the pull request
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/pull-request-event")
@RecordBuilder
public record GitHubPullRequestEvent(
        String action,
        int number,
        GitHubPullRequestEventPullRequest pullRequest,
        @Nullable GitHubSimpleUser assignee,
        @Nullable List<GitHubSimpleUser> assignees,
        @Nullable GitHubLabel label,
        @Nullable List<GitHubLabel> labels)
        implements GitHubEventPayload {

    public GitHubPullRequestEvent {
        requireNonNull(action, "action");
        requireNonNull(pullRequest, "pullRequest");
    }
}
