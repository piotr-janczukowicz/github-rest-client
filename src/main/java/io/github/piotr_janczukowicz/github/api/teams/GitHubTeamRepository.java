package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.licenses.GitHubLicenseSimple;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Team Repository. A team's access to a repository.
 *
 * @param id Unique identifier of the repository
 * @param nodeId Node id
 * @param name The name of the repository.
 * @param fullName Full name
 * @param license License
 * @param forks Forks
 * @param permissions Permissions
 * @param roleName Role name
 * @param owner Owner
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
 * @param size Size
 * @param defaultBranch The default branch of the repository.
 * @param openIssuesCount Open issues count
 * @param isTemplate Whether this repository acts as a template that can be used to generate new repositories.
 * @param topics Topics
 * @param hasIssues Whether issues are enabled.
 * @param hasProjects Whether projects are enabled.
 * @param hasWiki Whether the wiki is enabled.
 * @param hasPages Has pages
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
 * @param allowMergeCommit Whether to allow merge commits for pull requests.
 * @param allowForking Whether to allow forking this repo
 * @param webCommitSignoffRequired Whether to require contributors to sign off on web-based commits
 * @param subscribersCount Subscribers count
 * @param networkCount Network count
 * @param openIssues Open issues
 * @param watchers Watchers
 * @param masterBranch Master branch
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/team-repository")
@RecordBuilder
public record GitHubTeamRepository(
        long id,
        String nodeId,
        String name,
        String fullName,
        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable GitHubLicenseSimple license,
        long forks,
        @Nullable GitHubTeamRepositoryPermissions permissions,
        @Nullable String roleName,
        @Nullable GitHubSimpleUser owner,
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
        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable String mirrorUrl,
        String hooksUrl,
        String svnUrl,
        @Nullable String homepage,
        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable String language,
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
        @Nullable Boolean allowMergeCommit,
        @Nullable Boolean allowForking,
        @Nullable Boolean webCommitSignoffRequired,
        @Nullable Long subscribersCount,
        @Nullable Long networkCount,
        long openIssues,
        long watchers,
        @Nullable String masterBranch) {

    public GitHubTeamRepository {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(name, "name");
        requireNonNull(fullName, "fullName");
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
     * Describes this record by its identity without credential material.
     *
     * @return text with {@code tempCloneToken} omitted
     */
    @Override
    public String toString() {
        return "GitHubTeamRepository[id=" + id + ", fullName=" + fullName + ", htmlUrl=" + htmlUrl + "]";
    }
}
