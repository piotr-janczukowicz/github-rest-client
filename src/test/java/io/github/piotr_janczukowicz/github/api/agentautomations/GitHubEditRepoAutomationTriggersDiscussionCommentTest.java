package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationTriggersDiscussionCommentTest {

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
                        .categories(List.of("categories", "categories"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldAcceptNull_whenCommentTypesNull() {
        final GitHubEditRepoAutomationTriggersDiscussionComment value =
                GitHubEditRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersDiscussionComment())
                        .commentTypes(null)
                        .build();

        assertThat(value.commentTypes()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenCommentTypesGiven() {
        final List<GitHubEditRepoAutomationTriggersDiscussionCommentCommentType> source =
                new ArrayList<>(List.of(GitHubEditRepoAutomationTriggersDiscussionCommentCommentType.COMMENT));
        final GitHubEditRepoAutomationTriggersDiscussionComment value =
                GitHubEditRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersDiscussionComment())
                        .commentTypes(source)
                        .build();

        source.add(GitHubEditRepoAutomationTriggersDiscussionCommentCommentType.COMMENT);

        assertThat(value.commentTypes()).hasSize(source.size() - 1);
    }

    @Test
    void constructor_shouldAcceptNull_whenCategoriesNull() {
        final GitHubEditRepoAutomationTriggersDiscussionComment value =
                GitHubEditRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersDiscussionComment())
                        .categories(null)
                        .build();

        assertThat(value.categories()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenCategoriesGiven() {
        final List<String> source = new ArrayList<>(List.of("categories"));
        final GitHubEditRepoAutomationTriggersDiscussionComment value =
                GitHubEditRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersDiscussionComment())
                        .categories(source)
                        .build();

        source.add("categories");

        assertThat(value.categories()).hasSize(source.size() - 1);
    }
}
