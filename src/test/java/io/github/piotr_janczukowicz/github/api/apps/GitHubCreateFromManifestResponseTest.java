package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateFromManifestResponseTest {

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubCreateFromManifestResponseBuilder builder = GitHubCreateFromManifestResponseBuilder.builder(
                        AppsCatalogFixtures.createFromManifestResponse())
                .nodeId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenClientIdNull() {
        final GitHubCreateFromManifestResponseBuilder builder = GitHubCreateFromManifestResponseBuilder.builder(
                        AppsCatalogFixtures.createFromManifestResponse())
                .clientId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("clientId");
    }

    @Test
    void constructor_shouldThrow_whenClientSecretNull() {
        final GitHubCreateFromManifestResponseBuilder builder = GitHubCreateFromManifestResponseBuilder.builder(
                        AppsCatalogFixtures.createFromManifestResponse())
                .clientSecret(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("clientSecret");
    }

    @Test
    void constructor_shouldThrow_whenPemNull() {
        final GitHubCreateFromManifestResponseBuilder builder = GitHubCreateFromManifestResponseBuilder.builder(
                        AppsCatalogFixtures.createFromManifestResponse())
                .pem(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("pem");
    }

    @Test
    void constructor_shouldThrow_whenOwnerNull() {
        final GitHubCreateFromManifestResponseBuilder builder = GitHubCreateFromManifestResponseBuilder.builder(
                        AppsCatalogFixtures.createFromManifestResponse())
                .owner(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("owner");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubCreateFromManifestResponseBuilder builder = GitHubCreateFromManifestResponseBuilder.builder(
                        AppsCatalogFixtures.createFromManifestResponse())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenExternalUrlNull() {
        final GitHubCreateFromManifestResponseBuilder builder = GitHubCreateFromManifestResponseBuilder.builder(
                        AppsCatalogFixtures.createFromManifestResponse())
                .externalUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("externalUrl");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final GitHubCreateFromManifestResponseBuilder builder = GitHubCreateFromManifestResponseBuilder.builder(
                        AppsCatalogFixtures.createFromManifestResponse())
                .htmlUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubCreateFromManifestResponseBuilder builder = GitHubCreateFromManifestResponseBuilder.builder(
                        AppsCatalogFixtures.createFromManifestResponse())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubCreateFromManifestResponseBuilder builder = GitHubCreateFromManifestResponseBuilder.builder(
                        AppsCatalogFixtures.createFromManifestResponse())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenPermissionsNull() {
        final GitHubCreateFromManifestResponseBuilder builder = GitHubCreateFromManifestResponseBuilder.builder(
                        AppsCatalogFixtures.createFromManifestResponse())
                .permissions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("permissions");
    }

    @Test
    void constructor_shouldThrow_whenEventsNull() {
        final GitHubCreateFromManifestResponseBuilder builder = GitHubCreateFromManifestResponseBuilder.builder(
                        AppsCatalogFixtures.createFromManifestResponse())
                .events(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("events");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubCreateFromManifestResponse value = AppsCatalogFixtures.createFromManifestResponse();

        final String text = value.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("clientSecret=<redacted>");
            softly.assertThat(text).doesNotContain(value.clientSecret());
            softly.assertThat(text).contains("webhookSecret=<redacted>");
            softly.assertThat(text).doesNotContain(value.webhookSecret());
            softly.assertThat(text).contains("pem=<redacted>");
            softly.assertThat(text).doesNotContain(value.pem());
        });
    }
}
