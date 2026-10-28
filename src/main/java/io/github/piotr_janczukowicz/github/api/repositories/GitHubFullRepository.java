package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.licenses.GitHubLicenseSimple;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubCodeOfConductSimple;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Full Repository.
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
 * @param defaultBranch Default branch
 * @param openIssuesCount Open issues count
 * @param isTemplate Is template
 * @param topics Topics
 * @param hasIssues Has issues
 * @param hasProjects Has projects
 * @param hasWiki Has wiki
 * @param hasPages Has pages
 * @param hasDiscussions Has discussions
 * @param hasPullRequests Has pull requests
 * @param pullRequestCreationPolicy The policy controlling who can create pull requests: all or collaborators_only.
 * @param archived Archived
 * @param disabled Returns whether or not this repository disabled.
 * @param visibility The repository visibility: public, private, or internal.
 * @param pushedAt Pushed at
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param permissions Permissions
 * @param allowRebaseMerge Allow rebase merge
 * @param templateRepository Template repository
 * @param tempCloneToken Temp clone token
 * @param allowSquashMerge Allow squash merge
 * @param allowAutoMerge Allow auto merge
 * @param deleteBranchOnMerge Delete branch on merge
 * @param allowMergeCommit Allow merge commit
 * @param allowUpdateBranch Allow update branch
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
 * @param allowForking Allow forking
 * @param webCommitSignoffRequired Web commit signoff required
 * @param subscribersCount Subscribers count
 * @param networkCount Network count
 * @param license License
 * @param organization Organization
 * @param parent A repository on GitHub.
 * @param source A repository on GitHub.
 * @param forks Forks
 * @param masterBranch Master branch
 * @param openIssues Open issues
 * @param watchers Watchers
 * @param anonymousAccessEnabled Whether anonymous git access is allowed.
 * @param codeOfConduct Code of Conduct Simple
 * @param securityAndAnalysis Security and analysis
 * @param customProperties The custom properties that were defined for the repository. The keys are the custom property
 *     names, and the values are the corresponding custom property values.
 */
@CatalogSchema("components/schemas/full-repository")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubFullRepository(
        long id,
        String nodeId,
        String name,
        String fullName,
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
        boolean hasDiscussions,
        @Nullable Boolean hasPullRequests,
        @Nullable GitHubPullRequestCreationPolicy pullRequestCreationPolicy,
        boolean archived,
        boolean disabled,
        @Nullable String visibility,
        Instant pushedAt,
        Instant createdAt,
        Instant updatedAt,
        @Nullable GitHubRepositoryPermissions permissions,
        @Nullable Boolean allowRebaseMerge,
        @Nullable GitHubRepository templateRepository,
        @Nullable String tempCloneToken,
        @Nullable Boolean allowSquashMerge,
        @Nullable Boolean allowAutoMerge,
        @Nullable Boolean deleteBranchOnMerge,
        @Nullable Boolean allowMergeCommit,
        @Nullable Boolean allowUpdateBranch,
        @Nullable GitHubSquashMergeCommitTitle squashMergeCommitTitle,
        @Nullable GitHubSquashMergeCommitMessage squashMergeCommitMessage,
        @Nullable GitHubMergeCommitTitle mergeCommitTitle,
        @Nullable GitHubMergeCommitMessage mergeCommitMessage,
        @Nullable Boolean allowForking,
        @Nullable Boolean webCommitSignoffRequired,
        long subscribersCount,
        long networkCount,
        @Nullable OptionalNull<GitHubLicenseSimple> license,
        @Nullable GitHubSimpleUser organization,
        @Nullable GitHubRepository parent,
        @Nullable GitHubRepository source,
        long forks,
        @Nullable String masterBranch,
        long openIssues,
        long watchers,
        @Nullable Boolean anonymousAccessEnabled,
        @Nullable GitHubCodeOfConductSimple codeOfConduct,
        @Nullable GitHubSecurityAndAnalysis securityAndAnalysis,
        @Nullable Map<String, Object> customProperties) {

    public GitHubFullRepository {
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
        requireNonNull(pushedAt, "pushedAt");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }

    /**
     * Describes this repository by its identity without credential material.
     *
     * @return text with {@code tempCloneToken} omitted
     */
    @Override
    public String toString() {
        return "GitHubFullRepository[id=" + id + ", fullName=" + fullName + ", htmlUrl=" + htmlUrl + "]";
    }
}
