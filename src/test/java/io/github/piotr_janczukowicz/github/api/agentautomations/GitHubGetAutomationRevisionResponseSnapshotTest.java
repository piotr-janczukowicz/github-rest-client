package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationRevisionResponseSnapshotTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubGetAutomationRevisionResponseSnapshotBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationRevisionResponseSnapshot())
                        .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubGetAutomationRevisionResponseSnapshotBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationRevisionResponseSnapshot())
                        .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenPromptNull() {
        final GitHubGetAutomationRevisionResponseSnapshotBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationRevisionResponseSnapshot())
                        .prompt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("prompt");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubGetAutomationRevisionResponseSnapshotBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationRevisionResponseSnapshot())
                        .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubGetAutomationRevisionResponseSnapshotBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationRevisionResponseSnapshot())
                        .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenCreatedByNull() {
        final GitHubGetAutomationRevisionResponseSnapshotBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationRevisionResponseSnapshot())
                        .createdBy(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdBy");
    }
}
