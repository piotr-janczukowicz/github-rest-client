package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBypassResponseTest {

    @Test
    void constructor_shouldKeepStatus_whenBuilt() {
        final GitHubBypassResponse sut = SecretScanningCatalogFixtures.bypassResponse();

        assertThat(sut.status()).isEqualTo(GitHubBypassResponseStatus.APPROVED);
    }
}
