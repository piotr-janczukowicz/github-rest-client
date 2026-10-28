package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueType;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Issue Search Result Item.
 *
 * @param url Url
 * @param repositoryUrl Repository url
 * @param labelsUrl Labels url
 * @param commentsUrl Comments url
 * @param eventsUrl Events url
 * @param htmlUrl Html url
 * @param id Id
 * @param nodeId Node id
 * @param number Number
 * @param title Title
 * @param locked Locked
 * @param activeLockReason Active lock reason
 * @param assignees Assignees
 * @param user User
 * @param labels Labels
 * @param subIssuesSummary Sub issues summary
 * @param issueDependenciesSummary Issue dependencies summary
 * @param issueFieldValues Issue field values
 * @param state State
 * @param stateReason State reason
 * @param milestone Milestone
 * @param comments Comments
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param closedAt Closed at
 * @param textMatches Text matches
 * @param pullRequest Pull request
 * @param body Body
 * @param score Score
 * @param authorAssociation Author association
 * @param draft Draft
 * @param repository Repository
 * @param bodyHtml Body html
 * @param bodyText Body text
 * @param timelineUrl Timeline url
 * @param type Type
 * @param performedViaGithubApp Performed via github app
 * @param pinnedComment Pinned comment
 * @param reactions Reactions
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/issue-search-result-item")
public record GitHubIssueSearchResultItem(
        String url,
        String repositoryUrl,
        String labelsUrl,
        String commentsUrl,
        String eventsUrl,
        String htmlUrl,
        long id,
        String nodeId,
        int number,
        String title,
        boolean locked,
        @Nullable String activeLockReason,
        @Nullable List<GitHubSimpleUser> assignees,
        @Nullable GitHubSimpleUser user,
        List<Label> labels,
        @Nullable GitHubSubIssuesSummary subIssuesSummary,
        @Nullable GitHubIssueDependenciesSummary issueDependenciesSummary,
        @Nullable List<GitHubIssueFieldValue> issueFieldValues,
        String state,
        @Nullable String stateReason,
        @Nullable GitHubMilestone milestone,
        int comments,
        Instant createdAt,
        Instant updatedAt,
        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable Instant closedAt,
        @Nullable List<GitHubSearchTextMatchItem> textMatches,
        @Nullable PullRequest pullRequest,
        @Nullable String body,
        double score,
        GitHubAuthorAssociation authorAssociation,
        @Nullable Boolean draft,
        @Nullable GitHubRepository repository,
        @Nullable String bodyHtml,
        @Nullable String bodyText,
        @Nullable String timelineUrl,
        @Nullable GitHubIssueType type,
        @Nullable GitHubIntegration performedViaGithubApp,
        @Nullable GitHubIssueComment pinnedComment,
        @Nullable GitHubReactionRollup reactions) {

    public GitHubIssueSearchResultItem {
        requireNonNull(url, "url");
        requireNonNull(repositoryUrl, "repositoryUrl");
        requireNonNull(labelsUrl, "labelsUrl");
        requireNonNull(commentsUrl, "commentsUrl");
        requireNonNull(eventsUrl, "eventsUrl");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(title, "title");
        requireNonNull(labels, "labels");
        requireNonNull(state, "state");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(authorAssociation, "authorAssociation");
    }

    /**
     * Label on an issue search result.
     *
     * @param id Id
     * @param nodeId Node id
     * @param url Url
     * @param name Name
     * @param color Color
     * @param isDefault Default
     * @param description Description
     * @param archivedAt Timestamp indicating when the label was archived, or {@code null} if it has not been archived
     * @param archivedBy The user who archived the label, or {@code null} if it has not been archived
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    @CatalogSchema("components/schemas/issue-search-result-item/properties/labels/items")
    public record Label(
            @Nullable Long id,
            @Nullable String nodeId,
            @Nullable String url,
            @Nullable String name,
            @Nullable String color,
            @JsonProperty("default") @Nullable Boolean isDefault,
            @Nullable String description,
            @Nullable Instant archivedAt,
            @Nullable GitHubSimpleUser archivedBy) {}

    /**
     * Pull request metadata on an issue search result.
     *
     * @param mergedAt Merged at
     * @param diffUrl Diff url
     * @param htmlUrl Html url
     * @param patchUrl Patch url
     * @param url Url
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    @CatalogSchema("components/schemas/issue-search-result-item/properties/pull_request")
    public record PullRequest(
            @Nullable Instant mergedAt,
            @Nullable String diffUrl,
            @Nullable String htmlUrl,
            @Nullable String patchUrl,
            @Nullable String url) {}
}
