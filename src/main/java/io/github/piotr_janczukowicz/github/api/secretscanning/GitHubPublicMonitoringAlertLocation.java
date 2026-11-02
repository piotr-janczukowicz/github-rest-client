package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Delivered source location details for a public monitoring alert.
 *
 * @param path The path of the file.
 * @param startLine The first line of the secret.
 * @param endLine The last line of the secret.
 * @param startColumn The first column of the secret.
 * @param endColumn The last column of the secret.
 * @param blobSha The SHA of the blob.
 * @param commitSha The SHA of the commit.
 * @param htmlUrl The browser URL of the location.
 * @param contentNumber The number of the content the secret was found in.
 * @param contentId The identifier of the content the secret was found in.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/public-monitoring-alert-location")
@Flavors(GitHubFlavor.GHEC)
// The implemented sealed interface carries the deduction; this type itself decodes as a plain record.
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public record GitHubPublicMonitoringAlertLocation(
        @Nullable String path,
        @Nullable Integer startLine,
        @Nullable Integer endLine,
        @Nullable Integer startColumn,
        @Nullable Integer endColumn,
        @Nullable String blobSha,
        @Nullable String commitSha,
        @Nullable String htmlUrl,
        @Nullable Integer contentNumber,
        @Nullable Long contentId)
        implements GitHubPublicMonitoringAlertAttributionCommit,
                GitHubPublicMonitoringAlertAttributionWikiCommit,
                GitHubPublicMonitoringAlertAttributionIssueTitle,
                GitHubPublicMonitoringAlertAttributionIssueBody,
                GitHubPublicMonitoringAlertAttributionIssueComment,
                GitHubPublicMonitoringAlertAttributionPullRequestTitle,
                GitHubPublicMonitoringAlertAttributionPullRequestBody,
                GitHubPublicMonitoringAlertAttributionPullRequestComment,
                GitHubPublicMonitoringAlertAttributionPullRequestReviewComment,
                GitHubPublicMonitoringAlertAttributionPullRequestReview,
                GitHubPublicMonitoringAlertAttributionDiscussionTitle,
                GitHubPublicMonitoringAlertAttributionDiscussionBody,
                GitHubPublicMonitoringAlertAttributionDiscussionComment {}
