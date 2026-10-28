package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemTriggersDiscussionCommentTest {

    @Test
    void constructor_shouldLeaveCommentTypesNull_whenNull() {
        final GitHubListAutomationsItemTriggersDiscussionCommentBuilder builder =
                GitHubListAutomationsItemTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersDiscussionComment())
                        .commentTypes(null);

        final GitHubListAutomationsItemTriggersDiscussionComment sut = builder.build();

        assertThat(sut.commentTypes()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubListAutomationsItemTriggersDiscussionCommentBuilder builder =
                GitHubListAutomationsItemTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersDiscussionComment())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubListAutomationsItemTriggersDiscussionCommentBuilder builder =
                GitHubListAutomationsItemTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubListAutomationsItemTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubListAutomationsItemTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubListAutomationsItemTriggersDiscussionCommentCommentType.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesDuplicated() {
        final GitHubListAutomationsItemTriggersDiscussionCommentBuilder builder =
                GitHubListAutomationsItemTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubListAutomationsItemTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubListAutomationsItemTriggersDiscussionCommentCommentType.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldLeaveCategoriesNull_whenNull() {
        final GitHubListAutomationsItemTriggersDiscussionCommentBuilder builder =
                GitHubListAutomationsItemTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersDiscussionComment())
                        .categories(null);

        final GitHubListAutomationsItemTriggersDiscussionComment sut = builder.build();

        assertThat(sut.categories()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubListAutomationsItemTriggersDiscussionCommentBuilder builder =
                GitHubListAutomationsItemTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersDiscussionComment())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubListAutomationsItemTriggersDiscussionCommentBuilder builder =
                GitHubListAutomationsItemTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersDiscussionComment())
                        .categories(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }
}
