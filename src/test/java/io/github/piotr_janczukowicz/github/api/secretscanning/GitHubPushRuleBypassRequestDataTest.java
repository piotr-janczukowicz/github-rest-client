package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPushRuleBypassRequestDataTest {

    @Test
    void constructor_shouldKeepRuleType_whenBuilt() {
        final GitHubPushRuleBypassRequestData sut = SecretScanningCatalogFixtures.pushRuleBypassRequestData();

        assertThat(sut.ruleType()).isEqualTo("commit_message_pattern");
    }
}
