package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationDiscussionCommentTriggerPayloadTest {

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubAutomationDiscussionCommentTriggerPayloadBuilder builder =
                GitHubAutomationDiscussionCommentTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationDiscussionCommentTriggerPayload())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubAutomationDiscussionCommentTriggerPayloadBuilder builder =
                GitHubAutomationDiscussionCommentTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationDiscussionCommentTriggerPayload())
                        .commentTypes(List.of(
                                GitHubAutomationDiscussionCommentCommentTypeInput.COMMENT,
                                GitHubAutomationDiscussionCommentCommentTypeInput.COMMENT,
                                GitHubAutomationDiscussionCommentCommentTypeInput.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesDuplicated() {
        final GitHubAutomationDiscussionCommentTriggerPayloadBuilder builder =
                GitHubAutomationDiscussionCommentTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationDiscussionCommentTriggerPayload())
                        .commentTypes(List.of(
                                GitHubAutomationDiscussionCommentCommentTypeInput.COMMENT,
                                GitHubAutomationDiscussionCommentCommentTypeInput.COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubAutomationDiscussionCommentTriggerPayloadBuilder builder =
                GitHubAutomationDiscussionCommentTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationDiscussionCommentTriggerPayload())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubAutomationDiscussionCommentTriggerPayloadBuilder builder =
                GitHubAutomationDiscussionCommentTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationDiscussionCommentTriggerPayload())
                        .categories(List.of("categories", "categories"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldAcceptNull_whenCommentTypesNull() {
        final GitHubAutomationDiscussionCommentTriggerPayload value =
                GitHubAutomationDiscussionCommentTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationDiscussionCommentTriggerPayload())
                        .commentTypes(null)
                        .build();

        assertThat(value.commentTypes()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenCommentTypesGiven() {
        final List<GitHubAutomationDiscussionCommentCommentTypeInput> source =
                new ArrayList<>(List.of(GitHubAutomationDiscussionCommentCommentTypeInput.COMMENT));
        final GitHubAutomationDiscussionCommentTriggerPayload value =
                GitHubAutomationDiscussionCommentTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationDiscussionCommentTriggerPayload())
                        .commentTypes(source)
                        .build();

        source.add(GitHubAutomationDiscussionCommentCommentTypeInput.COMMENT);

        assertThat(value.commentTypes()).hasSize(source.size() - 1);
    }

    @Test
    void constructor_shouldAcceptNull_whenCategoriesNull() {
        final GitHubAutomationDiscussionCommentTriggerPayload value =
                GitHubAutomationDiscussionCommentTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationDiscussionCommentTriggerPayload())
                        .categories(null)
                        .build();

        assertThat(value.categories()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenCategoriesGiven() {
        final List<String> source = new ArrayList<>(List.of("categories"));
        final GitHubAutomationDiscussionCommentTriggerPayload value =
                GitHubAutomationDiscussionCommentTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationDiscussionCommentTriggerPayload())
                        .categories(source)
                        .build();

        source.add("categories");

        assertThat(value.categories()).hasSize(source.size() - 1);
    }
}
