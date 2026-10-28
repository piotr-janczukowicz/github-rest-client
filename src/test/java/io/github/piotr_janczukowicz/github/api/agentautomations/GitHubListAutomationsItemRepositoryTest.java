package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemRepositoryTest {

    @Test
    void constructor_shouldThrow_whenOwnerNull() {
        final GitHubListAutomationsItemRepositoryBuilder builder = GitHubListAutomationsItemRepositoryBuilder.builder(
                        AgentAutomationsCatalogFixtures.listAutomationsItemRepository())
                .owner(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("owner");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubListAutomationsItemRepositoryBuilder builder = GitHubListAutomationsItemRepositoryBuilder.builder(
                        AgentAutomationsCatalogFixtures.listAutomationsItemRepository())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
