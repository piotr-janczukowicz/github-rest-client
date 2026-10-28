package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Code scanning alert items.
 *
 * @param number The security alert number.
 * @param createdAt The time that the alert was created in ISO 8601 format: `YYYY-MM-DDTHH:MM:SSZ`.
 * @param updatedAt The time that the alert was last updated in ISO 8601 format: `YYYY-MM-DDTHH:MM:SSZ`.
 * @param url The REST API URL of the alert resource.
 * @param htmlUrl The GitHub URL of the alert resource.
 * @param instancesUrl The REST API URL for fetching the list of instances for an alert.
 * @param state State of a code scanning alert.
 * @param fixedAt The time that the alert was no longer detected and was considered fixed in ISO 8601 format:
 *     `YYYY-MM-DDTHH:MM:SSZ`.
 * @param dismissedBy A GitHub user.
 * @param dismissedAt The time that the alert was dismissed in ISO 8601 format: `YYYY-MM-DDTHH:MM:SSZ`.
 * @param dismissedReason **Required when the state is dismissed.** The reason for dismissing or closing the alert.
 * @param dismissedComment The dismissal comment associated with the dismissal of the alert.
 * @param rule Rule.
 * @param tool Tool.
 * @param mostRecentInstance Most recent instance.
 * @param dismissalApprovedBy A GitHub user.
 * @param assignees Assignees.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-alert-items")
public record GitHubCodeScanningAlertItems(
        int number,
        Instant createdAt,
        @Nullable Instant updatedAt,
        String url,
        String htmlUrl,
        String instancesUrl,
        @Nullable GitHubCodeScanningAlertState state,
        @Nullable Instant fixedAt,
        @Nullable GitHubSimpleUser dismissedBy,
        @Nullable Instant dismissedAt,
        @Nullable GitHubCodeScanningAlertDismissedReason dismissedReason,
        @Nullable String dismissedComment,
        GitHubCodeScanningAlertRuleSummary rule,
        GitHubCodeScanningAnalysisTool tool,
        GitHubCodeScanningAlertInstance mostRecentInstance,
        @Nullable GitHubSimpleUser dismissalApprovedBy,
        @Nullable List<GitHubSimpleUser> assignees) {
    public GitHubCodeScanningAlertItems {
        requireNonNull(createdAt, "createdAt");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(instancesUrl, "instancesUrl");
        dismissedComment = requireMaxLengthIfPresent(dismissedComment, 280, "dismissedComment");
        requireNonNull(rule, "rule");
        requireNonNull(tool, "tool");
        requireNonNull(mostRecentInstance, "mostRecentInstance");
    }
}
