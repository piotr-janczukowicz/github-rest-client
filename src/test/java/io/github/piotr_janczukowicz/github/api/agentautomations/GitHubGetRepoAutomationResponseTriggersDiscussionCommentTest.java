package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoAutomationResponseTriggersDiscussionCommentTest {

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersDiscussionComment())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubGetRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubGetRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubGetRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldAccept_whenCommentTypesRepeat() {
        final GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubGetRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubGetRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT));

        final GitHubGetRepoAutomationResponseTriggersDiscussionComment value = builder.build();

        assertThat(value.commentTypes()).hasSize(2);
    }

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersDiscussionComment())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersDiscussionComment())
                        .categories(List.of("categories", "categories"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldAcceptNull_whenCommentTypesNull() {
        final GitHubGetRepoAutomationResponseTriggersDiscussionComment value =
                GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersDiscussionComment())
                        .commentTypes(null)
                        .build();

        assertThat(value.commentTypes()).isNull();
    }

    @Test
    void constructor_shouldAcceptNull_whenCategoriesNull() {
        final GitHubGetRepoAutomationResponseTriggersDiscussionComment value =
                GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersDiscussionComment())
                        .categories(null)
                        .build();

        assertThat(value.categories()).isNull();
    }
}
