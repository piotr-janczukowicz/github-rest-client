package io.github.piotr_janczukowicz.github.api.activity;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.api.issues.IssuesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequest;
import io.github.piotr_janczukowicz.github.api.pullrequests.PullrequestsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.releases.ReleasesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestBranchRef;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.time.Instant;
import java.util.List;

final class ActivityCatalogFixtures {

    private static final String API_URL = "https://api.github.com/repos/octocat/Hello-World";
    private static final Instant SINCE = Instant.parse("2024-01-01T00:00:00Z");
    private static final Instant BEFORE = Instant.parse("2024-06-01T00:00:00Z");

    private ActivityCatalogFixtures() {}

    static GitHubListNotificationsForAuthenticatedUserFilter listNotificationsFilter() {
        return GitHubListNotificationsForAuthenticatedUserFilterBuilder.builder()
                .all(true)
                .participating(false)
                .since(SINCE)
                .before(BEFORE)
                .build();
    }

    static GitHubListRepoNotificationsForAuthenticatedUserFilter listRepoNotificationsFilter() {
        return GitHubListRepoNotificationsForAuthenticatedUserFilterBuilder.builder()
                .all(true)
                .participating(false)
                .since(SINCE)
                .before(BEFORE)
                .build();
    }

    static GitHubListReposStarredByAuthenticatedUserFilter listReposStarredByAuthenticatedUserFilter() {
        return GitHubListReposStarredByAuthenticatedUserFilterBuilder.builder()
                .sort(GitHubSortStarred.CREATED)
                .direction(GitHubDirection.DESC)
                .build();
    }

    static GitHubListReposStarredByUserFilter listReposStarredByUserFilter() {
        return GitHubListReposStarredByUserFilterBuilder.builder()
                .sort(GitHubSortStarred.UPDATED)
                .direction(GitHubDirection.ASC)
                .build();
    }

    static GitHubMarkNotificationsAsReadPayload markNotificationsAsReadPayload() {
        return GitHubMarkNotificationsAsReadPayloadBuilder.builder()
                .lastReadAt(SINCE)
                .read(true)
                .build();
    }

    static GitHubMarkNotificationsAsReadResponse markNotificationsAsReadResponse() {
        return GitHubMarkNotificationsAsReadResponseBuilder.builder()
                .message("Notifications marked as read")
                .build();
    }

    static GitHubMarkRepoNotificationsAsReadPayload markRepoNotificationsAsReadPayload() {
        return GitHubMarkRepoNotificationsAsReadPayloadBuilder.builder()
                .lastReadAt(SINCE)
                .build();
    }

    static GitHubMarkRepoNotificationsAsReadResponse markRepoNotificationsAsReadResponse() {
        return GitHubMarkRepoNotificationsAsReadResponseBuilder.builder()
                .message("Notifications marked as read")
                .url(API_URL + "/notifications")
                .build();
    }

    static GitHubSetThreadSubscriptionPayload setThreadSubscriptionPayload() {
        return GitHubSetThreadSubscriptionPayloadBuilder.builder()
                .ignored(false)
                .build();
    }

    static GitHubSetRepoSubscriptionPayload setRepoSubscriptionPayload() {
        return GitHubSetRepoSubscriptionPayloadBuilder.builder()
                .subscribed(true)
                .ignored(false)
                .build();
    }

    static GitHubGetStargazerCountForRepoResponse getStargazerCountForRepoResponse() {
        return new GitHubGetStargazerCountForRepoResponse(42);
    }

    static GitHubStargazerHistory stargazerHistory() {
        return new GitHubStargazerHistory(List.of(0, 12, 7, 0, 0, 0, 0), 19, 1_754_784_000L);
    }

    static GitHubActor actor() {
        return GitHubActorBuilder.builder()
                .id(1)
                .login("octocat")
                .displayLogin("octocat")
                .gravatarId("")
                .url("https://api.github.com/users/octocat")
                .avatarUrl("https://github.com/images/error/octocat_happy.gif")
                .build();
    }

    static GitHubLinkWithType linkWithType() {
        return GitHubLinkWithTypeBuilder.builder()
                .href(API_URL)
                .type("application/atom+xml")
                .build();
    }

