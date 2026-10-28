package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationResponseTriggersDiscussionUpdatedTest {

    @Test
    void constructor_shouldLeaveCategoriesNull_whenNull() {
        final GitHubGetAutomationResponseTriggersDiscussionUpdatedBuilder builder =
                GitHubGetAutomationResponseTriggersDiscussionUpdatedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersDiscussionUpdated())
                        .categories(null);

        final GitHubGetAutomationResponseTriggersDiscussionUpdated sut = builder.build();

        assertThat(sut.categories()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubGetAutomationResponseTriggersDiscussionUpdatedBuilder builder =
                GitHubGetAutomationResponseTriggersDiscussionUpdatedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersDiscussionUpdated())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubGetAutomationResponseTriggersDiscussionUpdatedBuilder builder =
                GitHubGetAutomationResponseTriggersDiscussionUpdatedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersDiscussionUpdated())
                        .categories(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }
}
