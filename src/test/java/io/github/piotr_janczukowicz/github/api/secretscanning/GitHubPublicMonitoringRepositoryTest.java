package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPublicMonitoringRepositoryTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubPublicMonitoringRepositoryBuilder builder = GitHubPublicMonitoringRepositoryBuilder.builder(
                        SecretScanningCatalogFixtures.publicMonitoringRepository())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenFullNameNull() {
        final GitHubPublicMonitoringRepositoryBuilder builder = GitHubPublicMonitoringRepositoryBuilder.builder(
                        SecretScanningCatalogFixtures.publicMonitoringRepository())
                .fullName(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("fullName");
    }
}