    static GitHubFeed feed() {
        return GitHubFeedBuilder.builder()
                .timelineUrl("https://github.com/timeline")
                .userUrl("https://github.com/{user}")
                .currentUserPublicUrl("https://github.com/octocat")
                .currentUserUrl("https://github.com/octocat?token=abc")
                .currentUserActorUrl("https://github.com/octocat/{actor}")
                .currentUserOrganizationUrl("https://github.com/orgs/{org}")
                .currentUserOrganizationUrls(List.of("https://github.com/orgs/github"))
                .securityAdvisoriesUrl("https://github.com/security-advisories")
                .repositoryDiscussionsUrl("https://github.com/discussions")
                .repositoryDiscussionsCategoryUrl("https://github.com/discussions/{category}")
                .links(feedLinks())
                .build();
    }

    static GitHubFeedLinks feedLinks() {
        return GitHubFeedLinksBuilder.builder()
                .timeline(linkWithType())
                .user(linkWithType())
                .securityAdvisories(linkWithType())
                .currentUser(linkWithType())
                .currentUserPublic(linkWithType())
                .currentUserActor(linkWithType())
                .currentUserOrganization(linkWithType())
                .currentUserOrganizations(List.of(linkWithType()))
                .repositoryDiscussions(linkWithType())
                .repositoryDiscussionsCategory(linkWithType())
                .build();
    }

    static GitHubThread thread() {
        return GitHubThreadBuilder.builder()
                .id("1")
                .repository(RepositoriesCatalogFixtures.helloWorldMinimalRepository())
                .subject(threadSubject())
                .reason("subscribed")
                .unread(true)
                .updatedAt("2024-01-01T00:00:00Z")
                .lastReadAt("2024-01-02T00:00:00Z")
                .url(API_URL + "/notifications/threads/1")
                .subscriptionUrl(API_URL + "/notifications/threads/1/subscription")
                .build();
    }

    static GitHubThreadSubject threadSubject() {
        return GitHubThreadSubjectBuilder.builder()
                .title("Greetings")
                .url(API_URL + "/issues/1")
                .latestCommentUrl(API_URL + "/issues/comments/1")
                .type("Issue")
                .build();
    }

    static GitHubThreadSubscription threadSubscription() {
        return GitHubThreadSubscriptionBuilder.builder()
                .subscribed(true)
                .ignored(false)
                .reason("subscribed")
                .createdAt(SINCE)
                .url(API_URL + "/notifications/threads/1/subscription")
                .threadUrl(API_URL + "/notifications/threads/1")
                .repositoryUrl(API_URL)
                .build();
    }

    static GitHubStargazer stargazer() {
        return GitHubStargazerBuilder.builder()
                .starredAt(SINCE)
                .user(UsersCatalogFixtures.octocat())
                .build();
    }

    static GitHubStarredRepository starredRepository() {
        return GitHubStarredRepositoryBuilder.builder()
                .starredAt(SINCE)
                .repo(RepositoriesCatalogFixtures.teamEnvironmentRepository())
                .build();
    }

    static GitHubWatchEvent watchEvent() {
        return GitHubWatchEventBuilder.builder().action("started").build();
    }

    static GitHubPushEvent pushEvent() {
        return GitHubPushEventBuilder.builder()
                .repositoryId(1_296_269)
                .pushId(10_115_855_396L)
                .ref("refs/heads/master")
                .head("ffac537e6cbbf934b08745a378932722df287a53")
                .before("1cd26a7e9f3d7bd7a1d4ba5d2c52ffe9f0c1a0e8")
                .size(1)
                .distinctSize(1)
                .commits(List.of(pushEventCommit()))
                .build();
    }

    static GitHubPushEventCommit pushEventCommit() {
        return GitHubPushEventCommitBuilder.builder()
                .sha("ffac537e6cbbf934b08745a378932722df287a53")
                .message("Fix all the bugs")
                .author(pushEventCommitAuthor())
                .url(API_URL + "/commits/ffac537e6cbbf934b08745a378932722df287a53")
                .distinct(true)
                .build();
    }

    static GitHubPushEventCommitAuthor pushEventCommitAuthor() {
        return GitHubPushEventCommitAuthorBuilder.builder()
                .name("Monalisa Octocat")
                .email("octocat@github.com")
                .build();
    }

    static GitHubCreateEvent createEvent() {
        return GitHubCreateEventBuilder.builder()
                .ref("main")
                .refType("branch")
                .fullRef("refs/heads/main")
                .masterBranch("main")
                .description("Hello World")
                .pusherType("user")
                .build();
    }

