package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationRevisionResponseTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubGetAutomationRevisionResponseBuilder builder = GitHubGetAutomationRevisionResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getAutomationRevisionResponse())
                .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdNull() {
        final GitHubGetAutomationRevisionResponseBuilder builder = GitHubGetAutomationRevisionResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getAutomationRevisionResponse())
                .automationId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationId");
    }

    @Test
    void constructor_shouldThrow_whenChangeTypeNull() {
        final GitHubGetAutomationRevisionResponseBuilder builder = GitHubGetAutomationRevisionResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getAutomationRevisionResponse())
                .changeType(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("changeType");
    }

    @Test
    void constructor_shouldThrow_whenChangedAtNull() {
        final GitHubGetAutomationRevisionResponseBuilder builder = GitHubGetAutomationRevisionResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getAutomationRevisionResponse())
                .changedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("changedAt");
    }
}
