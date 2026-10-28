package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationResponseRepositoryTest {

    @Test
    void constructor_shouldThrow_whenOwnerNull() {
        final GitHubGetAutomationResponseRepositoryBuilder builder =
                GitHubGetAutomationResponseRepositoryBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseRepository())
                        .owner(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("owner");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubGetAutomationResponseRepositoryBuilder builder =
                GitHubGetAutomationResponseRepositoryBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseRepository())
                        .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
