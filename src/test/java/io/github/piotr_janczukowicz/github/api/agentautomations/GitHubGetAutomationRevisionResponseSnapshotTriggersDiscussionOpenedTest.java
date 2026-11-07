package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionOpenedTest {

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionOpenedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersDiscussionOpened())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionOpenedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersDiscussionOpened())
                        .categories(List.of("categories", "categories"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldAcceptNull_whenCategoriesNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionOpened value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersDiscussionOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersDiscussionOpened())
                        .categories(null)
                        .build();

        assertThat(value.categories()).isNull();
    }
}
