package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentTest {

    @Test
    void constructor_shouldThrow_whenCommentTypesTooSmall() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersDiscussionComment())
                        .commentTypes(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCommentTypesTooLarge() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentCommentType
                                        .COMMENT,
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentCommentType
                                        .COMMENT,
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentCommentType
                                        .COMMENT));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commentTypes");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersDiscussionComment())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersDiscussionComment())
                        .categories(List.of("categories", "categories"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldAccept_whenCommentTypesRepeat() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionComment value =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersDiscussionComment())
                        .commentTypes(List.of(
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentCommentType
                                        .COMMENT,
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentCommentType
                                        .COMMENT))
                        .build();

        assertThat(value.commentTypes()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenCommentTypesNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionComment value =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersDiscussionComment())
                        .commentTypes(null)
                        .build();

        assertThat(value.commentTypes()).isNull();
    }

    @Test
    void constructor_shouldAcceptNull_whenCategoriesNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionComment value =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionCommentBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersDiscussionComment())
                        .categories(null)
                        .build();

        assertThat(value.categories()).isNull();
    }
}
