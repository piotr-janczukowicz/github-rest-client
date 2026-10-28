package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationResponseTriggersPullRequestCommentTest {

    @Test
    void constructor_shouldThrow_whenCommentTypesNull() {
        final GitHubGetAutomationResponseTriggersPullRequestCommentBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersPullRequestComment())
                        .commentTypes(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubGetAutomationResponseTriggersPullRequestCommentBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersPullRequestComment())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubGetAutomationResponseTriggersPullRequestCommentBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersPullRequestComment())
                        .commentTypes(List.of(
                                GitHubGetAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT,
                                GitHubGetAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT,
                                GitHubGetAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesDuplicated() {
        final GitHubGetAutomationResponseTriggersPullRequestCommentBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersPullRequestComment())
                        .commentTypes(List.of(
                                GitHubGetAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT,
                                GitHubGetAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }
}
