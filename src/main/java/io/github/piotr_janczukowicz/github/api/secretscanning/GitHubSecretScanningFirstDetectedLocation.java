package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Details on the location where the token was initially detected. This can be a commit, wiki commit, issue, discussion,
 * pull request. A shape that matches none of them is kept raw as {@link GitHubSecretScanningUnknownLocationDetails}, so
 * that one new location shape does not fail the whole alert or list it is in.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = GitHubSecretScanningUnknownLocationDetails.class)
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
    @JsonSubTypes.Type(GitHubSecretScanningLocationPullRequestReviewComment.class)
})
@CatalogSchema("components/schemas/secret-scanning-first-detected-location")
public sealed interface GitHubSecretScanningFirstDetectedLocation
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
                GitHubSecretScanningUnknownLocationDetails {}
