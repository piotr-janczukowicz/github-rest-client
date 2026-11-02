package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationsAutomationTriggersDiscussionCommentTest {

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
    void constructor_shouldAccept_whenCommentTypesRepeat() {
        final GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType.COMMENT));

        final GitHubListRepoAutomationsAutomationTriggersDiscussionComment value = builder.build();

        assertThat(value.commentTypes()).hasSize(2);
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
                        .categories(List.of("categories", "categories"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldAcceptNull_whenCommentTypesNull() {
        final GitHubListRepoAutomationsAutomationTriggersDiscussionComment value =
                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersDiscussionComment())
                        .commentTypes(null)
                        .build();

        assertThat(value.commentTypes()).isNull();
    }

    @Test
    void constructor_shouldAcceptNull_whenCategoriesNull() {
        final GitHubListRepoAutomationsAutomationTriggersDiscussionComment value =
                GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersDiscussionComment())
                        .categories(null)
                        .build();

        assertThat(value.categories()).isNull();
    }
}
