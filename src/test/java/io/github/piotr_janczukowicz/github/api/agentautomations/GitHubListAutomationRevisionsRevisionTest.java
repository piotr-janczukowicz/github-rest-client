package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationRevisionsRevisionTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubListAutomationRevisionsRevisionBuilder builder =
                GitHubListAutomationRevisionsRevisionBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationRevisionsRevision())
                        .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdNull() {
        final GitHubListAutomationRevisionsRevisionBuilder builder =
                GitHubListAutomationRevisionsRevisionBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationRevisionsRevision())
                        .automationId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationId");
    }

    @Test
    void constructor_shouldThrow_whenChangeTypeNull() {
        final GitHubListAutomationRevisionsRevisionBuilder builder =
                GitHubListAutomationRevisionsRevisionBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationRevisionsRevision())
                        .changeType(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("changeType");
    }

    @Test
    void constructor_shouldThrow_whenChangedAtNull() {
        final GitHubListAutomationRevisionsRevisionBuilder builder =
                GitHubListAutomationRevisionsRevisionBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationRevisionsRevision())
                        .changedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("changedAt");
    }
}
