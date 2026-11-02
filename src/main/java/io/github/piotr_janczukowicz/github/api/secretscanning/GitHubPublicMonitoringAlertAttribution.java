package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Attribution information for the alert.
 *
 * @param secretAuthor The user attributed as the secret author.
 * @param committerEmail The email address associated with the secret's author.
 * @param method The method used to attribute the alert.
 * @param commit The commit location of the secret.
 * @param wikiCommit The wiki commit location of the secret.
 * @param issueTitle The issue title location of the secret.
 * @param issueBody The issue body location of the secret.
 * @param issueComment The issue comment location of the secret.
 * @param pullRequestTitle The pull request title location of the secret.
 * @param pullRequestBody The pull request body location of the secret.
 * @param pullRequestComment The pull request comment location of the secret.
 * @param pullRequestReviewComment The pull request review comment location of the secret.
 * @param pullRequestReview The pull request review location of the secret.
 * @param discussionTitle The discussion title location of the secret.
 * @param discussionBody The discussion body location of the secret.
 * @param discussionComment The discussion comment location of the secret.
 * @param pullRequestTimelineComment The pull request timeline comment location of the secret.
 * @param commitMetadata The commit metadata location of the secret.
 * @param commitComment The commit comment location of the secret.
 * @param actionsLog The actions log location of the secret.
 * @param gistBlob The gist blob location of the secret.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/public-monitoring-alert/properties/attribution")
@Flavors(GitHubFlavor.GHEC)
public record GitHubPublicMonitoringAlertAttribution(
        @Nullable GitHubPublicMonitoringAlertSecretAuthor secretAuthor,
        @Nullable String committerEmail,
        @Nullable GitHubPublicMonitoringAlertAttributionMethod method,
        @Nullable GitHubPublicMonitoringAlertAttributionCommit commit,
        @Nullable GitHubPublicMonitoringAlertAttributionWikiCommit wikiCommit,
        @Nullable GitHubPublicMonitoringAlertAttributionIssueTitle issueTitle,
        @Nullable GitHubPublicMonitoringAlertAttributionIssueBody issueBody,
        @Nullable GitHubPublicMonitoringAlertAttributionIssueComment issueComment,
        @Nullable GitHubPublicMonitoringAlertAttributionPullRequestTitle pullRequestTitle,
        @Nullable GitHubPublicMonitoringAlertAttributionPullRequestBody pullRequestBody,
        @Nullable GitHubPublicMonitoringAlertAttributionPullRequestComment pullRequestComment,
        @Nullable GitHubPublicMonitoringAlertAttributionPullRequestReviewComment pullRequestReviewComment,
        @Nullable GitHubPublicMonitoringAlertAttributionPullRequestReview pullRequestReview,
        @Nullable GitHubPublicMonitoringAlertAttributionDiscussionTitle discussionTitle,
        @Nullable GitHubPublicMonitoringAlertAttributionDiscussionBody discussionBody,
        @Nullable GitHubPublicMonitoringAlertAttributionDiscussionComment discussionComment,
        @Nullable GitHubPublicMonitoringAlertLocation pullRequestTimelineComment,
        @Nullable GitHubPublicMonitoringAlertLocation commitMetadata,
        @Nullable GitHubPublicMonitoringAlertLocation commitComment,
        @Nullable GitHubPublicMonitoringAlertLocation actionsLog,
        @Nullable GitHubPublicMonitoringAlertLocation gistBlob) {}
