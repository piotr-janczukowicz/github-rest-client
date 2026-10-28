package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTriggersDiscussionUpdatedTest {

    @Test
    void constructor_shouldLeaveCategoriesNull_whenNull() {
        final GitHubCreateRepoAutomationTriggersDiscussionUpdatedBuilder builder =
                GitHubCreateRepoAutomationTriggersDiscussionUpdatedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionUpdated())
                        .categories(null);

        final GitHubCreateRepoAutomationTriggersDiscussionUpdated sut = builder.build();

        assertThat(sut.categories()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubCreateRepoAutomationTriggersDiscussionUpdatedBuilder builder =
                GitHubCreateRepoAutomationTriggersDiscussionUpdatedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionUpdated())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubCreateRepoAutomationTriggersDiscussionUpdatedBuilder builder =
                GitHubCreateRepoAutomationTriggersDiscussionUpdatedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionUpdated())
                        .categories(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }
}
