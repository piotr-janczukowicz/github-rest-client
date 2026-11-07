package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentTest {

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersDiscussionComment())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentCommentType
                                        .COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersDiscussionComment())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersDiscussionComment())
                        .categories(List.of("categories", "categories"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldAccept_whenCommentTypesRepeat() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionComment value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentCommentType.COMMENT,
                                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentCommentType
                                        .COMMENT))
                        .build();

        assertThat(value.commentTypes()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenCommentTypesNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionComment value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersDiscussionComment())
                        .commentTypes(null)
                        .build();

        assertThat(value.commentTypes()).isNull();
    }

    @Test
    void constructor_shouldAcceptNull_whenCategoriesNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionComment value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersDiscussionComment())
                        .categories(null)
                        .build();

        assertThat(value.categories()).isNull();
    }
}
