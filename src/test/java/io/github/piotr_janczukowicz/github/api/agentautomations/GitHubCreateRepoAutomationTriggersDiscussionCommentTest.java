package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTriggersDiscussionCommentTest {

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
                        .categories(List.of("categories", "categories"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldAcceptNull_whenCommentTypesNull() {
        final GitHubCreateRepoAutomationTriggersDiscussionComment value =
                GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionComment())
                        .commentTypes(null)
                        .build();

        assertThat(value.commentTypes()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenCommentTypesGiven() {
        final List<GitHubCreateRepoAutomationTriggersDiscussionCommentCommentType> source =
                new ArrayList<>(List.of(GitHubCreateRepoAutomationTriggersDiscussionCommentCommentType.COMMENT));
        final GitHubCreateRepoAutomationTriggersDiscussionComment value =
                GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionComment())
                        .commentTypes(source)
                        .build();

        source.add(GitHubCreateRepoAutomationTriggersDiscussionCommentCommentType.COMMENT);

        assertThat(value.commentTypes()).hasSize(source.size() - 1);
    }

    @Test
    void constructor_shouldAcceptNull_whenCategoriesNull() {
        final GitHubCreateRepoAutomationTriggersDiscussionComment value =
                GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionComment())
                        .categories(null)
                        .build();

        assertThat(value.categories()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenCategoriesGiven() {
        final List<String> source = new ArrayList<>(List.of("categories"));
        final GitHubCreateRepoAutomationTriggersDiscussionComment value =
                GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionComment())
                        .categories(source)
                        .build();

        source.add("categories");

        assertThat(value.categories()).hasSize(source.size() - 1);
    }
}