    static GitHubDeleteEvent deleteEvent() {
        return GitHubDeleteEventBuilder.builder()
                .ref("feature")
                .refType("branch")
                .fullRef("refs/heads/feature")
                .pusherType("user")
                .build();
    }

    static GitHubMemberEvent memberEvent() {
        return GitHubMemberEventBuilder.builder()
                .action("added")
                .member(UsersCatalogFixtures.octocat())
                .build();
    }

    static GitHubIssuesEvent issuesEvent() {
        return GitHubIssuesEventBuilder.builder()
                .action("assigned")
                .issue(IssuesCatalogFixtures.issue())
                .assignee(UsersCatalogFixtures.octocat())
                .assignees(List.of(UsersCatalogFixtures.octocat()))
                .label(IssuesCatalogFixtures.label())
                .labels(List.of(IssuesCatalogFixtures.label()))
                .build();
    }

    static GitHubIssueCommentEvent issueCommentEvent() {
        return GitHubIssueCommentEventBuilder.builder()
                .action("created")
                .issue(IssuesCatalogFixtures.issue())
                .comment(IssuesCatalogFixtures.issueComment())
                .build();
    }

    static GitHubPullRequestEvent pullRequestEvent() {
        return GitHubPullRequestEventBuilder.builder()
                .action("assigned")
                .number(1)
                .pullRequest(pullRequestEventPullRequest())
                .assignee(UsersCatalogFixtures.octocat())
                .assignees(List.of(UsersCatalogFixtures.octocat()))
                .label(IssuesCatalogFixtures.label())
                .labels(List.of(IssuesCatalogFixtures.label()))
                .build();
    }

    static GitHubGollumEvent gollumEvent() {
        return GitHubGollumEventBuilder.builder()
                .pages(List.of(gollumEventPage()))
                .build();
    }

    static GitHubGollumEventPage gollumEventPage() {
        return GitHubGollumEventPageBuilder.builder()
                .pageName("Home")
                .title("Home")
                .summary("Updated the home page")
                .action("created")
                .sha("45dc6a4f4bd4b4b1dd7a7bc2d1c4ac6b0c1d2e3f")
                .htmlUrl("https://github.com/octocat/Hello-World/wiki/Home")
                .build();
    }

    static GitHubCommitCommentEvent commitCommentEvent() {
        return GitHubCommitCommentEventBuilder.builder()
                .action("created")
                .comment(commitCommentEventComment())
                .build();
    }

    static GitHubCommitCommentEventComment commitCommentEventComment() {
        return GitHubCommitCommentEventCommentBuilder.builder()
                .htmlUrl("https://github.com/octocat/Hello-World/commit/6dcb09b#commitcomment-1")
                .url(API_URL + "/comments/1")
                .id(1L)
                .nodeId("MDEzOkNvbW1pdENvbW1lbnQx")
                .body("Great stuff!")
                .path("README.md")
                .position(4)
                .line(14)
                .commitId("6dcb09b5b57875f334f61aebed695e2e4193db5e")
                .user(UsersCatalogFixtures.octocat())
                .createdAt(SINCE)
                .updatedAt(BEFORE)
                .reactions(IssuesCatalogFixtures.issueReactions())
                .authorAssociation(GitHubAuthorAssociation.OWNER)
                .build();
    }

    static GitHubDiscussionEvent discussionEvent() {
        return GitHubDiscussionEventBuilder.builder()
                .action("action")
                .discussion(discussion())
                .build();
    }

    static GitHubDiscussion discussion() {
        return GitHubDiscussionBuilder.builder()
                .activeLockReason("active_lock_reason")
                .answerChosenAt("answer_chosen_at")
                .answerChosenBy(discussionAnswerChosenBy())
                .answerHtmlUrl("answer_html_url")
                .authorAssociation(GitHubDiscussionAuthorAssociation.COLLABORATOR)
                .body("body")
                .category(discussionCategory())
                .comments(1)
                .createdAt(SINCE)
                .htmlUrl("html_url")
                .id(1L)
                .locked(true)
                .nodeId("node_id")
                .number(1)
                .reactions(discussionReactions())
                .repositoryUrl("repository_url")
                .state(GitHubDiscussionState.OPEN)
                .stateReason(GitHubDiscussionStateReason.RESOLVED)
                .timelineUrl("timeline_url")
                .title("title")
                .updatedAt(SINCE)
                .user(discussionUser())
                .labels(List.of(IssuesCatalogFixtures.label()))
                .build();
    }

