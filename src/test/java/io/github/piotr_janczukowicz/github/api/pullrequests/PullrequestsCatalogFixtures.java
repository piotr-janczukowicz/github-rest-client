package io.github.piotr_janczukowicz.github.api.pullrequests;

import io.github.piotr_janczukowicz.github.api.licenses.GitHubLicenseSimple;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubAutoMerge;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubAutoMergeMethod;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCodeSearchIndexStatus;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubDiffEntry;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubDiffEntryStatus;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubLink;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMergeCommitMessage;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMergeCommitTitle;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestBranchRef;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestCreationPolicy;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestLabel;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestSimpleLinks;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryPermissions;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSquashMergeCommitMessage;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSquashMergeCommitTitle;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.search.GitHubMilestone;
import io.github.piotr_janczukowicz.github.api.search.GitHubMilestoneState;
import io.github.piotr_janczukowicz.github.api.search.GitHubReactionRollup;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamOwnershipType;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamSimple;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.json.OptionalNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PullrequestsCatalogFixtures {

    private PullrequestsCatalogFixtures() {}

    @SafeVarargs
    private static <T> List<T> recordList(final T... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    static GitHubPullRequestLabelItem pullRequestLabelItem() {
        return new GitHubPullRequestLabelItem(
                208_045_946L,
                "MDU6TGFiZWwyMDgwNDU5NDY=",
                "https://api.github.com/repos/octocat/Hello-World/labels/bug",
                "bug",
                OptionalNull.of("Something isn't working"),
                "f29513",
                true,
                Instant.parse("2023-01-01T12:00:00Z"),
                UsersCatalogFixtures.octocat());
    }

    public static GitHubPullRequest pullRequest() {
        return new GitHubPullRequest(
                "https://api.github.com/repos/octocat/Hello-World/pulls/1347",
                1,
                "MDExOlB1bGxSZXF1ZXN0MQ==",
                "https://github.com/octocat/Hello-World/pull/1347",
                "https://github.com/octocat/Hello-World/pull/1347.diff",
                "https://github.com/octocat/Hello-World/pull/1347.patch",
                "https://api.github.com/repos/octocat/Hello-World/issues/1347",
                "https://api.github.com/repos/octocat/Hello-World/pulls/1347/commits",
                "https://api.github.com/repos/octocat/Hello-World/pulls/1347/comments",
                "https://api.github.com/repos/octocat/Hello-World/pulls/comments{/number}",
                "https://api.github.com/repos/octocat/Hello-World/issues/1347/comments",
                "https://api.github.com/repos/octocat/Hello-World/statuses/6dcb09b5b57875f334f61aebed695e2e4193db5e",
                1347,
                GitHubPullRequestUpdateState.OPEN,
                false,
                "new-feature",
                UsersCatalogFixtures.octocat(),
                "Please pull these awesome changes",
                recordList(new GitHubPullRequestLabel(
                        208_045_946,
                        "MDU6TGFiZWwyMDgwNDU5NDY=",
                        "https://api.github.com/repos/octocat/Hello-World/labels/bug",
                        "bug",
                        "Something isn't working",
                        "f29513",
                        true,
                        Instant.parse("2023-01-01T12:00:00Z"),
                        UsersCatalogFixtures.octocat())),
                OptionalNull.of(new GitHubMilestone(
                        "https://api.github.com/repos/octocat/Hello-World/milestones/1",
                        "https://github.com/octocat/Hello-World/milestone/1",
                        "https://api.github.com/repos/octocat/Hello-World/milestones/1/labels",
                        1_002_604,
                        "MDQ6TWlsZXN0b25lMTAwMjYwNA==",
                        1,
                        GitHubMilestoneState.OPEN,
                        "v1.0",
                        "Tracking milestone for version 1.0",
                        UsersCatalogFixtures.octocat(),
                        4,
                        8,
                        Instant.parse("2011-01-26T19:01:12Z"),
                        Instant.parse("2011-01-26T19:14:43Z"),
                        Instant.parse("2011-01-26T19:14:43Z"),
                        Instant.parse("2012-07-09T00:00:00Z"))),
                "resolved",
                Instant.parse("2011-01-26T19:01:12Z"),
                Instant.parse("2011-01-26T19:01:12Z"),
                OptionalNull.of(Instant.parse("2011-01-26T19:14:43Z")),
                OptionalNull.of(Instant.parse("2011-01-26T19:14:43Z")),
                recordList(UsersCatalogFixtures.octocat()),
                recordList(UsersCatalogFixtures.octocat()),
                recordList(new GitHubTeamSimple(
                        2,
                        "MDQ6VGVhbTI=",
                        "https://api.github.com/organizations/1/team/2",
                        "https://api.github.com/organizations/1/team/2/members{/member}",
                        "Parent Team",
                        "Parent team.",
                        "admin",
                        "closed",
                        "notifications_enabled",
                        "https://github.com/orgs/rails/teams/parent",
                        "https://api.github.com/organizations/1/team/2/repos",
                        "parent-team",
                        "cn=parent-team,ou=groups,dc=github,dc=com",
                        GitHubTeamOwnershipType.ORGANIZATION,
                        1L,
                        2L)),
                new GitHubPullRequestBranchRef(
                        "octocat:new-topic",
                        "new-topic",
                        helloWorldRepository(),
                        "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                        OptionalNull.of(UsersCatalogFixtures.octocat())),
                new GitHubPullRequestBranchRef(
                        "octocat:master",
                        "master",
                        helloWorldRepository(),
                        "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                        OptionalNull.of(UsersCatalogFixtures.octocat())),
                new GitHubPullRequestSimpleLinks(
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/issues/1347/comments"),
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/1347/commits"),
                        new GitHubLink(
                                "https://api.github.com/repos/octocat/Hello-World/statuses/6dcb09b5b57875f334f61aebed695e2e4193db5e"),
                        new GitHubLink("https://github.com/octocat/Hello-World/pull/1347"),
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/issues/1347"),
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/1347/comments"),
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/comments{/number}"),
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/1347")),
                GitHubAuthorAssociation.OWNER,
                OptionalNull.of(new GitHubAutoMerge(
                        UsersCatalogFixtures.octocat(),
                        GitHubAutoMergeMethod.SQUASH,
                        "Amazing pull request",
                        "Merge pull request")),
                new GitHubPullRequestStack(
                        new GitHubPullRequestStackBase("master", "6dcb09b5b57875f334f61aebed695e2e4193db5e"),
                        2,
                        1,
                        10L,
                        100),
                false,
                false,
                OptionalNull.of(true),
                true,
                "clean",
                OptionalNull.of(UsersCatalogFixtures.octocat()),
                10,
                0,
                true,
                3,
                100,
                3,
                5);
    }

    public static GitHubPullRequestMinimal pullRequestMinimal() {
        return new GitHubPullRequestMinimal(
                100_001,
                101,
                "https://api.github.com/repos/octocat/hello-world/pulls/101",
                new GitHubPullRequestMinimalRef(
                        "user-model",
                        "aaa1111aaa1111aaa1111aaa1111aaa1111aaa11",
                        new GitHubPullRequestMinimalRepo(
                                1_296_269, "https://api.github.com/repos/octocat/hello-world", "hello-world")),
                new GitHubPullRequestMinimalRef(
                        "main",
                        "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                        new GitHubPullRequestMinimalRepo(
                                1_296_269, "https://api.github.com/repos/octocat/hello-world", "hello-world")));
    }

    static GitHubPullRequestMinimalRef pullRequestMinimalRef() {
        return new GitHubPullRequestMinimalRef(
                "user-model",
                "aaa1111aaa1111aaa1111aaa1111aaa1111aaa11",
                new GitHubPullRequestMinimalRepo(
                        1_296_269, "https://api.github.com/repos/octocat/hello-world", "hello-world"));
    }

    static GitHubPullRequestMinimalRepo pullRequestMinimalRepo() {
        return new GitHubPullRequestMinimalRepo(
                1_296_269, "https://api.github.com/repos/octocat/hello-world", "hello-world");
    }

    static GitHubPullRequestReview pullRequestReview() {
        return new GitHubPullRequestReview(
                80,
                "MDE3OlB1bGxSZXF1ZXN0UmV2aWV3ODA=",
                UsersCatalogFixtures.octocat(),
                "This looks great.",
                "APPROVED",
                "https://github.com/octocat/Hello-World/pull/12#pullrequestreview-80",
                "https://api.github.com/repos/octocat/Hello-World/pulls/12",
                new GitHubPullRequestReviewLinks(
                        new GitHubLink("https://github.com/octocat/Hello-World/pull/12#pullrequestreview-80"),
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/12")),
                Instant.parse("2019-03-21T01:03:24Z"),
                "54bb654c9e6025347f57900a4a5c2313a96b8035",
                "<p>This looks great.</p>",
                "This looks great.",
                GitHubAuthorAssociation.OWNER);
    }

    static GitHubPullRequestReview pullRequestReviewFromListStub() {
        return new GitHubPullRequestReview(
                80,
                "MDE3OlB1bGxSZXF1ZXN0UmV2aWV3ODA=",
                null,
                "This looks great.",
                "APPROVED",
                "https://github.com/octocat/Hello-World/pull/12#pullrequestreview-80",
                "https://api.github.com/repos/octocat/Hello-World/pulls/12",
                new GitHubPullRequestReviewLinks(
                        new GitHubLink("https://github.com/octocat/Hello-World/pull/12#pullrequestreview-80"),
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/12")),
                Instant.parse("2019-03-21T01:03:24Z"),
                "54bb654c9e6025347f57900a4a5c2313a96b8035",
                null,
                null,
                GitHubAuthorAssociation.OWNER);
    }

    static GitHubPullRequestReviewLinks pullRequestReviewLinks() {
        return new GitHubPullRequestReviewLinks(
                new GitHubLink("https://github.com/octocat/Hello-World/pull/12#pullrequestreview-80"),
                new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/12"));
    }

    static GitHubPullRequestReviewRequest pullRequestReviewRequest() {
        return new GitHubPullRequestReviewRequest(
                recordList(UsersCatalogFixtures.octocat()), recordList(RepositoriesCatalogFixtures.requestedTeam()));
    }

    static GitHubPullRequestReviewComment pullRequestReviewComment() {
        return new GitHubPullRequestReviewComment(
                "https://api.github.com/repos/octocat/Hello-World/pulls/comments/1",
                42L,
                1,
                "MDI0OlB1bGxSZXF1ZXN0UmV2aWV3Q29tbWVudDEw",
                "@@ -16,33 +16,40 @@ public class Connection : IConnection...",
                "config/database.yaml",
                1,
                4,
                "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "9c48853fa3dc5c1c3d6f1f1cd1f2743e72652840",
                2L,
                OptionalNull.of(UsersCatalogFixtures.octocat()),
                "We should probably include a check for null values here.",
                Instant.parse("2011-04-14T16:00:49Z"),
                Instant.parse("2011-04-14T16:00:49Z"),
                "https://github.com/octocat/Hello-World/pull/1#discussion-diff-1",
                "https://api.github.com/repos/octocat/Hello-World/pulls/1",
                GitHubAuthorAssociation.NONE,
                new GitHubPullRequestReviewCommentLinks(
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/comments/1"),
                        new GitHubLink("https://github.com/octocat/Hello-World/pull/1#discussion-diff-1"),
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/1")),
                1,
                1,
                GitHubPullRequestReviewCommentSide.RIGHT,
                2,
                2,
                GitHubPullRequestReviewCommentSide.RIGHT,
                GitHubPullRequestReviewCommentSubjectType.LINE,
                new GitHubReactionRollup(
                        "https://api.github.com/repos/octocat/Spoon-Knife/issues/comments/42/reactions",
                        2,
                        1,
                        0,
                        0,
                        0,
                        1,
                        0,
                        0,
                        0),
                "<p>We should probably include a check for null values here.</p>",
                "We should probably include a check for null values here.");
    }

    static GitHubPullRequestReviewComment pullRequestReviewCommentMultiLine() {
        return new GitHubPullRequestReviewComment(
                "https://api.github.com/repos/octocat/Hello-World/pulls/comments/2",
                42L,
                2L,
                "MDI0OlB1bGxSZXF1ZXN0UmV2aWV3Q29tbWVudDIw",
                "@@ -1,2 +1,2 @@\n line one\n-line two\n+line two changed",
                "file1.txt",
                2,
                2,
                "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                null,
                OptionalNull.ofNull(),
                "Great stuff!",
                Instant.parse("2011-04-14T16:00:49Z"),
                Instant.parse("2011-04-14T16:00:49Z"),
                "https://github.com/octocat/Hello-World/pull/1#discussion-diff-2",
                "https://api.github.com/repos/octocat/Hello-World/pulls/1",
                GitHubAuthorAssociation.NONE,
                new GitHubPullRequestReviewCommentLinks(
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/comments/2"),
                        new GitHubLink("https://github.com/octocat/Hello-World/pull/1#discussion-diff-2"),
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/1")),
                1,
                null,
                GitHubPullRequestReviewCommentSide.RIGHT,
                2,
                null,
                GitHubPullRequestReviewCommentSide.RIGHT,
                null,
                null,
                null,
                null);
    }

    static GitHubPullRequestReviewComment pullRequestReviewCommentFromListStub() {
        return new GitHubPullRequestReviewComment(
                "https://api.github.com/repos/octocat/Hello-World/pulls/comments/1",
                42L,
                1,
                "MDI0OlB1bGxSZXF1ZXN0UmV2aWV3Q29tbWVudDEw",
                "@@ -16,33 +16,40 @@ public class Connection : IConnection...",
                "config/database.yaml",
                1,
                4,
                "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "9c48853fa3dc5c1c3d6f1f1cd1f2743e72652840",
                null,
                OptionalNull.ofNull(),
                "We should probably include a check for null values here.",
                Instant.parse("2011-04-14T16:00:49Z"),
                Instant.parse("2011-04-14T16:00:49Z"),
                "https://github.com/octocat/Hello-World/pull/1#discussion-diff-1",
                "https://api.github.com/repos/octocat/Hello-World/pulls/1",
                GitHubAuthorAssociation.NONE,
                new GitHubPullRequestReviewCommentLinks(
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/comments/1"),
                        new GitHubLink("https://github.com/octocat/Hello-World/pull/1#discussion-diff-1"),
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/1")),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    static GitHubPullRequestReviewCommentLinks pullRequestReviewCommentLinks() {
        return new GitHubPullRequestReviewCommentLinks(
                new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/comments/1"),
                new GitHubLink("https://github.com/octocat/Hello-World/pull/1#discussion-diff-1"),
                new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/1"));
    }

    static GitHubReviewComment reviewComment() {
        return new GitHubReviewComment(
                "https://api.github.com/repos/octocat/Hello-World/pulls/comments/10",
                42L,
                10,
                "MDI0OlB1bGxSZXF1ZXN0UmV2aWV3Q29tbWVudDEw",
                "@@ -16,33 +16,40 @@ public class Connection : IConnection...",
                "file1.txt",
                1,
                4,
                "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "9c48853fa3dc5c1c3d6f1f1cd1f2743e72652840",
                2L,
                OptionalNull.of(UsersCatalogFixtures.octocat()),
                "Great stuff",
                Instant.parse("2011-04-14T16:00:49Z"),
                Instant.parse("2011-04-14T16:00:49Z"),
                "https://github.com/octocat/Hello-World/pull/1#discussion-diff-1",
                "https://api.github.com/repos/octocat/Hello-World/pulls/1",
                GitHubAuthorAssociation.NONE,
                new GitHubPullRequestReviewCommentLinks(
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/comments/10"),
                        new GitHubLink("https://github.com/octocat/Hello-World/pull/1#discussion-diff-1"),
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/1")),
                "Great stuff",
                "<p>Great stuff</p>",
                new GitHubReactionRollup(
                        "https://api.github.com/repos/octocat/Spoon-Knife/issues/comments/42/reactions",
                        2,
                        1,
                        0,
                        0,
                        0,
                        1,
                        0,
                        0,
                        0),
                GitHubPullRequestReviewCommentSide.RIGHT,
                GitHubPullRequestReviewCommentStartSide.RIGHT,
                2,
                2,
                1,
                1,
                GitHubPullRequestReviewCommentSubjectType.LINE);
    }

    static GitHubPullRequestMergeResult pullRequestMergeResult() {
        return new GitHubPullRequestMergeResult(
                "6dcb09b5b57875f334f61aebed695e2e4193db5e", true, "Pull Request successfully merged");
    }

    static GitHubPullRequestMergeAsyncResult pullRequestMergeAsyncResultPending() {
        return new GitHubPullRequestMergeAsyncResult(
                GitHubPullRequestMergeAsyncStatus.PENDING,
                new GitHubPullRequestMergeAsyncResultDetailsAccepted(
                        "Merge request enqueued.",
                        "630b9d5e-3f2a-4f7e-8b0c-2d5f9a8c1e42",
                        GitHubPullRequestMergeAsyncMethod.MERGE,
                        GitHubPullRequestMergeAsyncAction.DEFAULT,
                        "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                        true));
    }

    static GitHubPullRequestMergeAsyncResultDetails pullRequestMergeAsyncResultDetails() {
        return new GitHubPullRequestMergeAsyncResultDetailsMerged(
                "Pull request is already merged.", "6dcb09b5b57875f334f61aebed695e2e4193db5e");
    }

    static GitHubPullRequestBranchUpdateResult pullRequestBranchUpdateResult() {
        return new GitHubPullRequestBranchUpdateResult(
                "Updating pull request branch.", "https://github.com/repos/octocat/Hello-World/pulls/53");
    }

    static GitHubPullRequestStack pullRequestStack() {
        return new GitHubPullRequestStack(
                new GitHubPullRequestStackBase("main", "6dcb09b5b57875f334f61aebed695e2e4193db5e"),
                3,
                2,
                9_876_543L,
                42);
    }

    static GitHubPullRequestStackBase pullRequestStackBase() {
        return new GitHubPullRequestStackBase("main", "6dcb09b5b57875f334f61aebed695e2e4193db5e");
    }

    static GitHubPullRequestStackRefBase pullRequestStackRefBase() {
        return new GitHubPullRequestStackRefBase("main");
    }

    static GitHubPullRequestStackDetail pullRequestStackDetail() {
        return new GitHubPullRequestStackDetail(
                9_876_543,
                42,
                "S_kwDOABCDEF4AAAAA",
                "https://api.github.com/repos/octocat/hello-world/stacks/42",
                new GitHubPullRequestStackRefBase("main"),
                true,
                Instant.parse("2026-04-15T10:00:00Z"),
                recordList(
                        new GitHubPullRequestStackPullRequest(
                                100_001,
                                101,
                                "https://api.github.com/repos/octocat/hello-world/pulls/101",
                                new GitHubPullRequestMinimalRef(
                                        "user-model",
                                        "aaa1111aaa1111aaa1111aaa1111aaa1111aaa11",
                                        new GitHubPullRequestMinimalRepo(
                                                1_296_269,
                                                "https://api.github.com/repos/octocat/hello-world",
                                                "hello-world")),
                                new GitHubPullRequestMinimalRef(
                                        "main",
                                        "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                                        new GitHubPullRequestMinimalRepo(
                                                1_296_269,
                                                "https://api.github.com/repos/octocat/hello-world",
                                                "hello-world")),
                                "PR_kwDOABCDEF4AAAAA",
                                "Add user model",
                                GitHubPullRequestStackState.OPEN,
                                Instant.parse("2022-04-28T12:00:00Z"),
                                false,
                                "https://github.com/octocat/hello-world/pull/101",
                                OptionalNull.of(UsersCatalogFixtures.octocat())),
                        new GitHubPullRequestStackPullRequest(
                                100_002,
                                102,
                                "https://api.github.com/repos/octocat/hello-world/pulls/102",
                                new GitHubPullRequestMinimalRef(
                                        "user-api",
                                        "bbb2222bbb2222bbb2222bbb2222bbb2222bbb22",
                                        new GitHubPullRequestMinimalRepo(
                                                1_296_269,
                                                "https://api.github.com/repos/octocat/hello-world",
                                                "hello-world")),
                                new GitHubPullRequestMinimalRef(
                                        "user-model",
                                        "aaa1111aaa1111aaa1111aaa1111aaa1111aaa11",
                                        new GitHubPullRequestMinimalRepo(
                                                1_296_269,
                                                "https://api.github.com/repos/octocat/hello-world",
                                                "hello-world")),
                                "PR_kwDOABCDEF4AAAAB",
                                "Add user API",
                                GitHubPullRequestStackState.OPEN,
                                Instant.parse("2022-04-28T12:00:00Z"),
                                true,
                                "https://github.com/octocat/hello-world/pull/102",
                                OptionalNull.of(UsersCatalogFixtures.octocat()))));
    }

    static GitHubPullRequestStackMinimal pullRequestStackMinimal() {
        return new GitHubPullRequestStackMinimal(
                9_876_543,
                42,
                "S_kwDOABCDEF4AAAAA",
                "https://api.github.com/repos/octocat/hello-world/stacks/42",
                new GitHubPullRequestStackRefBase("main"),
                true,
                Instant.parse("2026-04-15T10:00:00Z"),
                recordList(
                        new GitHubPullRequestStackMinimalPullRequest(
                                101,
                                GitHubPullRequestStackState.OPEN,
                                false,
                                Instant.parse("2022-04-28T12:00:00Z"),
                                new GitHubPullRequestStackMinimalPullRequestHead(
                                        "user-model", "aaa1111aaa1111aaa1111aaa1111aaa1111aaa11")),
                        new GitHubPullRequestStackMinimalPullRequest(
                                102,
                                GitHubPullRequestStackState.OPEN,
                                false,
                                Instant.parse("2022-04-28T12:00:00Z"),
                                new GitHubPullRequestStackMinimalPullRequestHead(
                                        "user-api", "bbb2222bbb2222bbb2222bbb2222bbb2222bbb22"))));
    }

    static GitHubPullRequestStackMinimalPullRequest pullRequestStackMinimalPullRequest() {
        return new GitHubPullRequestStackMinimalPullRequest(
                101,
                GitHubPullRequestStackState.OPEN,
                false,
                Instant.parse("2022-04-28T12:00:00Z"),
                new GitHubPullRequestStackMinimalPullRequestHead(
                        "user-model", "aaa1111aaa1111aaa1111aaa1111aaa1111aaa11"));
    }

    static GitHubPullRequestStackMinimalPullRequestHead pullRequestStackMinimalPullRequestHead() {
        return new GitHubPullRequestStackMinimalPullRequestHead(
                "user-model", "aaa1111aaa1111aaa1111aaa1111aaa1111aaa11");
    }

    static GitHubPullRequestStackPullRequest pullRequestStackPullRequest() {
        return new GitHubPullRequestStackPullRequest(
                100_001,
                101,
                "https://api.github.com/repos/octocat/hello-world/pulls/101",
                new GitHubPullRequestMinimalRef(
                        "user-model",
                        "aaa1111aaa1111aaa1111aaa1111aaa1111aaa11",
                        new GitHubPullRequestMinimalRepo(
                                1_296_269, "https://api.github.com/repos/octocat/hello-world", "hello-world")),
                new GitHubPullRequestMinimalRef(
                        "main",
                        "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                        new GitHubPullRequestMinimalRepo(
                                1_296_269, "https://api.github.com/repos/octocat/hello-world", "hello-world")),
                "PR_kwDOABCDEF4AAAAA",
                "Add user model",
                GitHubPullRequestStackState.OPEN,
                Instant.parse("2022-04-28T12:00:00Z"),
                false,
                "https://github.com/octocat/hello-world/pull/101",
                OptionalNull.of(UsersCatalogFixtures.octocat()));
    }

    static GitHubListPullRequestsFilter listPullRequestsFilter() {
        return new GitHubListPullRequestsFilter(
                GitHubListPullRequestsState.OPEN,
                "octocat:new-feature",
                "master",
                GitHubListPullRequestsSort.CREATED,
                GitHubListPullRequestsDirection.DESC);
    }

    static GitHubListPullRequestStacksFilter listPullRequestStacksFilter() {
        return new GitHubListPullRequestStacksFilter(101);
    }

    static GitHubListPullReviewCommentsFilter listPullReviewCommentsFilter() {
        return new GitHubListPullReviewCommentsFilter(
                GitHubListPullReviewCommentsSort.CREATED,
                GitHubListPullRequestsDirection.ASC,
                Instant.parse("2011-01-26T19:01:12Z"));
    }

    static GitHubListRepoReviewCommentsFilter listRepoReviewCommentsFilter() {
        return new GitHubListRepoReviewCommentsFilter(
                GitHubListRepoReviewCommentsSort.CREATED,
                GitHubListPullRequestsDirection.ASC,
                Instant.parse("2011-01-26T19:01:12Z"));
    }

    static GitHubCreatePullRequestPayload createPullRequestPayload() {
        return new GitHubCreatePullRequestPayload(
                "Amazing new feature",
                "octocat:new-feature",
                "octocat/Hello-World",
                "master",
                "Please pull these awesome changes",
                true,
                false,
                1347L);
    }

    static GitHubUpdatePullRequestPayload updatePullRequestPayload() {
        return new GitHubUpdatePullRequestPayload(
                "new-feature", "Please pull these awesome changes", GitHubPullRequestUpdateState.OPEN, "master", true);
    }

    static GitHubMergePullRequestPayload mergePullRequestPayload() {
        return new GitHubMergePullRequestPayload(
                "Amazing pull request",
                "Merge pull request",
                "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                GitHubPullRequestMergeMethod.SQUASH);
    }

    static GitHubMergePullRequestAsyncPayload mergePullRequestAsyncPayload() {
        return new GitHubMergePullRequestAsyncPayload(
                "Amazing pull request",
                "Merge pull request",
                "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                GitHubPullRequestMergeMethod.SQUASH,
                GitHubPullRequestMergeAsyncAction.DIRECT_MERGE,
                true);
    }

    static GitHubUpdatePullRequestBranchPayload updatePullRequestBranchPayload() {
        return new GitHubUpdatePullRequestBranchPayload("6dcb09b5b57875f334f61aebed695e2e4193db5e");
    }

    static GitHubCreateReviewPayload createReviewPayload() {
        return new GitHubCreateReviewPayload(
                "ecdd80bb57125d7ba9641ffaa4d7d2c19d3f3091",
                "This is close to perfect!",
                GitHubPullRequestReviewEvent.REQUEST_CHANGES,
                recordList(
                        new GitHubCreateReviewCommentItemPayload("file.md", "Please fix", 4, 6, "RIGHT", 1, "RIGHT")));
    }

    static GitHubUpdateReviewPayload updateReviewPayload() {
        return new GitHubUpdateReviewPayload("Updated review body.");
    }

    static GitHubSubmitReviewPayload submitReviewPayload() {
        return new GitHubSubmitReviewPayload(
                "Here is the body for the review.", GitHubPullRequestReviewEvent.REQUEST_CHANGES);
    }

    static GitHubDismissReviewPayload dismissReviewPayload() {
        return new GitHubDismissReviewPayload("You are dismissed", GitHubDismissReviewEvent.DISMISS);
    }

    static GitHubCreateReviewCommentPayload createReviewCommentPayload() {
        return new GitHubCreateReviewCommentPayload(
                "Great stuff!",
                "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "file1.txt",
                5,
                GitHubPullRequestReviewCommentSide.RIGHT,
                2,
                1,
                GitHubPullRequestReviewCommentStartSide.RIGHT,
                426_899_381L,
                GitHubPullRequestReviewCommentSubjectType.LINE);
    }

    static GitHubCreateReviewCommentItemPayload createReviewCommentItemPayload() {
        return new GitHubCreateReviewCommentItemPayload("file.md", "Please fix", 4, 6, "RIGHT", 1, "RIGHT");
    }

    static GitHubCreateReviewCommentReplyPayload createReviewCommentReplyPayload() {
        return new GitHubCreateReviewCommentReplyPayload("Great stuff!");
    }

    static GitHubUpdateReviewCommentPayload updateReviewCommentPayload() {
        return new GitHubUpdateReviewCommentPayload("I like this too!");
    }

    static GitHubRequestReviewersPayload requestReviewersPayload() {
        return new GitHubRequestReviewersPayload(List.of("octocat", "hubot"), List.of("justice-league"));
    }

    static GitHubRerequestReviewersPayload rerequestReviewersPayload() {
        return new GitHubRerequestReviewersPayload(List.of("octocat", "hubot"), List.of("justice-league"));
    }

    static GitHubRemoveReviewersPayload removeReviewersPayload() {
        return new GitHubRemoveReviewersPayload(List.of("octocat"), List.of("justice-league"));
    }

    static GitHubCreatePullRequestStackPayload createPullRequestStackPayload() {
        return new GitHubCreatePullRequestStackPayload(List.of(101, 102, 103));
    }

    static GitHubAddPullRequestsToStackPayload addPullRequestsToStackPayload() {
        return new GitHubAddPullRequestsToStackPayload(List.of(104, 102));
    }

    private static GitHubRepository helloWorldRepository() {
        return new GitHubRepository(
                42,
                "MDEwOlJlcG9zaXRvcnkxMjk2MjY5",
                "Team Environment",
                "octocat/Hello-World",
                OptionalNull.of(new GitHubLicenseSimple(
                        "mit",
                        "MIT License",
                        "https://api.github.com/licenses/mit",
                        "MIT",
                        "MDc6TGljZW5zZW1pdA==",
                        "https://choosealicense.com/licenses/mit/")),
                9,
                new GitHubRepositoryPermissions(true, false, true, false, true),
                UsersCatalogFixtures.octocat(),
                false,
                "https://github.com/octocat/Hello-World",
                "This your first repo!",
                false,
                "https://api.github.com/repos/octocat/Hello-World",
                "http://api.github.com/repos/octocat/Hello-World/{archive_format}{/ref}",
                "http://api.github.com/repos/octocat/Hello-World/assignees{/user}",
                "http://api.github.com/repos/octocat/Hello-World/git/blobs{/sha}",
                "http://api.github.com/repos/octocat/Hello-World/branches{/branch}",
                "http://api.github.com/repos/octocat/Hello-World/collaborators{/collaborator}",
                "http://api.github.com/repos/octocat/Hello-World/comments{/number}",
                "http://api.github.com/repos/octocat/Hello-World/commits{/sha}",
                "http://api.github.com/repos/octocat/Hello-World/compare/{base}...{head}",
                "http://api.github.com/repos/octocat/Hello-World/contents/{+path}",
                "http://api.github.com/repos/octocat/Hello-World/contributors",
                "http://api.github.com/repos/octocat/Hello-World/deployments",
                "http://api.github.com/repos/octocat/Hello-World/downloads",
                "http://api.github.com/repos/octocat/Hello-World/events",
                "http://api.github.com/repos/octocat/Hello-World/forks",
                "http://api.github.com/repos/octocat/Hello-World/git/commits{/sha}",
                "http://api.github.com/repos/octocat/Hello-World/git/refs{/sha}",
                "http://api.github.com/repos/octocat/Hello-World/git/tags{/sha}",
                "git:github.com/octocat/Hello-World.git",
                "http://api.github.com/repos/octocat/Hello-World/issues/comments{/number}",
                "http://api.github.com/repos/octocat/Hello-World/issues/events{/number}",
                "http://api.github.com/repos/octocat/Hello-World/issues{/number}",
                "http://api.github.com/repos/octocat/Hello-World/keys{/key_id}",
                "http://api.github.com/repos/octocat/Hello-World/labels{/name}",
                "http://api.github.com/repos/octocat/Hello-World/languages",
                "http://api.github.com/repos/octocat/Hello-World/merges",
                "http://api.github.com/repos/octocat/Hello-World/milestones{/number}",
                "http://api.github.com/repos/octocat/Hello-World/notifications{?since,all,participating}",
                "http://api.github.com/repos/octocat/Hello-World/pulls{/number}",
                "http://api.github.com/repos/octocat/Hello-World/releases{/id}",
                "git@github.com:octocat/Hello-World.git",
                "http://api.github.com/repos/octocat/Hello-World/stargazers",
                "http://api.github.com/repos/octocat/Hello-World/statuses/{sha}",
                "http://api.github.com/repos/octocat/Hello-World/subscribers",
                "http://api.github.com/repos/octocat/Hello-World/subscription",
                "http://api.github.com/repos/octocat/Hello-World/tags",
                "http://api.github.com/repos/octocat/Hello-World/teams",
                "http://api.github.com/repos/octocat/Hello-World/git/trees{/sha}",
                "https://github.com/octocat/Hello-World.git",
                OptionalNull.of("https://github.com/octocat/Hello-World-mirror.git"),
                "http://api.github.com/repos/octocat/Hello-World/hooks",
                "https://svn.github.com/octocat/Hello-World",
                "https://github.com",
                OptionalNull.of("C"),
                9,
                80,
                80,
                108,
                "master",
                0,
                false,
                List.of("octocat", "atom", "electron", "api"),
                true,
                true,
                true,
                false,
                false,
                true,
                GitHubPullRequestCreationPolicy.ALL,
                false,
                false,
                "public",
                Instant.parse("2011-01-26T19:06:43Z"),
                Instant.parse("2011-01-26T19:01:12Z"),
                Instant.parse("2011-01-26T19:14:43Z"),
                true,
                "abc123token",
                true,
                false,
                false,
                true,
                GitHubSquashMergeCommitTitle.PR_TITLE,
                GitHubSquashMergeCommitMessage.PR_BODY,
                GitHubMergeCommitTitle.PR_TITLE,
                GitHubMergeCommitMessage.PR_BODY,
                true,
                true,
                false,
                0,
                80,
                "2020-11-02T16:12:33Z",
                false,
                new GitHubCodeSearchIndexStatus(true, "6dcb09b5b57875f334f61aebed695e2e4193db5e"));
    }

    static GitHubDiffEntry diffEntryFromStub() {
        return new GitHubDiffEntry(
                "bbcd538c8e72b8c175046e27cc8f907076331401",
                "file1.txt",
                GitHubDiffEntryStatus.ADDED,
                103,
                21,
                124,
                "https://github.com/octocat/Hello-World/blob/6dcb09b5b57875f334f61aebed695e2e4193db5e/file1.txt",
                "https://github.com/octocat/Hello-World/raw/6dcb09b5b57875f334f61aebed695e2e4193db5e/file1.txt",
                "https://api.github.com/repos/octocat/Hello-World/contents/file1.txt?ref=6dcb09b5b57875f334f61aebed695e2e4193db5e",
                null,
                null);
    }

    static List<GitHubPullRequestStackMinimal> pullRequestStackMinimalItems() {
        return List.of(new GitHubPullRequestStackMinimal(
                9_876_543,
                42,
                "S_kwDOABCDEF4AAAAA",
                "https://api.github.com/repos/octocat/hello-world/stacks/42",
                new GitHubPullRequestStackRefBase("main"),
                true,
                Instant.parse("2026-04-15T10:00:00Z"),
                List.of(
                        new GitHubPullRequestStackMinimalPullRequest(
                                101,
                                GitHubPullRequestStackState.OPEN,
                                false,
                                null,
                                new GitHubPullRequestStackMinimalPullRequestHead(
                                        "user-model", "aaa1111aaa1111aaa1111aaa1111aaa1111aaa11")),
                        new GitHubPullRequestStackMinimalPullRequest(
                                102,
                                GitHubPullRequestStackState.OPEN,
                                false,
                                null,
                                new GitHubPullRequestStackMinimalPullRequestHead(
                                        "user-api", "bbb2222bbb2222bbb2222bbb2222bbb2222bbb22")))));
    }
}
