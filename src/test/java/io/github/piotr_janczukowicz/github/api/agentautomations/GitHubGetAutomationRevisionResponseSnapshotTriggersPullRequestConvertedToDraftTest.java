package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestConvertedToDraftTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestConvertedToDraftBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestConvertedToDraft())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestConvertedToDraftBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestConvertedToDraft())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestConvertedToDraftBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestConvertedToDraft())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestConvertedToDraft value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestConvertedToDraft())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
