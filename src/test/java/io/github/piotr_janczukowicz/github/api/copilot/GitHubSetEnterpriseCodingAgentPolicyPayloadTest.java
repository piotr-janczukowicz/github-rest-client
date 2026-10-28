package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetEnterpriseCodingAgentPolicyPayloadTest {

    @Test
    void constructor_shouldThrow_whenPolicyStateNull() {
        final Throwable thrown = catchThrowable(() -> GitHubSetEnterpriseCodingAgentPolicyPayloadBuilder.builder(
                        CopilotCatalogFixtures.setEnterpriseCodingAgentPolicyPayload())
                .policyState(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("policyState");
    }
}
