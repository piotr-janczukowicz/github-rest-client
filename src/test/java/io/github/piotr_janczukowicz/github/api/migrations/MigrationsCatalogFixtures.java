package io.github.piotr_janczukowicz.github.api.migrations;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.time.Instant;
import java.util.List;

final class MigrationsCatalogFixtures {

    private static final String REPO_HTTP = "http://api.github.com/repos/octocat/Hello-World";

    private MigrationsCatalogFixtures() {}

    static GitHubSimpleUser migrationFixtureOctocat() {
        return UsersCatalogFixtures.octocat();
    }

    static GitHubSimpleRepository migrationFixtureHelloWorld() {
        return new GitHubSimpleRepository(
                1_296_269L,
                "MDEwOlJlcG9zaXRvcnkxMjk2MjY5",
                "Hello-World",
                "octocat/Hello-World",
                migrationFixtureOctocat(),
                false,
                "https://github.com/octocat/Hello-World",
                "This your first repo!",
                false,
                "https://api.github.com/repos/octocat/Hello-World",
                REPO_HTTP + "/{archive_format}{/ref}",
                REPO_HTTP + "/assignees{/user}",
                REPO_HTTP + "/git/blobs{/sha}",
                REPO_HTTP + "/branches{/branch}",
                REPO_HTTP + "/collaborators{/collaborator}",
                REPO_HTTP + "/comments{/number}",
                REPO_HTTP + "/commits{/sha}",
                REPO_HTTP + "/compare/{base}...{head}",
                REPO_HTTP + "/contents/{+path}",
                REPO_HTTP + "/contributors",
                REPO_HTTP + "/deployments",
                REPO_HTTP + "/downloads",
                REPO_HTTP + "/events",
                REPO_HTTP + "/forks",
                REPO_HTTP + "/git/commits{/sha}",
                REPO_HTTP + "/git/refs{/sha}",
                REPO_HTTP + "/git/tags{/sha}",
                REPO_HTTP + "/issues/comments{/number}",
                REPO_HTTP + "/issues/events{/number}",
                REPO_HTTP + "/issues{/number}",
                REPO_HTTP + "/keys{/key_id}",
                REPO_HTTP + "/labels{/name}",
                REPO_HTTP + "/languages",
                REPO_HTTP + "/merges",
                REPO_HTTP + "/milestones{/number}",
                REPO_HTTP + "/notifications{?since,all,participating}",
                REPO_HTTP + "/pulls{/number}",
                REPO_HTTP + "/releases{/id}",
                REPO_HTTP + "/stargazers",
                REPO_HTTP + "/statuses/{sha}",
                REPO_HTTP + "/subscribers",
                REPO_HTTP + "/subscription",
                REPO_HTTP + "/tags",
                REPO_HTTP + "/teams",
                REPO_HTTP + "/git/trees{/sha}",
                REPO_HTTP + "/hooks");
    }

    static GitHubMigration migration() {
        return new GitHubMigration(
                79L,
                "MDEyOk9yZ2FuaXphdGlvbjE=",
                migrationFixtureOctocat(),
                "0b989ba4-242f-11e5-81e1-c7b6966d2516",
                "exported",
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                List.of(migrationFixtureHelloWorld()),
                "https://api.github.com/orgs/github/migrations/79",
                Instant.parse("2015-07-06T22:33:38Z"),
                Instant.parse("2015-07-06T22:33:38Z"),
                "https://api.github.com/orgs/github/migrations/79/archive",
                List.of("repositories"));
    }

    static GitHubImport githubImport() {
        return new GitHubImport(
                "subversion",
                true,
                "http://svn.example.com/svn/myproject",
                "http://svn.example.com/svn",
                "myproject",
                GitHubImportStatus.COMPLETE,
                "Done",
                "import",
                "",
                100,
                4,
                100,
                true,
                132_331_036,
                1,
                List.of(new GitHubImport.ProjectChoice("subversion", "proj", "My Project")),
                "",
                4,
                "https://api.github.com/repos/octocat/socm/import",
                "https://import.github.com/octocat/socm/import",
                "https://api.github.com/repos/octocat/socm/import/authors",
                "https://api.github.com/repos/octocat/socm",
                "http://svn.example.com/svn");
    }

    static GitHubMapCommitAuthorPayload mapCommitAuthorPayload() {
        return GitHubMapCommitAuthorPayloadBuilder.builder()
                .email("hubot@github.com")
                .name("Hubot")
                .build();
    }

    static GitHubSetLfsPreferencePayload setLfsPreferencePayload() {
        return GitHubSetLfsPreferencePayloadBuilder.builder()
                .useLfs(GitHubImportUseLfs.OPT_IN)
                .build();
    }

    static GitHubStartForAuthenticatedUserPayload startForAuthenticatedUserPayload() {
        return GitHubStartForAuthenticatedUserPayloadBuilder.builder()
                .repositories(List.of("Hello-World"))
                .build();
    }

    static GitHubStartForOrgPayload startForOrgPayload() {
        return GitHubStartForOrgPayloadBuilder.builder()
                .repositories(List.of("Hello-World"))
                .build();
    }

    static GitHubStartImportPayload startImportPayload() {
        return GitHubStartImportPayloadBuilder.builder()
                .vcsUrl("http://svn.example.com/svn/myproject")
                .vcs(GitHubImportVcs.SUBVERSION)
                .build();
    }

    static GitHubUpdateImportPayload updateImportPayload() {
        return GitHubUpdateImportPayloadBuilder.builder()
                .vcsUsername("octocat")
                .vcsPassword("secret")
                .build();
    }

    static GitHubPorterAuthor porterAuthor() {
        return new GitHubPorterAuthor(
                2_268_557,
                "nobody@fc7da526-431c-80fe-3c8c-c148ff18d7ef",
                "nobody",
                "hubot@github.com",
                "Hubot",
                "https://api.github.com/repos/octocat/socm/import/authors/2268557",
                "https://api.github.com/repos/octocat/socm/import");
    }

    static List<GitHubPorterAuthor> porterAuthorItems() {
        return List.of(
                porterAuthor(),
                new GitHubPorterAuthor(
                        2_268_558,
                        "svner@fc7da526-431c-80fe-3c8c-c148ff18d7ef",
                        "svner",
                        "svner@fc7da526-431c-80fe-3c8c-c148ff18d7ef",
                        "svner",
                        "https://api.github.com/repos/octocat/socm/import/authors/2268558",
                        "https://api.github.com/repos/octocat/socm/import"),
                new GitHubPorterAuthor(
                        2_268_559,
                        "svner@example.com@fc7da526-431c-80fe-3c8c-c148ff18d7ef",
                        "svner@example.com",
                        "svner@example.com@fc7da526-431c-80fe-3c8c-c148ff18d7ef",
                        "svner@example.com",
                        "https://api.github.com/repos/octocat/socm/import/authors/2268559",
                        "https://api.github.com/repos/octocat/socm/import"));
    }

    static GitHubPorterLargeFile porterLargeFile() {
        return new GitHubPorterLargeFile(
                "refs/heads/master", "foo/bar/1", "d3d9446802a44259755d38e6d163e820", 10_485_760);
    }

    static List<GitHubPorterLargeFile> porterLargeFileItems() {
        return List.of(
                porterLargeFile(),
                new GitHubPorterLargeFile(
                        "refs/heads/master", "foo/bar/2", "6512bd43d9caa6e02c990b0a82652dca", 11_534_336),
                new GitHubPorterLargeFile(
                        "refs/heads/master", "foo/bar/3", "c20ad4d76fe97759aa27a0c99bff6710", 12_582_912));
    }
}
