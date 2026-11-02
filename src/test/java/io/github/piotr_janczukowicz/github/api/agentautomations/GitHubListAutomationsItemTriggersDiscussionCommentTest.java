package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemTriggersDiscussionCommentTest {

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
    void constructor_shouldAccept_whenCommentTypesRepeat() {
        final GitHubListAutomationsItemTriggersDiscussionCommentBuilder builder =
                GitHubListAutomationsItemTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubListAutomationsItemTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubListAutomationsItemTriggersDiscussionCommentCommentType.COMMENT));

        final GitHubListAutomationsItemTriggersDiscussionComment value = builder.build();

        assertThat(value.commentTypes()).hasSize(2);
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
                        .categories(List.of("categories", "categories"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldAcceptNull_whenCommentTypesNull() {
        final GitHubListAutomationsItemTriggersDiscussionComment value =
                GitHubListAutomationsItemTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersDiscussionComment())
                        .commentTypes(null)
                        .build();

        assertThat(value.commentTypes()).isNull();
    }

    @Test
    void constructor_shouldAcceptNull_whenCategoriesNull() {
        final GitHubListAutomationsItemTriggersDiscussionComment value =
                GitHubListAutomationsItemTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersDiscussionComment())
                        .categories(null)
                        .build();

        assertThat(value.categories()).isNull();
    }
}
