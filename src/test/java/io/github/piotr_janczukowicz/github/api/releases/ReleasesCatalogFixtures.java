package io.github.piotr_janczukowicz.github.api.releases;

import io.github.piotr_janczukowicz.github.api.search.GitHubReactionRollup;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.time.Instant;
import java.util.List;

public final class ReleasesCatalogFixtures {

    private ReleasesCatalogFixtures() {}

    public static GitHubReleaseAsset releaseAsset() {
        return new GitHubReleaseAsset(
                "https://api.github.com/repos/octocat/Hello-World/releases/assets/1",
                "https://github.com/octocat/Hello-World/releases/download/v1.0.0/example.zip",
                1L,
                "MDEyOlJlbGVhc2VBc3NldDE=",
                "example.zip",
                "short description",
                GitHubReleaseAssetState.UPLOADED,
                "application/zip",
                1024L,
                "sha256:2151b604e3429bff440b9fbc03eb3617bc2603cda96c95b9bb05277f9ddba255",
                42L,
                Instant.parse("2013-02-27T19:35:22Z"),
                Instant.parse("2013-02-27T19:35:22Z"),
                UsersCatalogFixtures.octocat());
    }

    static GitHubCreateReleasePayload createReleasePayload() {
        return new GitHubCreateReleasePayload("v1.0.0", null, null, null, null, null, null, null, null);
    }

    static GitHubGenerateReleaseNotesPayload generateReleaseNotesPayload() {
        return new GitHubGenerateReleaseNotesPayload("v1.0.0", null, null, null);
    }

    static GitHubUpdateReleaseAssetPayload updateReleaseAssetPayload() {
        return new GitHubUpdateReleaseAssetPayload("foo.zip", null, null);
    }

    static GitHubUpdateReleasePayload updateReleasePayload() {
        return new GitHubUpdateReleasePayload(null, null, "v2.0.0", null, null, null, null, null);
    }

    static GitHubRelease release() {
        return new GitHubRelease(
                "https://api.github.com/repos/octocat/Hello-World/releases/1",
                "https://github.com/octocat/Hello-World/releases/v1.0.0",
                "https://api.github.com/repos/octocat/Hello-World/releases/1/assets",
                "https://uploads.github.com/repos/octocat/Hello-World/releases/1/assets{?name,label}",
                "https://api.github.com/repos/octocat/Hello-World/tarball/v1.0.0",
                "https://api.github.com/repos/octocat/Hello-World/zipball/v1.0.0",
                1L,
                "MDc6UmVsZWFzZTE=",
                "v1.0.0",
                "master",
                "v1.0.0",
                "Description of the release",
                false,
                false,
                false,
                Instant.parse("2013-02-27T19:35:22Z"),
                Instant.parse("2013-02-27T19:35:22Z"),
                Instant.parse("2013-02-27T19:35:22Z"),
                UsersCatalogFixtures.octocat(),
                List.of(releaseAsset()),
                "<p>Description of the release</p>",
                "Description of the release",
                2,
                "https://github.com/octocat/Hello-World/discussions/90",
                new GitHubReactionRollup(
                        "https://api.github.com/repos/octocat/Hello-World/releases/1/reactions",
                        5,
                        3,
                        1,
                        0,
                        0,
                        1,
                        0,
                        0,
                        0));
    }

    static GitHubReleaseNotesContent releaseNotesContent() {
        return new GitHubReleaseNotesContent("Release v1.0.0 is now available!", "## Changes\n\n* Fixed bugs");
    }
}
