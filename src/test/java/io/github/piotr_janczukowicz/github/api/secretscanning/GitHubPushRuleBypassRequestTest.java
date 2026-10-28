package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPushRuleBypassRequestTest {

    @Test
    void constructor_shouldLeaveDataNull_whenNull() {
        final GitHubPushRuleBypassRequest sut = GitHubPushRuleBypassRequestBuilder.builder(
                        SecretScanningCatalogFixtures.pushRuleBypassRequest())
                .data(null)
                .build();

        assertThat(sut.data()).isNull();
    }

    @Test
    void constructor_shouldLeaveResponsesNull_whenNull() {
        final GitHubPushRuleBypassRequest sut = GitHubPushRuleBypassRequestBuilder.builder(
                        SecretScanningCatalogFixtures.pushRuleBypassRequest())
                .responses(null)
                .build();

        assertThat(sut.responses()).isNull();
    }
}
