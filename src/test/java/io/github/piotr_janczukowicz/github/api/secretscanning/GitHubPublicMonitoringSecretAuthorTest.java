package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPublicMonitoringSecretAuthorTest {

    @Test
    void constructor_shouldThrow_whenLoginNull() {
        final GitHubPublicMonitoringSecretAuthorBuilder builder = GitHubPublicMonitoringSecretAuthorBuilder.builder(
                        SecretScanningCatalogFixtures.publicMonitoringSecretAuthor())
                .login(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("login");
    }
}
