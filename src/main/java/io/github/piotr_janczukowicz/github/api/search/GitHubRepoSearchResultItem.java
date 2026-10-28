package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.licenses.GitHubLicenseSimple;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestCreationPolicy;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Repo Search Result Item.
 *
 * @param id Id
 * @param nodeId Node id
 * @param name Name
 * @param fullName Full name
 * @param owner A GitHub user.
 * @param isPrivate Is private
 * @param htmlUrl Html url
 * @param description Description
 * @param fork Fork
 * @param url Url
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param pushedAt Pushed at
 * @param homepage Homepage
 * @param size The size of the repository, in kilobytes. Size is calculated hourly. When a repository is initially
 *     created, the size is 0.
 * @param stargazersCount Stargazers count
 * @param watchersCount Watchers count
 * @param language Language
 * @param forksCount Forks count
 * @param openIssuesCount Open issues count
 * @param masterBranch Master branch
 * @param defaultBranch Default branch
 * @param score Score
 * @param forksUrl Forks url
 * @param keysUrl Keys url
 * @param collaboratorsUrl Collaborators url
 * @param teamsUrl Teams url
 * @param hooksUrl Hooks url
 * @param issueEventsUrl Issue events url
 * @param eventsUrl Events url
 * @param assigneesUrl Assignees url
 * @param branchesUrl Branches url
 * @param tagsUrl Tags url
 * @param blobsUrl Blobs url
 * @param gitTagsUrl Git tags url
 * @param gitRefsUrl Git refs url
 * @param treesUrl Trees url
 * @param statusesUrl Statuses url
 * @param languagesUrl Languages url
 * @param stargazersUrl Stargazers url
 * @param contributorsUrl Contributors url
 * @param subscribersUrl Subscribers url
 * @param subscriptionUrl Subscription url
 * @param commitsUrl Commits url
 * @param gitCommitsUrl Git commits url
 * @param commentsUrl Comments url
 * @param issueCommentUrl Issue comment url
 * @param contentsUrl Contents url
 * @param compareUrl Compare url
 * @param mergesUrl Merges url
 * @param archiveUrl Archive url
 * @param downloadsUrl Downloads url
 * @param issuesUrl Issues url
 * @param pullsUrl Pulls url
 * @param milestonesUrl Milestones url
 * @param notificationsUrl Notifications url
 * @param labelsUrl Labels url
 * @param releasesUrl Releases url
 * @param deploymentsUrl Deployments url
 * @param gitUrl Git url
 * @param sshUrl Ssh url
 * @param cloneUrl Clone url
 * @param svnUrl Svn url
 * @param forks Forks
 * @param openIssues Open issues
 * @param watchers Watchers
 * @param topics Topics
 * @param mirrorUrl Mirror url
 * @param hasIssues Has issues
 * @param hasProjects Has projects
 * @param hasPages Has pages
 * @param hasWiki Has wiki
 * @param hasDownloads Has downloads
 * @param hasDiscussions Has discussions
 * @param hasPullRequests Has pull requests
 * @param pullRequestCreationPolicy The policy controlling who can create pull requests: all or collaborators_only.
 * @param archived Archived
 * @param disabled Returns whether or not this repository disabled.
 * @param visibility The repository visibility: public, private, or internal.
 * @param license License
 * @param permissions Permissions
 * @param textMatches Text matches
 * @param tempCloneToken Temp clone token
 * @param allowMergeCommit Allow merge commit
 * @param allowSquashMerge Allow squash merge
 * @param allowRebaseMerge Allow rebase merge
 * @param allowAutoMerge Allow auto merge
 * @param deleteBranchOnMerge Delete branch on merge
 * @param allowForking Allow forking
 * @param isTemplate Is template
 * @param webCommitSignoffRequired Web commit signoff required
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/repo-search-result-item")
public record GitHubRepoSearchResultItem(
        long id,
        String nodeId,
        String name,
        String fullName,
        @Nullable GitHubSimpleUser owner,
        @JsonProperty("private") boolean isPrivate,
        String htmlUrl,
        @Nullable String description,
        boolean fork,
        String url,
        Instant createdAt,
        Instant updatedAt,
        Instant pushedAt,
        @Nullable String homepage,
        int size,
        int stargazersCount,
        int watchersCount,
        @Nullable String language,
        int forksCount,
        int openIssuesCount,
        @Nullable String masterBranch,
        String defaultBranch,
        double score,
        String forksUrl,
        String keysUrl,
        String collaboratorsUrl,
        String teamsUrl,
        String hooksUrl,
        String issueEventsUrl,
        String eventsUrl,
        String assigneesUrl,
        String branchesUrl,
        String tagsUrl,
        String blobsUrl,
        String gitTagsUrl,
        String gitRefsUrl,
        String treesUrl,
        String statusesUrl,
        String languagesUrl,
        String stargazersUrl,
        String contributorsUrl,
        String subscribersUrl,
        String subscriptionUrl,
        String commitsUrl,
        String gitCommitsUrl,
        String commentsUrl,
        String issueCommentUrl,
        String contentsUrl,
        String compareUrl,
        String mergesUrl,
        String archiveUrl,
        String downloadsUrl,
        String issuesUrl,
        String pullsUrl,
        String milestonesUrl,
        String notificationsUrl,
        String labelsUrl,
        String releasesUrl,
        String deploymentsUrl,
        String gitUrl,
        String sshUrl,
        String cloneUrl,
        String svnUrl,
        int forks,
        int openIssues,
        int watchers,
        @Nullable List<String> topics,
        @Nullable String mirrorUrl,
        boolean hasIssues,
        boolean hasProjects,
        boolean hasPages,
        boolean hasWiki,
        boolean hasDownloads,
        @Nullable Boolean hasDiscussions,
        @Nullable Boolean hasPullRequests,
        @Nullable GitHubPullRequestCreationPolicy pullRequestCreationPolicy,
        boolean archived,
        boolean disabled,
        @Nullable String visibility,
        @Nullable GitHubLicenseSimple license,
        @Nullable Permissions permissions,
        @Nullable List<GitHubSearchTextMatchItem> textMatches,
        @Nullable String tempCloneToken,
        @Nullable Boolean allowMergeCommit,
        @Nullable Boolean allowSquashMerge,
        @Nullable Boolean allowRebaseMerge,
        @Nullable Boolean allowAutoMerge,
        @Nullable Boolean deleteBranchOnMerge,
        @Nullable Boolean allowForking,
        @Nullable Boolean isTemplate,
        @Nullable Boolean webCommitSignoffRequired) {

    public GitHubRepoSearchResultItem {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(name, "name");
        requireNonNull(fullName, "fullName");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(url, "url");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(pushedAt, "pushedAt");
        requireNonNull(defaultBranch, "defaultBranch");
        requireNonNull(forksUrl, "forksUrl");
        requireNonNull(keysUrl, "keysUrl");
        requireNonNull(collaboratorsUrl, "collaboratorsUrl");
        requireNonNull(teamsUrl, "teamsUrl");
        requireNonNull(hooksUrl, "hooksUrl");
        requireNonNull(issueEventsUrl, "issueEventsUrl");
        requireNonNull(eventsUrl, "eventsUrl");
        requireNonNull(assigneesUrl, "assigneesUrl");
        requireNonNull(branchesUrl, "branchesUrl");
        requireNonNull(tagsUrl, "tagsUrl");
        requireNonNull(blobsUrl, "blobsUrl");
        requireNonNull(gitTagsUrl, "gitTagsUrl");
        requireNonNull(gitRefsUrl, "gitRefsUrl");
        requireNonNull(treesUrl, "treesUrl");
        requireNonNull(statusesUrl, "statusesUrl");
        requireNonNull(languagesUrl, "languagesUrl");
        requireNonNull(stargazersUrl, "stargazersUrl");
        requireNonNull(contributorsUrl, "contributorsUrl");
        requireNonNull(subscribersUrl, "subscribersUrl");
        requireNonNull(subscriptionUrl, "subscriptionUrl");
        requireNonNull(commitsUrl, "commitsUrl");
        requireNonNull(gitCommitsUrl, "gitCommitsUrl");
        requireNonNull(commentsUrl, "commentsUrl");
        requireNonNull(issueCommentUrl, "issueCommentUrl");
        requireNonNull(contentsUrl, "contentsUrl");
        requireNonNull(compareUrl, "compareUrl");
        requireNonNull(mergesUrl, "mergesUrl");
        requireNonNull(archiveUrl, "archiveUrl");
        requireNonNull(downloadsUrl, "downloadsUrl");
        requireNonNull(issuesUrl, "issuesUrl");
        requireNonNull(pullsUrl, "pullsUrl");
        requireNonNull(milestonesUrl, "milestonesUrl");
        requireNonNull(notificationsUrl, "notificationsUrl");
        requireNonNull(labelsUrl, "labelsUrl");
        requireNonNull(releasesUrl, "releasesUrl");
        requireNonNull(deploymentsUrl, "deploymentsUrl");
        requireNonNull(gitUrl, "gitUrl");
        requireNonNull(sshUrl, "sshUrl");
        requireNonNull(cloneUrl, "cloneUrl");
        requireNonNull(svnUrl, "svnUrl");
    }

    /**
     * Describes this record by its identity without credential material.
     *
     * @return text with {@code tempCloneToken} omitted
     */
    @Override
    public String toString() {
        return "GitHubRepoSearchResultItem[id=" + id + ", fullName=" + fullName + ", htmlUrl=" + htmlUrl + "]";
    }

    /**
     * Repository permissions on a repo search result.
     *
     * @param admin Admin
     * @param maintain Maintain
     * @param push Push
     * @param triage Triage
     * @param pull Pull
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    @CatalogSchema("components/schemas/repo-search-result-item/properties/permissions")
    public record Permissions(
            boolean admin,
            @Nullable Boolean maintain,
            boolean push,
            @Nullable Boolean triage,
            boolean pull) {}
}
