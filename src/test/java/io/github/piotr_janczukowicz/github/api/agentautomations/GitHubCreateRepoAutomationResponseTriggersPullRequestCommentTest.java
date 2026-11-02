package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationResponseTriggersPullRequestCommentTest {

    @Test
    void constructor_shouldThrow_whenCommentTypesNull() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestCommentBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestComment())
                        .commentTypes(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestCommentBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestComment())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestCommentBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestComment())
                        .commentTypes(List.of(
                                GitHubCreateRepoAutomationResponseTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT,
                                GitHubCreateRepoAutomationResponseTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT,
                                GitHubCreateRepoAutomationResponseTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldAccept_whenCommentTypesRepeat() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestCommentBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestComment())
                        .commentTypes(List.of(
                                GitHubCreateRepoAutomationResponseTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT,
                                GitHubCreateRepoAutomationResponseTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT));

        final GitHubCreateRepoAutomationResponseTriggersPullRequestComment value = builder.build();

        assertThat(value.commentTypes()).hasSize(2);
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestCommentBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestComment())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestCommentBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestComment())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestCommentBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestComment())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestComment value =
                GitHubCreateRepoAutomationResponseTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestComment())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
