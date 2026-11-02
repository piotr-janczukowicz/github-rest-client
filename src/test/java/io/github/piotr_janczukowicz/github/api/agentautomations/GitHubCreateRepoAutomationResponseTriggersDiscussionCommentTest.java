package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationResponseTriggersDiscussionCommentTest {

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersDiscussionComment())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldAccept_whenCommentTypesRepeat() {
        final GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT));

        final GitHubCreateRepoAutomationResponseTriggersDiscussionComment value = builder.build();

        assertThat(value.commentTypes()).hasSize(2);
    }

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersDiscussionComment())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersDiscussionComment())
                        .categories(List.of("categories", "categories"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldAcceptNull_whenCommentTypesNull() {
        final GitHubCreateRepoAutomationResponseTriggersDiscussionComment value =
                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersDiscussionComment())
                        .commentTypes(null)
                        .build();

        assertThat(value.commentTypes()).isNull();
    }

    @Test
    void constructor_shouldAcceptNull_whenCategoriesNull() {
        final GitHubCreateRepoAutomationResponseTriggersDiscussionComment value =
                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersDiscussionComment())
                        .categories(null)
                        .build();

        assertThat(value.categories()).isNull();
    }
}
