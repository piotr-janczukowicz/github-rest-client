package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationResponseTriggersDiscussionCommentTest {

    @Test
    void constructor_shouldLeaveCommentTypesNull_whenNull() {
        final GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersDiscussionComment())
                        .commentTypes(null);

        final GitHubCreateRepoAutomationResponseTriggersDiscussionComment sut = builder.build();

        assertThat(sut.commentTypes()).isNull();
    }

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
    void constructor_shouldThrow_whenCommentTypesDuplicated() {
        final GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldLeaveCategoriesNull_whenNull() {
        final GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersDiscussionComment())
                        .categories(null);

        final GitHubCreateRepoAutomationResponseTriggersDiscussionComment sut = builder.build();

        assertThat(sut.categories()).isNull();
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
                        .categories(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }
}
