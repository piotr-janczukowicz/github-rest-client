package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPushRuleBypassRequestOrganizationTest {

    @Test
    void constructor_shouldKeepName_whenBuilt() {
        final GitHubPushRuleBypassRequestOrganization sut =
                SecretScanningCatalogFixtures.pushRuleBypassRequestOrganization();

        assertThat(sut.name()).isEqualTo("octo-org");
    }
}
