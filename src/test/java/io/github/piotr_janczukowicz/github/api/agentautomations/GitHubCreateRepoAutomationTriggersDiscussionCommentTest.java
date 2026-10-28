package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTriggersDiscussionCommentTest {

    @Test
    void constructor_shouldLeaveCommentTypesNull_whenNull() {
        final GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder builder =
                GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionComment())
                        .commentTypes(null);

        final GitHubCreateRepoAutomationTriggersDiscussionComment sut = builder.build();

        assertThat(sut.commentTypes()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder builder =
                GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionComment())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder builder =
                GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubCreateRepoAutomationTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubCreateRepoAutomationTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubCreateRepoAutomationTriggersDiscussionCommentCommentType.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesDuplicated() {
        final GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder builder =
                GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubCreateRepoAutomationTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubCreateRepoAutomationTriggersDiscussionCommentCommentType.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldLeaveCategoriesNull_whenNull() {
        final GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder builder =
                GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionComment())
                        .categories(null);

        final GitHubCreateRepoAutomationTriggersDiscussionComment sut = builder.build();

        assertThat(sut.categories()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder builder =
                GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionComment())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder builder =
                GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionComment())
                        .categories(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }
}
