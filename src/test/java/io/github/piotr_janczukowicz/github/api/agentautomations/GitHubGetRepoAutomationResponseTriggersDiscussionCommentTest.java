package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoAutomationResponseTriggersDiscussionCommentTest {

    @Test
    void constructor_shouldLeaveCommentTypesNull_whenNull() {
        final GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersDiscussionComment())
                        .commentTypes(null);

        final GitHubGetRepoAutomationResponseTriggersDiscussionComment sut = builder.build();

        assertThat(sut.commentTypes()).isNull();
    }

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
    void constructor_shouldThrow_whenCommentTypesDuplicated() {
        final GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubGetRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubGetRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldLeaveCategoriesNull_whenNull() {
        final GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersDiscussionComment())
                        .categories(null);

        final GitHubGetRepoAutomationResponseTriggersDiscussionComment sut = builder.build();

        assertThat(sut.categories()).isNull();
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
                        .categories(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }
}
