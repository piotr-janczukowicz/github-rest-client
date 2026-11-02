package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationTasksTaskClientTest {

    @Test
    void constructor_shouldThrow_whenIntegrationIdNull() {
        final GitHubListRepoAutomationTasksTaskClientBuilder builder =
                GitHubListRepoAutomationTasksTaskClientBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationTasksTaskClient())
                        .integrationId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("integrationId");
    }

    @Test
    void constructor_shouldThrow_whenSlugNull() {
        final GitHubListRepoAutomationTasksTaskClientBuilder builder =
                GitHubListRepoAutomationTasksTaskClientBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationTasksTaskClient())
                        .slug(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("slug");
    }
}
