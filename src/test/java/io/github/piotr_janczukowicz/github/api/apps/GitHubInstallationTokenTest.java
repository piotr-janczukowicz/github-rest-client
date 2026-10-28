package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubInstallationTokenTest {

    @Test
    void constructor_shouldThrow_whenTokenNull() {
        final GitHubInstallationTokenBuilder builder = GitHubInstallationTokenBuilder.builder(
                        AppsCatalogFixtures.installationToken())
                .token(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("token");
    }

    @Test
    void constructor_shouldThrow_whenExpiresAtNull() {
        final GitHubInstallationTokenBuilder builder = GitHubInstallationTokenBuilder.builder(
                        AppsCatalogFixtures.installationToken())
                .expiresAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("expiresAt");
    }

    @Test
    void constructor_shouldKeepNullRepositories_whenNull() {
        final GitHubInstallationTokenBuilder builder = GitHubInstallationTokenBuilder.builder(
                        AppsCatalogFixtures.installationToken())
                .repositories(null);

        final GitHubInstallationToken value = builder.build();

        assertThat(value.repositories()).isNull();
    }

    @Test
    void constructor_shouldKeepNullSingleFilePaths_whenNull() {
        final GitHubInstallationTokenBuilder builder = GitHubInstallationTokenBuilder.builder(
                        AppsCatalogFixtures.installationToken())
                .singleFilePaths(null);

        final GitHubInstallationToken value = builder.build();

        assertThat(value.singleFilePaths()).isNull();
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubInstallationToken value = AppsCatalogFixtures.installationToken();

        final String text = value.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("token=<redacted>");
            softly.assertThat(text).doesNotContain(value.token());
        });
    }
}
