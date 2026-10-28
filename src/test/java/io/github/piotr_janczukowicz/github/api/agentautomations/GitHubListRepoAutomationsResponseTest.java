package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationsResponseTest {

    @Test
    void constructor_shouldThrow_whenAutomationsNull() {
        final GitHubListRepoAutomationsResponseBuilder builder = GitHubListRepoAutomationsResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.listRepoAutomationsResponse())
                .automations(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automations");
    }
}