    static GitHubDiscussionCategory discussionCategory() {
        return GitHubDiscussionCategoryBuilder.builder()
                .createdAt(SINCE)
                .description("description")
                .emoji("emoji")
                .id(1L)
                .answerable(true)
                .name("name")
                .nodeId("node_id")
                .repositoryId(1L)
                .slug("slug")
                .updatedAt("updated_at")
                .build();
    }

    static GitHubDiscussionUser discussionUser() {
        return GitHubDiscussionUserBuilder.builder()
                .avatarUrl("avatar_url")
                .deleted(true)
                .email("email")
                .eventsUrl("events_url")
                .followersUrl("followers_url")
                .followingUrl("following_url")
                .gistsUrl("gists_url")
                .gravatarId("gravatar_id")
                .htmlUrl("html_url")
                .id(1L)
                .login("login")
                .name("name")
                .nodeId("node_id")
                .organizationsUrl("organizations_url")
                .receivedEventsUrl("received_events_url")
                .reposUrl("repos_url")
                .siteAdmin(true)
                .starredUrl("starred_url")
                .subscriptionsUrl("subscriptions_url")
                .type(GitHubDiscussionUserType.BOT)
                .url("url")
                .userViewType("user_view_type")
                .build();
    }

    static GitHubDiscussionAnswerChosenBy discussionAnswerChosenBy() {
        return GitHubDiscussionAnswerChosenByBuilder.builder()
                .avatarUrl("avatar_url")
                .deleted(true)
                .email("email")
                .eventsUrl("events_url")
                .followersUrl("followers_url")
                .followingUrl("following_url")
                .gistsUrl("gists_url")
                .gravatarId("gravatar_id")
                .htmlUrl("html_url")
                .id(1L)
                .login("login")
                .name("name")
                .nodeId("node_id")
                .organizationsUrl("organizations_url")
                .receivedEventsUrl("received_events_url")
                .reposUrl("repos_url")
                .siteAdmin(true)
                .starredUrl("starred_url")
                .subscriptionsUrl("subscriptions_url")
                .type(GitHubDiscussionAnswerChosenByType.BOT)
                .url("url")
                .userViewType("user_view_type")
                .build();
    }

    static GitHubDiscussionReactions discussionReactions() {
        return GitHubDiscussionReactionsBuilder.builder()
                .plusOne(1)
                .minusOne(1)
                .confused(1)
                .eyes(1)
                .heart(1)
                .hooray(1)
                .laugh(1)
                .rocket(1)
                .totalCount(1)
                .url("url")
                .build();
    }

    static GitHubForkEvent forkEvent() {
        return GitHubForkEventBuilder.builder()
                .action("action")
                .forkee(forkEventForkee())
                .build();
    }

    static GitHubForkEventForkee forkEventForkee() {
        return GitHubForkEventForkeeBuilder.builder()
                .id(1L)
                .nodeId("node_id")
                .name("name")
                .fullName("full_name")
                .isPrivate(true)
                .owner(UsersCatalogFixtures.octocat())
                .htmlUrl("html_url")
                .description("description")
                .fork(true)
                .url("url")
                .forksUrl("forks_url")
                .keysUrl("keys_url")
                .collaboratorsUrl("collaborators_url")
                .teamsUrl("teams_url")
                .hooksUrl("hooks_url")
                .issueEventsUrl("issue_events_url")
                .eventsUrl("events_url")
                .assigneesUrl("assignees_url")
                .branchesUrl("branches_url")
                .tagsUrl("tags_url")
                .blobsUrl("blobs_url")
                .gitTagsUrl("git_tags_url")
                .gitRefsUrl("git_refs_url")
                .treesUrl("trees_url")
                .statusesUrl("statuses_url")
                .languagesUrl("languages_url")
                .stargazersUrl("stargazers_url")
                .contributorsUrl("contributors_url")
                .subscribersUrl("subscribers_url")
                .subscriptionUrl("subscription_url")
                .commitsUrl("commits_url")
                .gitCommitsUrl("git_commits_url")
                .commentsUrl("comments_url")
                .issueCommentUrl("issue_comment_url")
                .contentsUrl("contents_url")
                .compareUrl("compare_url")
                .mergesUrl("merges_url")
                .archiveUrl("archive_url")
                .downloadsUrl("downloads_url")
                .issuesUrl("issues_url")
                .pullsUrl("pulls_url")
                .milestonesUrl("milestones_url")
                .notificationsUrl("notifications_url")
                .labelsUrl("labels_url")
                .releasesUrl("releases_url")
                .deploymentsUrl("deployments_url")
                .createdAt(SINCE)
                .updatedAt(SINCE)
                .pushedAt(SINCE)
                .gitUrl("git_url")
                .sshUrl("ssh_url")
                .cloneUrl("clone_url")
                .svnUrl("svn_url")
                .homepage("homepage")
                .size(1)
                .stargazersCount(1)
                .watchersCount(1)
                .language("language")
                .hasIssues(true)
                .hasProjects(true)
                .hasDownloads(true)
                .hasWiki(true)
                .hasPages(true)
                .hasDiscussions(true)
                .hasPullRequests(true)
                .pullRequestCreationPolicy(GitHubForkEventForkeePullRequestCreationPolicy.ALL)
                .forksCount(1)
                .mirrorUrl("mirror_url")
                .archived(true)
                .disabled(true)
                .openIssuesCount(1)
                .license(RepositoriesCatalogFixtures.mitLicenseWithHtmlUrl())
                .allowForking(true)
                .isTemplate(true)
                .webCommitSignoffRequired(true)
                .topics(List.of("topics"))
                .visibility("visibility")
                .forks(1)
                .openIssues(1)
                .watchers(1)
                .defaultBranch("default_branch")
                .isPublic(true)
                .build();
    }

