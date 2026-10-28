package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPushRuleBypassRequestRepositoryTest {

    @Test
    void constructor_shouldKeepFullName_whenBuilt() {
        final GitHubPushRuleBypassRequestRepository sut =
                SecretScanningCatalogFixtures.pushRuleBypassRequestRepository();

        assertThat(sut.fullName()).isEqualTo("octo-org/smile");
    }
}
