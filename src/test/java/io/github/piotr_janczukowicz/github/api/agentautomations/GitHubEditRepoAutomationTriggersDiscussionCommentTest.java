package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationTriggersDiscussionCommentTest {

    @Test
    void constructor_shouldLeaveCommentTypesNull_whenNull() {
        final GitHubEditRepoAutomationTriggersDiscussionCommentBuilder builder =
                GitHubEditRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersDiscussionComment())
                        .commentTypes(null);

        final GitHubEditRepoAutomationTriggersDiscussionComment sut = builder.build();

        assertThat(sut.commentTypes()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubEditRepoAutomationTriggersDiscussionCommentBuilder builder =
                GitHubEditRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersDiscussionComment())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubEditRepoAutomationTriggersDiscussionCommentBuilder builder =
                GitHubEditRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubEditRepoAutomationTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubEditRepoAutomationTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubEditRepoAutomationTriggersDiscussionCommentCommentType.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesDuplicated() {
        final GitHubEditRepoAutomationTriggersDiscussionCommentBuilder builder =
                GitHubEditRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubEditRepoAutomationTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubEditRepoAutomationTriggersDiscussionCommentCommentType.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldLeaveCategoriesNull_whenNull() {
        final GitHubEditRepoAutomationTriggersDiscussionCommentBuilder builder =
                GitHubEditRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersDiscussionComment())
                        .categories(null);

        final GitHubEditRepoAutomationTriggersDiscussionComment sut = builder.build();

        assertThat(sut.categories()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubEditRepoAutomationTriggersDiscussionCommentBuilder builder =
                GitHubEditRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersDiscussionComment())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubEditRepoAutomationTriggersDiscussionCommentBuilder builder =
                GitHubEditRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersDiscussionComment())
                        .categories(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }
}