    static GitHubPullRequestReviewEvent pullRequestReviewEvent() {
        return GitHubPullRequestReviewEventBuilder.builder()
                .action("action")
                .review(pullRequestReviewEventReview())
                .pullRequest(pullRequestEventPullRequest())
                .build();
    }

    static GitHubPullRequestReviewEventReview pullRequestReviewEventReview() {
        return GitHubPullRequestReviewEventReviewBuilder.builder()
                .id(1L)
                .nodeId("node_id")
                .user(UsersCatalogFixtures.octocat())
                .body("body")
                .commitId("commit_id")
                .submittedAt("submitted_at")
                .state("state")
                .htmlUrl("html_url")
                .pullRequestUrl("pull_request_url")
                .links(pullRequestReviewEventReviewLinks())
                .updatedAt("updated_at")
                .authorAssociation(GitHubAuthorAssociation.OWNER)
                .build();
    }

    static GitHubPullRequestReviewEventReviewLinks pullRequestReviewEventReviewLinks() {
        return GitHubPullRequestReviewEventReviewLinksBuilder.builder()
                .html(pullRequestReviewEventReviewLinksHtml())
                .pullRequest(pullRequestReviewEventReviewLinksPullRequest())
                .build();
    }

    static GitHubPullRequestReviewEventReviewLinksHtml pullRequestReviewEventReviewLinksHtml() {
        return GitHubPullRequestReviewEventReviewLinksHtmlBuilder.builder()
                .href("href")
                .build();
    }

    static GitHubPullRequestReviewEventReviewLinksPullRequest pullRequestReviewEventReviewLinksPullRequest() {
        return GitHubPullRequestReviewEventReviewLinksPullRequestBuilder.builder()
                .href("href")
                .build();
    }

    static GitHubPullRequestReviewCommentEvent pullRequestReviewCommentEvent() {
        return GitHubPullRequestReviewCommentEventBuilder.builder()
                .action("action")
                .pullRequest(pullRequestEventPullRequest())
                .comment(pullRequestReviewCommentEventComment())
                .build();
    }

    static GitHubPullRequestReviewCommentEventComment pullRequestReviewCommentEventComment() {
        return GitHubPullRequestReviewCommentEventCommentBuilder.builder()
                .id(1L)
                .nodeId("node_id")
                .url("url")
                .pullRequestReviewId(1L)
                .diffHunk("diff_hunk")
                .path("path")
                .position(1)
                .originalPosition(1)
                .subjectType("subject_type")
                .commitId("commit_id")
                .user(pullRequestReviewCommentEventCommentUser())
                .body("body")
                .createdAt(SINCE)
                .updatedAt(SINCE)
                .htmlUrl("html_url")
                .pullRequestUrl("pull_request_url")
                .links(pullRequestReviewCommentEventCommentLinks())
                .originalCommitId("original_commit_id")
                .reactions(pullRequestReviewCommentEventCommentReactions())
                .inReplyToId(1L)
                .authorAssociation("OWNER")
                .startLine(1)
                .originalStartLine(1)
                .startSide("RIGHT")
                .line(1)
                .originalLine(1)
                .side("RIGHT")
                .build();
    }

