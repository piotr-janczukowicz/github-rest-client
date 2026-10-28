package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.licenses.GitHubLicenseSimple;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCodeSearchIndexStatus;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMergeCommitMessage;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMergeCommitTitle;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestCreationPolicy;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryPermissions;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSquashMergeCommitMessage;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSquashMergeCommitTitle;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The repository of a branch of a pull request event. Only the id, URL and name are in the minimal object; GitHub
 * Enterprise Server sends the full repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "components/schemas/pull-request-event/properties/pull_request/properties/head/properties/repo",
    "components/schemas/pull-request-event/properties/pull_request/properties/base/properties/repo",
    "components/schemas/pull-request-review-event/properties/pull_request/properties/head/properties/repo",
    "components/schemas/pull-request-review-event/properties/pull_request/properties/base/properties/repo",
    "components/schemas/pull-request-review-comment-event/properties/pull_request/properties/head/properties/repo",
    "components/schemas/pull-request-review-comment-event/properties/pull_request/properties/base/properties/repo"
})
@RecordBuilder
public record GitHubPullRequestEventPullRequestRepo(
        long id,
        @Nullable String nodeId,
        String name,
        @Nullable String fullName,
        @Nullable OptionalNull<GitHubLicenseSimple> license,
        @Nullable Long forks,
        @Nullable GitHubRepositoryPermissions permissions,
        @Nullable GitHubSimpleUser owner,
        @Nullable @JsonProperty("private") Boolean isPrivate,
        @Nullable String htmlUrl,
        @Nullable String description,
        @Nullable Boolean fork,
        String url,
        @Nullable String archiveUrl,
        @Nullable String assigneesUrl,
        @Nullable String blobsUrl,
        @Nullable String branchesUrl,
        @Nullable String collaboratorsUrl,
        @Nullable String commentsUrl,
        @Nullable String commitsUrl,
        @Nullable String compareUrl,
        @Nullable String contentsUrl,
        @Nullable String contributorsUrl,
        @Nullable String deploymentsUrl,
        @Nullable String downloadsUrl,
        @Nullable String eventsUrl,
        @Nullable String forksUrl,
        @Nullable String gitCommitsUrl,
        @Nullable String gitRefsUrl,
        @Nullable String gitTagsUrl,
        @Nullable String gitUrl,
        @Nullable String issueCommentUrl,
        @Nullable String issueEventsUrl,
        @Nullable String issuesUrl,
        @Nullable String keysUrl,
        @Nullable String labelsUrl,
        @Nullable String languagesUrl,
        @Nullable String mergesUrl,
        @Nullable String milestonesUrl,
        @Nullable String notificationsUrl,
        @Nullable String pullsUrl,
        @Nullable String releasesUrl,
        @Nullable String sshUrl,
        @Nullable String stargazersUrl,
        @Nullable String statusesUrl,
        @Nullable String subscribersUrl,
        @Nullable String subscriptionUrl,
        @Nullable String tagsUrl,
        @Nullable String teamsUrl,
        @Nullable String treesUrl,
        @Nullable String cloneUrl,
        @Nullable OptionalNull<String> mirrorUrl,
        @Nullable String hooksUrl,
        @Nullable String svnUrl,
        @Nullable String homepage,
        @Nullable OptionalNull<String> language,
        @Nullable Long forksCount,
        @Nullable Long stargazersCount,
        @Nullable Long watchersCount,
        @Nullable Long size,
        @Nullable String defaultBranch,
        @Nullable Long openIssuesCount,
        @Nullable Boolean isTemplate,
        @Nullable List<String> topics,
        @Nullable Boolean hasIssues,
        @Nullable Boolean hasProjects,
        @Nullable Boolean hasWiki,
        @Nullable Boolean hasPages,
        @Nullable Boolean hasDiscussions,
        @Nullable Boolean hasPullRequests,
        @Nullable GitHubPullRequestCreationPolicy pullRequestCreationPolicy,
        @Nullable Boolean archived,
        @Nullable Boolean disabled,
        @Nullable String visibility,
        @Nullable Instant pushedAt,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt,
        @Nullable Boolean allowRebaseMerge,
        @Nullable String tempCloneToken,
        @Nullable Boolean allowSquashMerge,
        @Nullable Boolean allowAutoMerge,
        @Nullable Boolean deleteBranchOnMerge,
        @Nullable Boolean allowUpdateBranch,
        @Nullable GitHubSquashMergeCommitTitle squashMergeCommitTitle,
        @Nullable GitHubSquashMergeCommitMessage squashMergeCommitMessage,
        @Nullable GitHubMergeCommitTitle mergeCommitTitle,
        @Nullable GitHubMergeCommitMessage mergeCommitMessage,
        @Nullable Boolean allowMergeCommit,
        @Nullable Boolean allowForking,
        @Nullable Boolean webCommitSignoffRequired,
        @Nullable Long openIssues,
        @Nullable Long watchers,
        @Nullable String starredAt,
        @Nullable Boolean anonymousAccessEnabled,
        @Nullable GitHubCodeSearchIndexStatus codeSearchIndexStatus) {
    public GitHubPullRequestEventPullRequestRepo {
        requireNonNull(name, "name");
        requireNonNull(url, "url");
    }

    /**
     * Short description without the clone token or any other field that may carry a credential.
     *
     * @return identifying fields only
     */
    @Override
    public String toString() {
        return "GitHubPullRequestEventPullRequestRepo[id=" + id + ", name=" + name + ", url=" + url + "]";
    }
}
