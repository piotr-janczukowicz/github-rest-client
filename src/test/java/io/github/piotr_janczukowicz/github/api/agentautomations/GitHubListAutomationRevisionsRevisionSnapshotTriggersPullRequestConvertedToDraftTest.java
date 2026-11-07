package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestConvertedToDraftTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestConvertedToDraftBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestConvertedToDraft())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestConvertedToDraftBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestConvertedToDraft())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestConvertedToDraftBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestConvertedToDraft())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestConvertedToDraft value =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestConvertedToDraft())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
