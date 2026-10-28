package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.codesofconduct.GitHubCodeOfConduct;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Minimal Repository.
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
 * @param disabled Disabled
 * @param visibility Visibility
 * @param pushedAt Pushed at
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param permissions Permissions
 * @param roleName Role name
 * @param tempCloneToken Temp clone token
 * @param deleteBranchOnMerge Delete branch on merge
 * @param subscribersCount Subscribers count
 * @param networkCount Network count
 * @param codeOfConduct Code Of Conduct
 * @param license License
 * @param forks Forks
 * @param openIssues Open issues
 * @param watchers Watchers
 * @param allowForking Allow forking
 * @param webCommitSignoffRequired Web commit signoff required
 * @param securityAndAnalysis Security and analysis
 * @param customProperties The custom properties that were defined for the repository. The keys are the custom property
 *     names, and the values are the corresponding custom property values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/minimal-repository")
public record GitHubMinimalRepository(
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
        @Nullable String gitUrl,
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
        @Nullable String sshUrl,
        String stargazersUrl,
        String statusesUrl,
        String subscribersUrl,
        String subscriptionUrl,
        String tagsUrl,
        String teamsUrl,
        String treesUrl,
        @Nullable String cloneUrl,
        @Nullable String mirrorUrl,
        String hooksUrl,
        @Nullable String svnUrl,
        @Nullable String homepage,
        @Nullable String language,
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
        @Nullable GitHubRepositoryPermissions permissions,
        @Nullable String roleName,
        @Nullable String tempCloneToken,
        @Nullable Boolean deleteBranchOnMerge,
        @Nullable Long subscribersCount,
        @Nullable Long networkCount,
        @Nullable GitHubCodeOfConduct codeOfConduct,
        @Nullable GitHubMinimalRepositoryLicense license,
        @Nullable Long forks,
        @Nullable Long openIssues,
        @Nullable Long watchers,
        @Nullable Boolean allowForking,
        @Nullable Boolean webCommitSignoffRequired,
        @Nullable GitHubSecurityAndAnalysis securityAndAnalysis,
        @Nullable Map<String, Object> customProperties) {

    public GitHubMinimalRepository {
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
        requireNonNull(stargazersUrl, "stargazersUrl");
        requireNonNull(statusesUrl, "statusesUrl");
        requireNonNull(subscribersUrl, "subscribersUrl");
        requireNonNull(subscriptionUrl, "subscriptionUrl");
        requireNonNull(tagsUrl, "tagsUrl");
        requireNonNull(teamsUrl, "teamsUrl");
        requireNonNull(treesUrl, "treesUrl");
        requireNonNull(hooksUrl, "hooksUrl");
    }

    /**
     * Describes this repository by its identity without credential material.
     *
     * @return text with {@code tempCloneToken} omitted
     */
    @Override
    public String toString() {
        return "GitHubMinimalRepository[id=" + id + ", fullName=" + fullName + ", htmlUrl=" + htmlUrl + "]";
    }
}
