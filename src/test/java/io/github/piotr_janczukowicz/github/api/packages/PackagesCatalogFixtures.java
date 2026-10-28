package io.github.piotr_janczukowicz.github.api.packages;

import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUserBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserViewType;
import java.time.Instant;
import java.util.List;

final class PackagesCatalogFixtures {

    private PackagesCatalogFixtures() {}

    static GitHubPackageVersionMetadata containerMetadata() {
        return new GitHubPackageVersionMetadata(
                GitHubPackageType.CONTAINER, new GitHubPackageContainerMetadata(List.of("latest")), null);
    }

    static GitHubPackageVersionMetadata dockerMetadata() {
        return new GitHubPackageVersionMetadata(
                GitHubPackageType.DOCKER, null, new GitHubPackageDockerMetadata(List.of("latest")));
    }

    static GitHubPackage githubPackage() {
        return new GitHubPackage(
                197L,
                "hello_docker",
                GitHubPackageType.CONTAINER,
                GitHubPackageVisibility.PRIVATE,
                "https://api.github.com/orgs/github/packages/container/hello_docker",
                "https://github.com/orgs/github/packages/container/package/hello_docker",
                1,
                packageOrgOwner(),
                RepositoriesCatalogFixtures.helloWorldMinimalRepository(),
                Instant.parse("2020-05-19T22:19:11Z"),
                Instant.parse("2020-05-19T22:19:11Z"));
    }

    static GitHubPackageVersion packageVersion() {
        return new GitHubPackageVersion(
                836L,
                "sha256:b3d3e366b55f9a54599220198b3db5da8f53592acbbb7dc7e4e9878762fc5344",
                "https://api.github.com/orgs/github/packages/container/hello_docker/versions/836",
                "https://github.com/orgs/github/packages/container/package/hello_docker",
                "https://github.com/orgs/github/packages/container/hello_docker/836",
                "MIT",
                "Hello Docker container image",
                Instant.parse("2020-05-19T22:19:11Z"),
                Instant.parse("2020-05-19T22:19:11Z"),
                Instant.parse("2020-05-20T10:00:00Z"),
                containerMetadata());
    }

    static GitHubSimpleUser packageOrgOwner() {
        return GitHubSimpleUserBuilder.builder()
                .login("github")
                .name("GitHub")
                .email("octocat@github.com")
                .id(9919L)
                .nodeId("MDEyOk9yZ2FuaXphdGlvbjk5MTk=")
                .avatarUrl("https://avatars.githubusercontent.com/u/9919?v=4")
                .gravatarId("")
                .url("https://api.github.com/users/github")
                .htmlUrl("https://github.com/github")
                .followersUrl("https://api.github.com/users/github/followers")
                .followingUrl("https://api.github.com/users/github/following{/other_user}")
                .gistsUrl("https://api.github.com/users/github/gists{/gist_id}")
                .starredUrl("https://api.github.com/users/github/starred{/owner}{/repo}")
                .subscriptionsUrl("https://api.github.com/users/github/subscriptions")
                .organizationsUrl("https://api.github.com/users/github/orgs")
                .reposUrl("https://api.github.com/users/github/repos")
                .eventsUrl("https://api.github.com/users/github/events{/privacy}")
                .receivedEventsUrl("https://api.github.com/users/github/received_events")
                .type("Organization")
                .siteAdmin(false)
                .starredAt("2020-11-02T16:12:33Z")
                .userViewType(GitHubUserViewType.PUBLIC)
                .build();
    }

    static List<GitHubPackage> packagesForOrg() {
        final GitHubSimpleUser owner = GitHubSimpleUserBuilder.builder()
                .login("github")
                .id(9919L)
                .nodeId("MDEyOk9yZ2FuaXphdGlvbjk5MTk=")
                .avatarUrl("https://avatars.githubusercontent.com/u/9919?v=4")
                .gravatarId("")
                .url("https://api.github.com/users/github")
                .htmlUrl("https://github.com/github")
                .followersUrl("https://api.github.com/users/github/followers")
                .followingUrl("https://api.github.com/users/github/following{/other_user}")
                .gistsUrl("https://api.github.com/users/github/gists{/gist_id}")
                .starredUrl("https://api.github.com/users/github/starred{/owner}{/repo}")
                .subscriptionsUrl("https://api.github.com/users/github/subscriptions")
                .organizationsUrl("https://api.github.com/users/github/orgs")
                .reposUrl("https://api.github.com/users/github/repos")
                .eventsUrl("https://api.github.com/users/github/events{/privacy}")
                .receivedEventsUrl("https://api.github.com/users/github/received_events")
                .type("Organization")
                .siteAdmin(false)
                .build();
        return List.of(
                new GitHubPackage(
                        197L,
                        "hello_docker",
                        GitHubPackageType.CONTAINER,
                        GitHubPackageVisibility.PRIVATE,
                        "https://api.github.com/orgs/github/packages/container/hello_docker",
                        "https://github.com/orgs/github/packages/container/package/hello_docker",
                        1,
                        owner,
                        null,
                        Instant.parse("2020-05-19T22:19:11Z"),
                        Instant.parse("2020-05-19T22:19:11Z")),
                new GitHubPackage(
                        198L,
                        "goodbye_docker",
                        GitHubPackageType.CONTAINER,
                        GitHubPackageVisibility.PRIVATE,
                        "https://api.github.com/orgs/github/packages/container/goodbye_docker",
                        "https://github.com/orgs/github/packages/container/package/goodbye_docker",
                        2,
                        owner,
                        null,
                        Instant.parse("2020-05-20T22:19:11Z"),
                        Instant.parse("2020-05-20T22:19:11Z")));
    }

    static List<GitHubPackageVersion> packageVersionsForOrg() {
        return List.of(
                new GitHubPackageVersion(
                        245_301L,
                        "1.0.4",
                        "https://api.github.com/orgs/octo-org/packages/npm/hello-world-npm/versions/245301",
                        "https://github.com/octo-org/hello-world-npm/packages/43752",
                        "https://github.com/octo-org/hello-world-npm/packages/43752?version=1.0.4",
                        null,
                        null,
                        Instant.parse("2019-11-05T22:49:04Z"),
                        Instant.parse("2019-11-05T22:49:04Z"),
                        null,
                        new GitHubPackageVersionMetadata(GitHubPackageType.NPM, null, null)),
                new GitHubPackageVersion(
                        209_672L,
                        "1.0.3",
                        "https://api.github.com/orgs/octo-org/packages/npm/hello-world-npm/versions/209672",
                        "https://github.com/octo-org/hello-world-npm/packages/43752",
                        "https://github.com/octo-org/hello-world-npm/packages/43752?version=1.0.3",
                        null,
                        null,
                        Instant.parse("2019-10-29T15:42:11Z"),
                        Instant.parse("2019-10-29T15:42:12Z"),
                        null,
                        new GitHubPackageVersionMetadata(GitHubPackageType.NPM, null, null)));
    }
}
