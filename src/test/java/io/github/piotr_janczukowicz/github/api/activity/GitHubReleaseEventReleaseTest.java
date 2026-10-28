package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReleaseEventReleaseTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubReleaseEventReleaseBuilder builder = GitHubReleaseEventReleaseBuilder.builder(
                        ActivityCatalogFixtures.releaseEventRelease())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final GitHubReleaseEventReleaseBuilder builder = GitHubReleaseEventReleaseBuilder.builder(
                        ActivityCatalogFixtures.releaseEventRelease())
                .htmlUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenAssetsUrlNull() {
        final GitHubReleaseEventReleaseBuilder builder = GitHubReleaseEventReleaseBuilder.builder(
                        ActivityCatalogFixtures.releaseEventRelease())
                .assetsUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("assetsUrl");
    }

    @Test
    void constructor_shouldThrow_whenUploadUrlNull() {
        final GitHubReleaseEventReleaseBuilder builder = GitHubReleaseEventReleaseBuilder.builder(
                        ActivityCatalogFixtures.releaseEventRelease())
                .uploadUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("uploadUrl");
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubReleaseEventReleaseBuilder builder = GitHubReleaseEventReleaseBuilder.builder(
                        ActivityCatalogFixtures.releaseEventRelease())
                .nodeId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenTagNameNull() {
        final GitHubReleaseEventReleaseBuilder builder = GitHubReleaseEventReleaseBuilder.builder(
                        ActivityCatalogFixtures.releaseEventRelease())
                .tagName(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tagName");
    }

    @Test
    void constructor_shouldThrow_whenTargetCommitishNull() {
        final GitHubReleaseEventReleaseBuilder builder = GitHubReleaseEventReleaseBuilder.builder(
                        ActivityCatalogFixtures.releaseEventRelease())
                .targetCommitish(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("targetCommitish");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubReleaseEventReleaseBuilder builder = GitHubReleaseEventReleaseBuilder.builder(
                        ActivityCatalogFixtures.releaseEventRelease())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenAuthorNull() {
        final GitHubReleaseEventReleaseBuilder builder = GitHubReleaseEventReleaseBuilder.builder(
                        ActivityCatalogFixtures.releaseEventRelease())
                .author(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("author");
    }

    @Test
    void constructor_shouldThrow_whenAssetsNull() {
        final GitHubReleaseEventReleaseBuilder builder = GitHubReleaseEventReleaseBuilder.builder(
                        ActivityCatalogFixtures.releaseEventRelease())
                .assets(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("assets");
    }
}
