package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionUpdatedTest {

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionUpdatedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionUpdatedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersDiscussionUpdated())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionUpdatedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionUpdatedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersDiscussionUpdated())
                        .categories(List.of("categories", "categories"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldAcceptNull_whenCategoriesNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionUpdated value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionUpdatedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersDiscussionUpdated())
                        .categories(null)
                        .build();

        assertThat(value.categories()).isNull();
    }
}