    static GitHubPullRequestReviewCommentEventCommentLinks pullRequestReviewCommentEventCommentLinks() {
        return GitHubPullRequestReviewCommentEventCommentLinksBuilder.builder()
                .self(pullRequestReviewCommentEventCommentLinksSelf())
                .html(pullRequestReviewCommentEventCommentLinksHtml())
                .pullRequest(pullRequestReviewCommentEventCommentLinksPullRequest())
                .build();
    }

    static GitHubPullRequestReviewCommentEventCommentLinksSelf pullRequestReviewCommentEventCommentLinksSelf() {
        return GitHubPullRequestReviewCommentEventCommentLinksSelfBuilder.builder()
                .href("href")
                .build();
    }

    static GitHubPullRequestReviewCommentEventCommentLinksHtml pullRequestReviewCommentEventCommentLinksHtml() {
        return GitHubPullRequestReviewCommentEventCommentLinksHtmlBuilder.builder()
                .href("href")
                .build();
    }

    static GitHubPullRequestReviewCommentEventCommentLinksPullRequest
            pullRequestReviewCommentEventCommentLinksPullRequest() {
        return GitHubPullRequestReviewCommentEventCommentLinksPullRequestBuilder.builder()
                .href("href")
                .build();
    }

    static GitHubPullRequestReviewCommentEventCommentUser pullRequestReviewCommentEventCommentUser() {
        return GitHubPullRequestReviewCommentEventCommentUserBuilder.builder()
                .avatarUrl("avatar_url")
                .deleted(true)
                .email("email")
                .eventsUrl("events_url")
                .followersUrl("followers_url")
                .followingUrl("following_url")
                .gistsUrl("gists_url")
                .gravatarId("gravatar_id")
                .htmlUrl("html_url")
                .id(1L)
                .login("login")
                .name("name")
                .nodeId("node_id")
                .organizationsUrl("organizations_url")
                .receivedEventsUrl("received_events_url")
                .reposUrl("repos_url")
                .siteAdmin(true)
                .starredUrl("starred_url")
                .subscriptionsUrl("subscriptions_url")
                .type(GitHubPullRequestReviewCommentEventCommentUserType.BOT)
                .url("url")
                .userViewType("user_view_type")
                .build();
    }

    static GitHubPullRequestReviewCommentEventCommentReactions pullRequestReviewCommentEventCommentReactions() {
        return GitHubPullRequestReviewCommentEventCommentReactionsBuilder.builder()
                .plusOne(1)
                .minusOne(1)
                .confused(1)
                .eyes(1)
                .heart(1)
                .hooray(1)
                .laugh(1)
                .rocket(1)
                .totalCount(1)
                .url("url")
                .build();
    }

    static GitHubReleaseEvent releaseEvent() {
        return GitHubReleaseEventBuilder.builder()
                .action("action")
                .release(releaseEventRelease())
                .build();
    }

    static GitHubReleaseEventRelease releaseEventRelease() {
        return GitHubReleaseEventReleaseBuilder.builder()
                .url("url")
                .htmlUrl("html_url")
                .assetsUrl("assets_url")
                .uploadUrl("upload_url")
                .tarballUrl("tarball_url")
                .zipballUrl("zipball_url")
                .id(1L)
                .nodeId("node_id")
                .tagName("tag_name")
                .targetCommitish("target_commitish")
                .name("name")
                .body("body")
                .draft(true)
                .prerelease(true)
                .immutable(true)
                .createdAt(SINCE)
                .publishedAt(SINCE)
                .updatedAt(SINCE)
                .author(UsersCatalogFixtures.octocat())
                .assets(List.of(ReleasesCatalogFixtures.releaseAsset()))
                .bodyHtml("body_html")
                .bodyText("body_text")
                .mentionsCount(1)
                .discussionUrl("discussion_url")
                .reactions(IssuesCatalogFixtures.issueReactions())
                .isShortDescriptionHtmlTruncated(true)
                .shortDescriptionHtml("short_description_html")
                .build();
    }

