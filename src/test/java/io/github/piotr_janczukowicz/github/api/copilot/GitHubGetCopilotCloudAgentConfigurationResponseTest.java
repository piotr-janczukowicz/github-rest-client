package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetCopilotCloudAgentConfigurationResponseTest {

    @Test
    void constructor_shouldLeaveMcpConfigurationNull_whenNull() {
        final GitHubGetCopilotCloudAgentConfigurationResponse value =
                GitHubGetCopilotCloudAgentConfigurationResponseBuilder.builder(
                                CopilotCatalogFixtures.getCopilotCloudAgentConfigurationResponse())
                        .mcpConfiguration(null)
                        .build();

        assertThat(value.mcpConfiguration()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenEnabledToolsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetCopilotCloudAgentConfigurationResponseBuilder.builder(
                        CopilotCatalogFixtures.getCopilotCloudAgentConfigurationResponse())
                .enabledTools(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enabledTools");
    }

    @Test
    void constructor_shouldThrow_whenCustomAllowlistNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetCopilotCloudAgentConfigurationResponseBuilder.builder(
                        CopilotCatalogFixtures.getCopilotCloudAgentConfigurationResponse())
                .customAllowlist(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("customAllowlist");
    }
}
