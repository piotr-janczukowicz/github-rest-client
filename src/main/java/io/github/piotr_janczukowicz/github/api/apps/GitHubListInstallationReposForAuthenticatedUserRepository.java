package io.github.piotr_janczukowicz.github.api.apps;

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
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Repository. A repository on GitHub.
 *
 * @param id Unique identifier of the repository
 * @param nodeId Node id
 * @param name The name of the repository.
 * @param fullName Full name
 * @param license License
 * @param forks Forks
 * @param permissions Permissions
 * @param owner A GitHub user.
 * @param isPrivate Is private
 * @param htmlUrl Html url
 * @param description Description
 * @param fork Fork
 * @param url Url
 * @param archiveUrl Archive url
 * @param assigneesUrl Assignees url
 * @param blobsUrl Blobs url
 * @param branchesUrl Branches url
 * @param collaboratorsUrl Collaborators url
 * @param commentsUrl Comments url
 * @param commitsUrl Commits url
 * @param compareUrl Compare url
 * @param contentsUrl Contents url
 * @param contributorsUrl Contributors url
 * @param deploymentsUrl Deployments url
 * @param downloadsUrl Downloads url
 * @param eventsUrl Events url
 * @param forksUrl Forks url
 * @param gitCommitsUrl Git commits url
 * @param gitRefsUrl Git refs url
 * @param gitTagsUrl Git tags url
 * @param gitUrl Git url
 * @param issueCommentUrl Issue comment url
 * @param issueEventsUrl Issue events url
 * @param issuesUrl Issues url
 * @param keysUrl Keys url
 * @param labelsUrl Labels url
 * @param languagesUrl Languages url
 * @param mergesUrl Merges url
 * @param milestonesUrl Milestones url
 * @param notificationsUrl Notifications url
 * @param pullsUrl Pulls url
 * @param releasesUrl Releases url
 * @param sshUrl Ssh url
 * @param stargazersUrl Stargazers url
 * @param statusesUrl Statuses url
 * @param subscribersUrl Subscribers url
 * @param subscriptionUrl Subscription url
 * @param tagsUrl Tags url
 * @param teamsUrl Teams url
 * @param treesUrl Trees url
 * @param cloneUrl Clone url
 * @param mirrorUrl Mirror url
 * @param hooksUrl Hooks url
 * @param svnUrl Svn url
 * @param homepage Homepage
 * @param language Language
 * @param forksCount Forks count
 * @param stargazersCount Stargazers count
 * @param watchersCount Watchers count
 * @param size The size of the repository, in kilobytes. Size is calculated hourly. When a repository is initially
 *     created, the size is 0.
 * @param defaultBranch The default branch of the repository.
 * @param openIssuesCount Open issues count
 * @param isTemplate Whether this repository acts as a template that can be used to generate new repositories.
 * @param topics Topics
 * @param hasIssues Whether issues are enabled.
 * @param hasProjects Whether projects are enabled.
 * @param hasWiki Whether the wiki is enabled.
 * @param hasPages Has pages
 * @param hasDiscussions Whether discussions are enabled.
 * @param hasPullRequests Whether pull requests are enabled.
 * @param pullRequestCreationPolicy The policy controlling who can create pull requests: all or collaborators_only.
 * @param archived Whether the repository is archived.
 * @param disabled Returns whether or not this repository disabled.
 * @param visibility The repository visibility: public, private, or internal.
 * @param pushedAt Pushed at
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param allowRebaseMerge Whether to allow rebase merges for pull requests.
 * @param tempCloneToken Temp clone token
 * @param allowSquashMerge Whether to allow squash merges for pull requests.
 * @param allowAutoMerge Whether to allow Auto-merge to be used on pull requests.
 * @param deleteBranchOnMerge Whether to delete head branches when pull requests are merged
 * @param allowUpdateBranch Whether or not a pull request head branch that is behind its base branch can always be
 *     updated even if it is not required to be up to date before merging.
 * @param squashMergeCommitTitle The default value for a squash merge commit title: - `PR_TITLE` - default to the pull
 *     request's title. - `COMMIT_OR_PR_TITLE` - default to the commit's title (if only one commit) or the pull
 *     request's title (when more than one commit).
 * @param squashMergeCommitMessage The default value for a squash merge commit message: - `PR_BODY` - default to the
 *     pull request's body. - `COMMIT_MESSAGES` - default to the branch's commit messages. - `BLANK` - default to a
 *     blank commit message.
 * @param mergeCommitTitle The default value for a merge commit title. - `PR_TITLE` - default to the pull request's
 *     title. - `MERGE_MESSAGE` - default to the classic title for a merge message (e.g., Merge pull request #123 from
 *     branch-name).
 * @param mergeCommitMessage The default value for a merge commit message. - `PR_TITLE` - default to the pull request's
 *     title. - `PR_BODY` - default to the pull request's body. - `BLANK` - default to a blank commit message.
 * @param allowMergeCommit Whether to allow merge commits for pull requests.
 * @param allowForking Whether to allow forking this repo
 * @param webCommitSignoffRequired Whether to require contributors to sign off on web-based commits
 * @param openIssues Open issues
 * @param watchers Watchers
 * @param starredAt Starred at
 * @param anonymousAccessEnabled Whether anonymous git access is enabled for this repository
 * @param codeSearchIndexStatus The status of the code search index for this repository
 * @param customProperties The custom properties that were defined for the repository. The keys are the custom property
 *     names, and the values are the corresponding custom property values. Present for org repos only.
 */
