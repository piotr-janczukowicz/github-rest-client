package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationRevisionsRevisionSnapshotTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationRevisionsRevisionSnapshot())
                        .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationRevisionsRevisionSnapshot())
                        .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenPromptNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationRevisionsRevisionSnapshot())
                        .prompt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("prompt");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationRevisionsRevisionSnapshot())
                        .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationRevisionsRevisionSnapshot())
                        .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenCreatedByNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationRevisionsRevisionSnapshot())
                        .createdBy(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdBy");
    }
}
