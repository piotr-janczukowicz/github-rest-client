package io.github.piotr_janczukowicz.github.api.gists;

import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.time.Instant;
import java.util.List;
import java.util.Map;

final class GistsCatalogFixtures {

    private GistsCatalogFixtures() {}

    static GitHubBaseGistFile baseGistFile() {
        return GitHubBaseGistFileBuilder.builder()
                .filename("hello_world.rb")
                .type("application/x-ruby")
                .language("Ruby")
                .rawUrl(
                        "https://gist.githubusercontent.com/octocat/6cad326836d38bd3a7ae/raw/db9c55113504e46fa076e7df3a04ce592e2e86d8/hello_world.rb")
                .size(167)
                .encoding("utf-8")
                .build();
    }

    static GitHubBaseGist baseGist() {
        return GitHubBaseGistBuilder.builder()
                .url("https://api.github.com/gists/aa5a315d61ae9438b18d")
                .forksUrl("https://api.github.com/gists/aa5a315d61ae9438b18d/forks")
                .commitsUrl("https://api.github.com/gists/aa5a315d61ae9438b18d/commits")
                .id("aa5a315d61ae9438b18d")
                .nodeId("MDQ6R2lzdGFhNWEzMTVkNjFhZTk0MzhiMThk")
                .gitPullUrl("https://gist.github.com/aa5a315d61ae9438b18d.git")
                .gitPushUrl("https://gist.github.com/aa5a315d61ae9438b18d.git")
                .htmlUrl("https://gist.github.com/aa5a315d61ae9438b18d")
                .files(Map.of("hello_world.rb", baseGistFile()))
                .isPublic(true)
                .createdAt(Instant.parse("2010-04-14T02:15:15Z"))
                .updatedAt(Instant.parse("2011-06-20T11:34:15Z"))
                .description("Hello World Examples")
                .comments(0)
                .commentsEnabled(true)
                .commentsUrl("https://api.github.com/gists/aa5a315d61ae9438b18d/comments/")
                .owner(UsersCatalogFixtures.octocat())
                .truncated(false)
                .build();
    }

    static GitHubGistSimpleForkOfFile gistSimpleForkOfFile() {
        return GitHubGistSimpleForkOfFileBuilder.builder()
                .filename("hello_world.rb")
                .type("application/x-ruby")
                .language("Ruby")
                .rawUrl(
                        "https://gist.githubusercontent.com/octocat/6cad326836d38bd3a7ae/raw/db9c55113504e46fa076e7df3a04ce592e2e86d8/hello_world.rb")
                .size(167)
                .build();
    }

    static GitHubGistSimpleForkOf gistSimpleForkOf() {
        return GitHubGistSimpleForkOfBuilder.builder()
                .url("https://api.github.com/gists/aa5a315d61ae9438b18d")
                .forksUrl("https://api.github.com/gists/aa5a315d61ae9438b18d/forks")
                .commitsUrl("https://api.github.com/gists/aa5a315d61ae9438b18d/commits")
                .id("aa5a315d61ae9438b18d")
                .nodeId("MDQ6R2lzdGFhNWEzMTVkNjFhZTk0MzhiMThk")
                .gitPullUrl("https://gist.github.com/aa5a315d61ae9438b18d.git")
                .gitPushUrl("https://gist.github.com/aa5a315d61ae9438b18d.git")
                .htmlUrl("https://gist.github.com/aa5a315d61ae9438b18d")
                .files(Map.of("hello_world.rb", gistSimpleForkOfFile()))
                .isPublic(true)
                .createdAt(Instant.parse("2010-04-14T02:15:15Z"))
                .updatedAt(Instant.parse("2011-06-20T11:34:15Z"))
                .description("Hello World Examples")
                .comments(0)
                .commentsEnabled(true)
                .user(UsersCatalogFixtures.octocat())
                .commentsUrl("https://api.github.com/gists/aa5a315d61ae9438b18d/comments/")
                .owner(UsersCatalogFixtures.octocat())
                .truncated(false)
                .forks(List.of(Map.of("id", "2decf6c462d9b4418f2")))
                .history(List.of(Map.of(
                        "version",
                        "468aac8caed5f0c3b859b8286968",
                        "committed_at",
                        "2022-09-21T10:28:06Z",
                        "change_status",
                        Map.of("total", 2, "additions", 1, "deletions", 1))))
                .build();
    }

    static GitHubGistSimpleFile gistSimpleFile() {
        return GitHubGistSimpleFileBuilder.builder()
                .filename("README.md")
                .type("text/markdown")
                .language("Markdown")
                .rawUrl(
                        "https://gist.githubusercontent.com/monalisa/2decf6c462d9b4418f2/raw/ac3e6daf176fafe73609fd000cd188e4472010fb/README.md")
                .size(23)
                .truncated(false)
                .content("Hello world from GitHub")
                .encoding("utf-8")
                .build();
    }

