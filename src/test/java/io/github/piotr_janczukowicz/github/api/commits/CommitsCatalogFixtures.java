package io.github.piotr_janczukowicz.github.api.commits;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubCommit;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCommitBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.search.GitHubReactionRollup;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.time.Instant;
import java.util.List;

final class CommitsCatalogFixtures {

    private static final Instant CREATED = Instant.parse("2011-04-14T16:00:49Z");

    private static final String SHA = "6dcb09b5b57875f334f61aebed695e2e4193db5e";

    private static final String STATUS_URL = "https://api.github.com/repos/octocat/Hello-World/statuses/" + SHA;

    private static final String TARGET_URL = "https://example.com/build/status";

    private static final String CONTEXT = "continuous-integration/jenkins";

    private static final String DESCRIPTION = "Build has completed successfully";

    private static final String AVATAR = "https://github.com/images/error/octocat_happy.gif";

    private CommitsCatalogFixtures() {}

    static GitHubListCommitsFilter listCommitsFilter() {
        return GitHubListCommitsFilterBuilder.builder()
                .sha("main")
                .path("README.md")
                .author("octocat")
                .committer("octocat")
                .since(Instant.parse("2011-01-26T19:01:12Z"))
                .until(Instant.parse("2011-01-26T19:14:43Z"))
                .build();
    }

    static GitHubUpdateCommitCommentPayload updateCommitCommentPayload() {
        return new GitHubUpdateCommitCommentPayload("n");
    }

    static GitHubCommitStatus commitStatus() {
        return GitHubCommitStatusBuilder.builder()
                .url(STATUS_URL)
                .avatarUrl(AVATAR)
                .id(1L)
                .nodeId("MDY6U3RhdHVzMQ==")
                .state("success")
                .description(DESCRIPTION)
                .targetUrl(TARGET_URL)
                .context(CONTEXT)
                .createdAt("2011-04-14T16:00:49Z")
                .updatedAt("2011-04-14T16:00:49Z")
                .creator(UsersCatalogFixtures.octocat())
                .build();
    }

    static GitHubSimpleCommitStatus simpleCommitStatus() {
        return GitHubSimpleCommitStatusBuilder.builder()
                .description(DESCRIPTION)
                .id(1L)
                .nodeId("MDY6U3RhdHVzMQ==")
                .state("success")
                .context(CONTEXT)
                .targetUrl(TARGET_URL)
                .required(true)
                .avatarUrl(AVATAR)
                .url(STATUS_URL)
                .createdAt(CREATED)
                .updatedAt(CREATED)
                .build();
    }

    static GitHubCommitComparison commitComparison() {
        return GitHubCommitComparisonBuilder.builder()
                .url("https://api.github.com/repos/octocat/Hello-World/compare/master...topic")
                .htmlUrl("https://github.com/octocat/Hello-World/compare/master...topic")
                .permalinkUrl("https://github.com/octocat/Hello-World/compare/octocat:master...octocat:topic")
                .diffUrl("https://github.com/octocat/Hello-World/compare/master...topic.diff")
                .patchUrl("https://github.com/octocat/Hello-World/compare/master...topic.patch")
                .baseCommit(RepositoriesCatalogFixtures.commit())
                .mergeBaseCommit(RepositoriesCatalogFixtures.commit())
                .status(GitHubCommitComparisonStatus.AHEAD)
                .aheadBy(4)
                .behindBy(5)
                .totalCommits(6)
                .commits(List.of(RepositoriesCatalogFixtures.commit()))
                .files(List.of(RepositoriesCatalogFixtures.sampleDiffEntry()))
                .build();
    }

    static GitHubCommitComparison commitComparisonFromStub() {
        final GitHubCommit baseCommit = GitHubCommitBuilder.builder(RepositoriesCatalogFixtures.commitFromStub())
                .parents(List.of())
                .build();
        return GitHubCommitComparisonBuilder.builder()
                .url("https://api.github.com/repos/octocat/Hello-World/compare/master...topic")
                .htmlUrl("https://github.com/octocat/Hello-World/compare/master...topic")
                .permalinkUrl("https://github.com/octocat/Hello-World/compare/octocat:master...octocat:topic")
                .diffUrl("https://github.com/octocat/Hello-World/compare/master...topic.diff")
                .patchUrl("https://github.com/octocat/Hello-World/compare/master...topic.patch")
                .baseCommit(baseCommit)
                .mergeBaseCommit(baseCommit)
                .status(GitHubCommitComparisonStatus.AHEAD)
                .aheadBy(4)
                .behindBy(5)
                .totalCommits(6)
                .commits(List.of())
                .build();
    }

    static GitHubCommitComment commitComment() {
        return GitHubCommitCommentBuilder.builder()
                .htmlUrl(
                        "https://github.com/octocat/Hello-World/commit/6dcb09b5b57875f334f61aebed695e2e4193db5e#commitcomment-1")
                .url("https://api.github.com/repos/octocat/Hello-World/comments/1")
                .id(1L)
                .nodeId("MDIzOkNvbW1lbnQx")
                .body("Great stuff")
                .path("file1.txt")
                .position(4)
                .line(4)
                .commitId(SHA)
                .user(UsersCatalogFixtures.octocat())
                .createdAt(CREATED)
                .updatedAt(CREATED)
                .authorAssociation(GitHubAuthorAssociation.NONE)
                .reactions(new GitHubReactionRollup(
                        "https://api.github.com/repos/octocat/Spoon-Knife/issues/comments/42/reactions",
                        2,
                        1,
                        0,
                        0,
                        0,
                        1,
                        0,
                        0,
                        0))
                .build();
    }

    static GitHubBranchShortCommit branchShortCommit() {
        return new GitHubBranchShortCommit("n", "n");
    }

    static GitHubCombinedCommitStatus combinedCommitStatus() {
        return GitHubCombinedCommitStatusBuilder.builder()
                .state("success")
                .statuses(List.of(simpleCommitStatus()))
                .sha(SHA)
                .totalCount(1)
                .repository(RepositoriesCatalogFixtures.helloWorldMinimalRepository())
                .commitUrl("https://api.github.com/repos/octocat/Hello-World/commits/" + SHA)
                .url("https://api.github.com/repos/octocat/Hello-World/commits/" + SHA + "/status")
                .build();
    }

    static GitHubBranchShort branchShort() {
        return new GitHubBranchShort(
                "main",
                new GitHubBranchShortCommit(SHA, "https://api.github.com/repos/octocat/Hello-World/commits/" + SHA),
                true);
    }

    static GitHubCreateCommitStatusPayload createCommitStatusPayload() {
        return GitHubCreateCommitStatusPayloadBuilder.builder()
                .state(GitHubCommitStatusState.SUCCESS)
                .targetUrl(TARGET_URL)
                .description("The build succeeded!")
                .context(CONTEXT)
                .build();
    }

    static GitHubCreateCommitCommentPayload createCommitCommentPayload() {
        return GitHubCreateCommitCommentPayloadBuilder.builder()
                .body("n")
                .path("file1.txt")
                .position(4)
                .line(4)
                .build();
    }
}
