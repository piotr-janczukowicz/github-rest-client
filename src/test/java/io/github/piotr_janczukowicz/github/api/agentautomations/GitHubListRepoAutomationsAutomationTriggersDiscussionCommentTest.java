package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationsAutomationTriggersDiscussionCommentTest {

    @Test
    void constructor_shouldLeaveCommentTypesNull_whenNull() {
        final GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersDiscussionComment())
                        .commentTypes(null);

        final GitHubListRepoAutomationsAutomationTriggersDiscussionComment sut = builder.build();

        assertThat(sut.commentTypes()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersDiscussionComment())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesDuplicated() {
        final GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldLeaveCategoriesNull_whenNull() {
        final GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersDiscussionComment())
                        .categories(null);

        final GitHubListRepoAutomationsAutomationTriggersDiscussionComment sut = builder.build();

        assertThat(sut.categories()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersDiscussionComment())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersDiscussionComment())
                        .categories(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }
}
