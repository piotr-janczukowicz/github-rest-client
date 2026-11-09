package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationDiscussionCommentTriggerTest {

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubAutomationDiscussionCommentTriggerBuilder builder =
                GitHubAutomationDiscussionCommentTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationDiscussionCommentTrigger())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubAutomationDiscussionCommentTriggerBuilder builder =
                GitHubAutomationDiscussionCommentTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationDiscussionCommentTrigger())
                        .commentTypes(List.of(
                                GitHubAutomationDiscussionCommentCommentType.COMMENT,
                                GitHubAutomationDiscussionCommentCommentType.COMMENT,
                                GitHubAutomationDiscussionCommentCommentType.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldAccept_whenCommentTypesRepeat() {
        final GitHubAutomationDiscussionCommentTriggerBuilder builder =
                GitHubAutomationDiscussionCommentTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationDiscussionCommentTrigger())
                        .commentTypes(List.of(
                                GitHubAutomationDiscussionCommentCommentType.COMMENT,
                                GitHubAutomationDiscussionCommentCommentType.COMMENT));

        final GitHubAutomationDiscussionCommentTrigger value = builder.build();

        assertThat(value.commentTypes()).hasSize(2);
    }

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubAutomationDiscussionCommentTriggerBuilder builder =
                GitHubAutomationDiscussionCommentTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationDiscussionCommentTrigger())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubAutomationDiscussionCommentTriggerBuilder builder =
                GitHubAutomationDiscussionCommentTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationDiscussionCommentTrigger())
                        .categories(List.of("categories", "categories"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldAcceptNull_whenCommentTypesNull() {
        final GitHubAutomationDiscussionCommentTrigger value = GitHubAutomationDiscussionCommentTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationDiscussionCommentTrigger())
                .commentTypes(null)
                .build();

        assertThat(value.commentTypes()).isNull();
    }

    @Test
    void constructor_shouldAcceptNull_whenCategoriesNull() {
        final GitHubAutomationDiscussionCommentTrigger value = GitHubAutomationDiscussionCommentTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationDiscussionCommentTrigger())
                .categories(null)
                .build();

        assertThat(value.categories()).isNull();
    }
}
