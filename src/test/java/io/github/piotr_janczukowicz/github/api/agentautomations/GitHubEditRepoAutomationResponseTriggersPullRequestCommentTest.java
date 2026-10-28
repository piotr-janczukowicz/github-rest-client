package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationResponseTriggersPullRequestCommentTest {

    @Test
    void constructor_shouldThrow_whenCommentTypesNull() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestCommentBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersPullRequestComment())
                        .commentTypes(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestCommentBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersPullRequestComment())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestCommentBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersPullRequestComment())
                        .commentTypes(List.of(
                                GitHubEditRepoAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT,
                                GitHubEditRepoAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT,
                                GitHubEditRepoAutomationResponseTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesDuplicated() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestCommentBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersPullRequestComment())
                        .commentTypes(List.of(
                                GitHubEditRepoAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT,
                                GitHubEditRepoAutomationResponseTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }
}
