package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentTest {

    @Test
    void constructor_shouldThrow_whenCommentTypesNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestComment())
                        .commentTypes(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestComment())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestComment())
                        .commentTypes(List.of(
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT,
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT,
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestComment())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestComment())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestComment())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldAccept_whenCommentTypesRepeat() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestComment value =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestComment())
                        .commentTypes(List.of(
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT,
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT))
                        .build();

        assertThat(value.commentTypes()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestComment value =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestComment())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
