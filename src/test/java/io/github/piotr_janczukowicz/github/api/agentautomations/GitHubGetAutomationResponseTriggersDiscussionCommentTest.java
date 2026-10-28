package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationResponseTriggersDiscussionCommentTest {

    @Test
    void constructor_shouldLeaveCommentTypesNull_whenNull() {
        final GitHubGetAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubGetAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersDiscussionComment())
                        .commentTypes(null);

        final GitHubGetAutomationResponseTriggersDiscussionComment sut = builder.build();

        assertThat(sut.commentTypes()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubGetAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubGetAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersDiscussionComment())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubGetAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubGetAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubGetAutomationResponseTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubGetAutomationResponseTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubGetAutomationResponseTriggersDiscussionCommentCommentType.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesDuplicated() {
        final GitHubGetAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubGetAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubGetAutomationResponseTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubGetAutomationResponseTriggersDiscussionCommentCommentType.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldLeaveCategoriesNull_whenNull() {
        final GitHubGetAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubGetAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersDiscussionComment())
                        .categories(null);

        final GitHubGetAutomationResponseTriggersDiscussionComment sut = builder.build();

        assertThat(sut.categories()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubGetAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubGetAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersDiscussionComment())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubGetAutomationResponseTriggersDiscussionCommentBuilder builder =
                GitHubGetAutomationResponseTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersDiscussionComment())
                        .categories(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }
}
