package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningLocationActionsLogTest {

    @Test
    void constructor_shouldThrow_whenPathNull() {
        final GitHubSecretScanningLocationActionsLogBuilder builder =
                GitHubSecretScanningLocationActionsLogBuilder.builder(
                                SecretScanningCatalogFixtures.secretScanningLocationActionsLog())
                        .path(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }
}