    static GitHubGistSimple gistSimple() {
        return GitHubGistSimpleBuilder.builder()
                .forkOf(gistSimpleForkOf())
                .url("https://api.github.com/gists/2decf6c462d9b4418f2")
                .forksUrl("https://api.github.com/gists/2decf6c462d9b4418f2/forks")
                .commitsUrl("https://api.github.com/gists/2decf6c462d9b4418f2/commits")
                .id("2decf6c462d9b4418f2")
                .nodeId("G_kwDOBhHyLdZDliNDQxOGYy")
                .gitPullUrl("https://gist.github.com/2decf6c462d9b4418f2.git")
                .gitPushUrl("https://gist.github.com/2decf6c462d9b4418f2.git")
                .htmlUrl("https://gist.github.com/2decf6c462d9b4418f2")
                .files(Map.of("README.md", gistSimpleFile()))
                .isPublic(true)
                .createdAt("2022-09-20T12:11:58Z")
                .updatedAt("2022-09-21T10:28:06Z")
                .description("An updated gist description.")
                .comments(0)
                .commentsEnabled(true)
                .user("monalisa")
                .commentsUrl("https://api.github.com/gists/2decf6c462d9b4418f2/comments")
                .owner(UsersCatalogFixtures.monalisa())
                .truncated(false)
                .build();
    }

    static GitHubGistComment gistComment() {
        return GitHubGistCommentBuilder.builder()
                .id(1)
                .nodeId("MDExOkdpc3RDb21tZW50MQ==")
                .url("https://api.github.com/gists/a6db0bec360bb87e9418/comments/1")
                .body("Just commenting for the sake of commenting")
                .user(UsersCatalogFixtures.octocat())
                .createdAt(Instant.parse("2011-04-18T23:23:56Z"))
                .updatedAt(Instant.parse("2011-04-18T23:23:56Z"))
                .authorAssociation(GitHubAuthorAssociation.COLLABORATOR)
                .build();
    }

    static GitHubGistCommitChangeStatus gistCommitChangeStatus() {
        return new GitHubGistCommitChangeStatus(180, 180, 0);
    }

    static GitHubGistCommit gistCommit() {
        return GitHubGistCommitBuilder.builder()
                .url("https://api.github.com/gists/aa5a315d61ae9438b18d/57a7f021a713b1c5a6a199b54cc514735d2d462f")
                .version("57a7f021a713b1c5a6a199b54cc514735d2d462f")
                .user(UsersCatalogFixtures.octocat())
                .changeStatus(gistCommitChangeStatus())
                .committedAt(Instant.parse("2010-04-14T02:15:15Z"))
                .build();
    }

    static GitHubCreateGistFile createGistFile() {
        return new GitHubCreateGistFile("puts \"Hello, World!\"");
    }

    static GitHubCreatePayload createPayload() {
        return GitHubCreatePayloadBuilder.builder()
                .description("Example Ruby script")
                .files(Map.of("hello.rb", createGistFile()))
                .isPublic(true)
                .build();
    }

    static GitHubCreatePayload createPayloadBoolean() {
        return GitHubCreatePayloadBuilder.builder(createPayload())
                .isPublic(false)
                .build();
    }

    static GitHubUpdateGistFile updateGistFile() {
        return new GitHubUpdateGistFile("blah", "goodbye.rb");
    }

    static GitHubUpdatePayload updatePayload() {
        return GitHubUpdatePayloadBuilder.builder()
                .description("An updated gist description")
                .files(Map.of("hello.rb", updateGistFile()))
                .build();
    }

    static GitHubCreateCommentPayload createCommentPayload() {
        return new GitHubCreateCommentPayload("This is a comment to a gist");
    }

    static GitHubUpdateCommentPayload updateCommentPayload() {
        return new GitHubUpdateCommentPayload("This is an update to a comment in a gist");
    }

    static GitHubListFilter listFilter() {
        return new GitHubListFilter(Instant.parse("2022-09-20T12:11:58Z"));
    }

    static GitHubListPublicFilter listPublicFilter() {
        return new GitHubListPublicFilter(Instant.parse("2022-09-20T12:11:58Z"));
    }

    static GitHubListStarredFilter listStarredFilter() {
        return new GitHubListStarredFilter(Instant.parse("2022-09-20T12:11:58Z"));
    }

    static GitHubListForUserFilter listForUserFilter() {
        return new GitHubListForUserFilter(Instant.parse("2022-09-20T12:11:58Z"));
    }
}
