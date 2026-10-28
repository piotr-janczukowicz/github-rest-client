package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDismissalRequestResponseTest {

    @Test
    void constructor_shouldKeepStatus_whenBuilt() {
        final GitHubDismissalRequestResponse sut = CodeScanningCatalogFixtures.dismissalRequestResponse();

        assertThat(sut.status()).isEqualTo(GitHubDismissalRequestResponseStatus.APPROVED);
    }
}