    static GitHubEvent event() {
        return GitHubEventBuilder.builder()
                .id("id")
                .type("PushEvent")
                .actor(actor())
                .repo(eventRepo())
                .org(actor())
                .payload(pushEvent())
                .isPublic(true)
                .createdAt(SINCE)
                .build();
    }

    static GitHubEventRepo eventRepo() {
        return GitHubEventRepoBuilder.builder().id(1L).name("name").url("url").build();
    }

    static GitHubPublicEvent publicEvent() {
        return new GitHubPublicEvent();
    }

    static GitHubPullRequestEventPullRequest pullRequestEventPullRequest() {
        final GitHubPullRequest pullRequest = PullrequestsCatalogFixtures.pullRequest();
        return GitHubPullRequestEventPullRequestBuilder.builder()
                .url(pullRequest.url())
                .id(pullRequest.id())
                .nodeId(pullRequest.nodeId())
                .htmlUrl(pullRequest.htmlUrl())
                .diffUrl(pullRequest.diffUrl())
                .patchUrl(pullRequest.patchUrl())
                .issueUrl(pullRequest.issueUrl())
                .commitsUrl(pullRequest.commitsUrl())
                .reviewCommentsUrl(pullRequest.reviewCommentsUrl())
                .reviewCommentUrl(pullRequest.reviewCommentUrl())
                .commentsUrl(pullRequest.commentsUrl())
                .statusesUrl(pullRequest.statusesUrl())
                .number(pullRequest.number())
                .state(pullRequest.state())
                .locked(pullRequest.locked())
                .title(pullRequest.title())
                .user(pullRequest.user())
                .body(pullRequest.body())
                .labels(pullRequest.labels())
                .milestone(pullRequest.milestone())
                .activeLockReason(pullRequest.activeLockReason())
                .createdAt(pullRequest.createdAt())
                .updatedAt(pullRequest.updatedAt())
                .closedAt(pullRequest.closedAt())
                .mergedAt(pullRequest.mergedAt())
                .assignees(pullRequest.assignees())
                .requestedReviewers(pullRequest.requestedReviewers())
                .requestedTeams(pullRequest.requestedTeams())
                .head(pullRequestEventPullRequestRef(pullRequest.head()))
                .base(pullRequestEventPullRequestRef(pullRequest.base()))
                .links(pullRequest.links())
                .authorAssociation(pullRequest.authorAssociation())
                .autoMerge(pullRequest.autoMerge())
                .stack(pullRequest.stack())
                .draft(pullRequest.draft())
                .merged(pullRequest.merged())
                .mergeable(pullRequest.mergeable())
                .rebaseable(pullRequest.rebaseable())
                .mergeableState(pullRequest.mergeableState())
                .mergedBy(pullRequest.mergedBy())
                .comments(pullRequest.comments())
                .reviewComments(pullRequest.reviewComments())
                .maintainerCanModify(pullRequest.maintainerCanModify())
                .commits(pullRequest.commits())
                .additions(pullRequest.additions())
                .deletions(pullRequest.deletions())
                .changedFiles(pullRequest.changedFiles())
                .build();
    }

    static GitHubPullRequestEventPullRequestRef pullRequestEventPullRequestRef() {
        return pullRequestEventPullRequestRef(
                PullrequestsCatalogFixtures.pullRequest().head());
    }

    private static GitHubPullRequestEventPullRequestRef pullRequestEventPullRequestRef(
            final GitHubPullRequestBranchRef ref) {
        return GitHubPullRequestEventPullRequestRefBuilder.builder()
                .label(ref.label())
                .ref(ref.ref())
                .repo(pullRequestEventPullRequestRepo(ref.repo()))
                .sha(ref.sha())
                .user(ref.user())
                .build();
    }

    static GitHubPullRequestEventPullRequestRepo pullRequestEventPullRequestRepo() {
        return pullRequestEventPullRequestRepo(
                PullrequestsCatalogFixtures.pullRequest().head().repo());
    }

