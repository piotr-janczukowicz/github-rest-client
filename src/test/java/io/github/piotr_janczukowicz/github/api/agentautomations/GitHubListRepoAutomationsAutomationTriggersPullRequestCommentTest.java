package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationsAutomationTriggersPullRequestCommentTest {

    @Test
    void constructor_shouldThrow_whenCommentTypesNull() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestCommentBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestComment())
                        .commentTypes(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestCommentBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestComment())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestCommentBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestComment())
                        .commentTypes(List.of(
                                GitHubListRepoAutomationsAutomationTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT,
                                GitHubListRepoAutomationsAutomationTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT,
                                GitHubListRepoAutomationsAutomationTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldAccept_whenCommentTypesRepeat() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestCommentBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestComment())
                        .commentTypes(List.of(
                                GitHubListRepoAutomationsAutomationTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT,
                                GitHubListRepoAutomationsAutomationTriggersPullRequestCommentCommentType
                                        .TIMELINE_COMMENT));

        final GitHubListRepoAutomationsAutomationTriggersPullRequestComment value = builder.build();

        assertThat(value.commentTypes()).hasSize(2);
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestCommentBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestComment())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestCommentBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestComment())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestCommentBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestComment())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestComment value =
                GitHubListRepoAutomationsAutomationTriggersPullRequestCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestComment())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
