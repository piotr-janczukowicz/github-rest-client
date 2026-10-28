package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Payload of a GitHub event; the concrete record is chosen by the event {@code type}. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/event/properties/payload")
public sealed interface GitHubEventPayload
        permits GitHubCreateEvent,
                GitHubDeleteEvent,
                GitHubDiscussionEvent,
                GitHubIssuesEvent,
                GitHubIssueCommentEvent,
                GitHubForkEvent,
                GitHubGollumEvent,
                GitHubMemberEvent,
                GitHubPublicEvent,
                GitHubPushEvent,
                GitHubPullRequestEvent,
                GitHubPullRequestReviewCommentEvent,
                GitHubPullRequestReviewEvent,
                GitHubReleaseEvent,
                GitHubWatchEvent,
                GitHubCommitCommentEvent,
                GitHubUnknownEventPayload {}