@CatalogSchema("operations/apps/list-installation-repos-for-authenticated-user/200/properties/repositories/items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubListInstallationReposForAuthenticatedUserRepository(
        long id,
        String nodeId,
        String name,
        String fullName,
        @Nullable OptionalNull<GitHubLicenseSimple> license,
        long forks,
        @Nullable GitHubRepositoryPermissions permissions,
        GitHubSimpleUser owner,
        @JsonProperty("private") boolean isPrivate,
        String htmlUrl,
        @Nullable String description,
        boolean fork,
        String url,
        String archiveUrl,
        String assigneesUrl,
        String blobsUrl,
        String branchesUrl,
        String collaboratorsUrl,
        String commentsUrl,
        String commitsUrl,
        String compareUrl,
        String contentsUrl,
        String contributorsUrl,
        String deploymentsUrl,
        String downloadsUrl,
        String eventsUrl,
        String forksUrl,
        String gitCommitsUrl,
        String gitRefsUrl,
        String gitTagsUrl,
        String gitUrl,
        String issueCommentUrl,
        String issueEventsUrl,
        String issuesUrl,
        String keysUrl,
        String labelsUrl,
        String languagesUrl,
        String mergesUrl,
        String milestonesUrl,
        String notificationsUrl,
        String pullsUrl,
        String releasesUrl,
        String sshUrl,
        String stargazersUrl,
        String statusesUrl,
        String subscribersUrl,
        String subscriptionUrl,
        String tagsUrl,
        String teamsUrl,
        String treesUrl,
        String cloneUrl,
        @Nullable OptionalNull<String> mirrorUrl,
        String hooksUrl,
        String svnUrl,
        @Nullable String homepage,
        @Nullable OptionalNull<String> language,
        long forksCount,
        long stargazersCount,
        long watchersCount,
        long size,
        String defaultBranch,
        long openIssuesCount,
        @Nullable Boolean isTemplate,
        @Nullable List<String> topics,
        boolean hasIssues,
        boolean hasProjects,
        boolean hasWiki,
        boolean hasPages,
        @Nullable Boolean hasDiscussions,
        @Nullable Boolean hasPullRequests,
        @Nullable GitHubPullRequestCreationPolicy pullRequestCreationPolicy,
        boolean archived,
        boolean disabled,
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
        long openIssues,
        long watchers,
        @Nullable String starredAt,
        @Nullable Boolean anonymousAccessEnabled,
        @Nullable GitHubCodeSearchIndexStatus codeSearchIndexStatus,
        @Nullable Map<String, Object> customProperties) {

    public GitHubListInstallationReposForAuthenticatedUserRepository {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(name, "name");
        requireNonNull(fullName, "fullName");
        requireNonNull(owner, "owner");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(url, "url");
        requireNonNull(archiveUrl, "archiveUrl");
        requireNonNull(assigneesUrl, "assigneesUrl");
        requireNonNull(blobsUrl, "blobsUrl");
        requireNonNull(branchesUrl, "branchesUrl");
        requireNonNull(collaboratorsUrl, "collaboratorsUrl");
        requireNonNull(commentsUrl, "commentsUrl");
        requireNonNull(commitsUrl, "commitsUrl");
        requireNonNull(compareUrl, "compareUrl");
        requireNonNull(contentsUrl, "contentsUrl");
        requireNonNull(contributorsUrl, "contributorsUrl");
        requireNonNull(deploymentsUrl, "deploymentsUrl");
        requireNonNull(downloadsUrl, "downloadsUrl");
        requireNonNull(eventsUrl, "eventsUrl");
        requireNonNull(forksUrl, "forksUrl");
        requireNonNull(gitCommitsUrl, "gitCommitsUrl");
        requireNonNull(gitRefsUrl, "gitRefsUrl");
        requireNonNull(gitTagsUrl, "gitTagsUrl");
        requireNonNull(gitUrl, "gitUrl");
        requireNonNull(issueCommentUrl, "issueCommentUrl");
        requireNonNull(issueEventsUrl, "issueEventsUrl");
        requireNonNull(issuesUrl, "issuesUrl");
        requireNonNull(keysUrl, "keysUrl");
        requireNonNull(labelsUrl, "labelsUrl");
        requireNonNull(languagesUrl, "languagesUrl");
        requireNonNull(mergesUrl, "mergesUrl");
        requireNonNull(milestonesUrl, "milestonesUrl");
        requireNonNull(notificationsUrl, "notificationsUrl");
        requireNonNull(pullsUrl, "pullsUrl");
        requireNonNull(releasesUrl, "releasesUrl");
        requireNonNull(sshUrl, "sshUrl");
        requireNonNull(stargazersUrl, "stargazersUrl");
        requireNonNull(statusesUrl, "statusesUrl");
        requireNonNull(subscribersUrl, "subscribersUrl");
        requireNonNull(subscriptionUrl, "subscriptionUrl");
        requireNonNull(tagsUrl, "tagsUrl");
        requireNonNull(teamsUrl, "teamsUrl");
        requireNonNull(treesUrl, "treesUrl");
        requireNonNull(cloneUrl, "cloneUrl");
        requireNonNull(hooksUrl, "hooksUrl");
        requireNonNull(svnUrl, "svnUrl");
        requireNonNull(defaultBranch, "defaultBranch");
    }

    /**
     * Describes this repository by its identity without credential material.
     *
     * @return text with {@code tempCloneToken} omitted
     */
    @Override
    public String toString() {
        return "GitHubListInstallationReposForAuthenticatedUserRepository[id=" + id + ", fullName=" + fullName
                + ", htmlUrl=" + htmlUrl + "]";
    }
}
