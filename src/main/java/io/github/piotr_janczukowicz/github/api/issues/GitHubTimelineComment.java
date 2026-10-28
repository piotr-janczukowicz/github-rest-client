package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.commits.GitHubCommitComment;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequestReviewComment;

/**
 * A comment of a timeline event: a pull request review comment on a line-commented event, or a commit comment on a
 * commit-commented event. Every property of a commit comment also exists on a review comment, so deduction cannot tell
 * the two apart by property names alone; a JSON object that does not match the review comment alone is decoded as a
 * commit comment.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = GitHubCommitComment.class)
@JsonSubTypes({@JsonSubTypes.Type(GitHubPullRequestReviewComment.class), @JsonSubTypes.Type(GitHubCommitComment.class)})
public sealed interface GitHubTimelineComment permits GitHubPullRequestReviewComment, GitHubCommitComment {}
