package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationPullRequestCommentTriggerTest {

    @Test
    void constructor_shouldThrow_whenCommentTypesNull() {
        final GitHubAutomationPullRequestCommentTriggerBuilder builder =
                GitHubAutomationPullRequestCommentTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestCommentTrigger())
                        .commentTypes(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubAutomationPullRequestCommentTriggerBuilder builder =
                GitHubAutomationPullRequestCommentTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestCommentTrigger())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubAutomationPullRequestCommentTriggerBuilder builder =
                GitHubAutomationPullRequestCommentTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestCommentTrigger())
                        .commentTypes(List.of(
                                GitHubAutomationPullRequestCommentCommentType.TIMELINE_COMMENT,
                                GitHubAutomationPullRequestCommentCommentType.TIMELINE_COMMENT,
                                GitHubAutomationPullRequestCommentCommentType.TIMELINE_COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldAccept_whenCommentTypesRepeat() {
        final GitHubAutomationPullRequestCommentTriggerBuilder builder =
                GitHubAutomationPullRequestCommentTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestCommentTrigger())
                        .commentTypes(List.of(
                                GitHubAutomationPullRequestCommentCommentType.TIMELINE_COMMENT,
                                GitHubAutomationPullRequestCommentCommentType.TIMELINE_COMMENT));

        final GitHubAutomationPullRequestCommentTrigger value = builder.build();

        assertThat(value.commentTypes()).hasSize(2);
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubAutomationPullRequestCommentTriggerBuilder builder =
                GitHubAutomationPullRequestCommentTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestCommentTrigger())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubAutomationPullRequestCommentTriggerBuilder builder =
                GitHubAutomationPullRequestCommentTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestCommentTrigger())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubAutomationPullRequestCommentTriggerBuilder builder =
                GitHubAutomationPullRequestCommentTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestCommentTrigger())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubAutomationPullRequestCommentTrigger value =
                GitHubAutomationPullRequestCommentTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestCommentTrigger())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
