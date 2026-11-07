package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentTest {

    @Test
    void constructor_shouldThrow_whenCommentTypesNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestComment())
                        .commentTypes(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestComment())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestComment())
                        .commentTypes(List.of(
                                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT,
                                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT,
                                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestComment())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestComment())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestComment())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldAccept_whenCommentTypesRepeat() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestComment value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestComment())
                        .commentTypes(List.of(
                                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT,
                                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT))
                        .build();

        assertThat(value.commentTypes()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestComment value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestComment())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
