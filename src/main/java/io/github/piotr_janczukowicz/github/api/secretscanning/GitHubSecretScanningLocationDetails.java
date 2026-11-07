package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Location-specific details for a secret scanning finding. The sibling {@code type} of the location selects the
 * implementing record (see {@code GitHubSecretScanningLocationDeserializer}); the {@code actions_log} shape is a subset
 * of the commit shapes, so the object shape alone cannot tell it apart.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
    @JsonSubTypes.Type(GitHubSecretScanningLocationCommit.class),
    @JsonSubTypes.Type(GitHubSecretScanningLocationWikiCommit.class),
    @JsonSubTypes.Type(GitHubSecretScanningLocationIssueTitle.class),
    @JsonSubTypes.Type(GitHubSecretScanningLocationIssueBody.class),
    @JsonSubTypes.Type(GitHubSecretScanningLocationIssueComment.class),
    @JsonSubTypes.Type(GitHubSecretScanningLocationDiscussionTitle.class),
    @JsonSubTypes.Type(GitHubSecretScanningLocationDiscussionBody.class),
    @JsonSubTypes.Type(GitHubSecretScanningLocationDiscussionComment.class),
    @JsonSubTypes.Type(GitHubSecretScanningLocationPullRequestTitle.class),
    @JsonSubTypes.Type(GitHubSecretScanningLocationPullRequestBody.class),
    @JsonSubTypes.Type(GitHubSecretScanningLocationPullRequestComment.class),
    @JsonSubTypes.Type(GitHubSecretScanningLocationPullRequestReview.class),
    @JsonSubTypes.Type(GitHubSecretScanningLocationPullRequestReviewComment.class),
    @JsonSubTypes.Type(GitHubSecretScanningLocationActionsLog.class)
})
@CatalogSchema("components/schemas/secret-scanning-location/properties/details")
public sealed interface GitHubSecretScanningLocationDetails
        permits GitHubSecretScanningLocationCommit,
                GitHubSecretScanningLocationWikiCommit,
                GitHubSecretScanningLocationIssueTitle,
                GitHubSecretScanningLocationIssueBody,
                GitHubSecretScanningLocationIssueComment,
                GitHubSecretScanningLocationDiscussionTitle,
                GitHubSecretScanningLocationDiscussionBody,
                GitHubSecretScanningLocationDiscussionComment,
                GitHubSecretScanningLocationPullRequestTitle,
                GitHubSecretScanningLocationPullRequestBody,
                GitHubSecretScanningLocationPullRequestComment,
                GitHubSecretScanningLocationPullRequestReview,
                GitHubSecretScanningLocationPullRequestReviewComment,
                GitHubSecretScanningLocationActionsLog,
                GitHubSecretScanningUnknownLocationDetails {}
