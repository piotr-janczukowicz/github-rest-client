package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPublicMonitoringAlertGistReferenceTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubPublicMonitoringAlertGistReferenceBuilder builder =
                GitHubPublicMonitoringAlertGistReferenceBuilder.builder(
                                SecretScanningCatalogFixtures.publicMonitoringAlertGistReference())
                        .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }
}
