package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationResponseTriggersDiscussionCommentTest {

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubEditRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubEditRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersDiscussionComment())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubEditRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubEditRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubEditRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubEditRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubEditRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldAccept_whenCommentTypesRepeat() {
        final GitHubEditRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubEditRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubEditRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubEditRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT));

        final GitHubEditRepoAutomationResponseTriggersDiscussionComment value = builder.build();

        assertThat(value.commentTypes()).hasSize(2);
    }

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubEditRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubEditRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersDiscussionComment())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubEditRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubEditRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersDiscussionComment())
                        .categories(List.of("categories", "categories"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldAcceptNull_whenCommentTypesNull() {
        final GitHubEditRepoAutomationResponseTriggersDiscussionComment value =
                GitHubEditRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersDiscussionComment())
                        .commentTypes(null)
                        .build();

        assertThat(value.commentTypes()).isNull();
    }

    @Test
    void constructor_shouldAcceptNull_whenCategoriesNull() {
        final GitHubEditRepoAutomationResponseTriggersDiscussionComment value =
                GitHubEditRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersDiscussionComment())
                        .categories(null)
                        .build();

        assertThat(value.categories()).isNull();
    }
}