    private static GitHubPullRequestEventPullRequestRepo pullRequestEventPullRequestRepo(
            final GitHubRepository repository) {
        return GitHubPullRequestEventPullRequestRepoBuilder.builder()
                .id(repository.id())
                .nodeId(repository.nodeId())
                .name(repository.name())
                .fullName(repository.fullName())
                .license(repository.license())
                .forks(repository.forks())
                .permissions(repository.permissions())
                .owner(repository.owner())
                .isPrivate(repository.isPrivate())
                .htmlUrl(repository.htmlUrl())
                .description(repository.description())
                .fork(repository.fork())
                .url(repository.url())
                .archiveUrl(repository.archiveUrl())
                .assigneesUrl(repository.assigneesUrl())
                .blobsUrl(repository.blobsUrl())
                .branchesUrl(repository.branchesUrl())
                .collaboratorsUrl(repository.collaboratorsUrl())
                .commentsUrl(repository.commentsUrl())
                .commitsUrl(repository.commitsUrl())
                .compareUrl(repository.compareUrl())
                .contentsUrl(repository.contentsUrl())
                .contributorsUrl(repository.contributorsUrl())
                .deploymentsUrl(repository.deploymentsUrl())
                .downloadsUrl(repository.downloadsUrl())
                .eventsUrl(repository.eventsUrl())
                .forksUrl(repository.forksUrl())
                .gitCommitsUrl(repository.gitCommitsUrl())
                .gitRefsUrl(repository.gitRefsUrl())
                .gitTagsUrl(repository.gitTagsUrl())
                .gitUrl(repository.gitUrl())
                .issueCommentUrl(repository.issueCommentUrl())
                .issueEventsUrl(repository.issueEventsUrl())
                .issuesUrl(repository.issuesUrl())
                .keysUrl(repository.keysUrl())
                .labelsUrl(repository.labelsUrl())
                .languagesUrl(repository.languagesUrl())
                .mergesUrl(repository.mergesUrl())
                .milestonesUrl(repository.milestonesUrl())
                .notificationsUrl(repository.notificationsUrl())
                .pullsUrl(repository.pullsUrl())
                .releasesUrl(repository.releasesUrl())
                .sshUrl(repository.sshUrl())
                .stargazersUrl(repository.stargazersUrl())
                .statusesUrl(repository.statusesUrl())
                .subscribersUrl(repository.subscribersUrl())
                .subscriptionUrl(repository.subscriptionUrl())
                .tagsUrl(repository.tagsUrl())
                .teamsUrl(repository.teamsUrl())
                .treesUrl(repository.treesUrl())
                .cloneUrl(repository.cloneUrl())
                .mirrorUrl(repository.mirrorUrl())
                .hooksUrl(repository.hooksUrl())
                .svnUrl(repository.svnUrl())
                .homepage(repository.homepage())
                .language(repository.language())
                .forksCount(repository.forksCount())
                .stargazersCount(repository.stargazersCount())
                .watchersCount(repository.watchersCount())
                .size(repository.size())
                .defaultBranch(repository.defaultBranch())
                .openIssuesCount(repository.openIssuesCount())
                .isTemplate(repository.isTemplate())
                .topics(repository.topics())
                .hasIssues(repository.hasIssues())
                .hasProjects(repository.hasProjects())
                .hasWiki(repository.hasWiki())
                .hasPages(repository.hasPages())
                .hasDiscussions(repository.hasDiscussions())
                .hasPullRequests(repository.hasPullRequests())
                .pullRequestCreationPolicy(repository.pullRequestCreationPolicy())
                .archived(repository.archived())
                .disabled(repository.disabled())
                .visibility(repository.visibility())
                .pushedAt(repository.pushedAt())
                .createdAt(repository.createdAt())
                .updatedAt(repository.updatedAt())
                .allowRebaseMerge(repository.allowRebaseMerge())
                .tempCloneToken(repository.tempCloneToken())
                .allowSquashMerge(repository.allowSquashMerge())
                .allowAutoMerge(repository.allowAutoMerge())
                .deleteBranchOnMerge(repository.deleteBranchOnMerge())
                .allowUpdateBranch(repository.allowUpdateBranch())
                .squashMergeCommitTitle(repository.squashMergeCommitTitle())
                .squashMergeCommitMessage(repository.squashMergeCommitMessage())
                .mergeCommitTitle(repository.mergeCommitTitle())
                .mergeCommitMessage(repository.mergeCommitMessage())
                .allowMergeCommit(repository.allowMergeCommit())
                .allowForking(repository.allowForking())
                .webCommitSignoffRequired(repository.webCommitSignoffRequired())
                .openIssues(repository.openIssues())
                .watchers(repository.watchers())
                .starredAt(repository.starredAt())
                .anonymousAccessEnabled(repository.anonymousAccessEnabled())
                .codeSearchIndexStatus(repository.codeSearchIndexStatus())
                .build();
    }
}
