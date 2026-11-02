package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTriggersDiscussionOpenedTest {

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubCreateRepoAutomationTriggersDiscussionOpenedBuilder builder =
                GitHubCreateRepoAutomationTriggersDiscussionOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionOpened())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubCreateRepoAutomationTriggersDiscussionOpenedBuilder builder =
                GitHubCreateRepoAutomationTriggersDiscussionOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionOpened())
                        .categories(List.of("categories", "categories"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldAcceptNull_whenCategoriesNull() {
        final GitHubCreateRepoAutomationTriggersDiscussionOpened value =
                GitHubCreateRepoAutomationTriggersDiscussionOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionOpened())
                        .categories(null)
                        .build();

        assertThat(value.categories()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenCategoriesGiven() {
        final List<String> source = new ArrayList<>(List.of("categories"));
        final GitHubCreateRepoAutomationTriggersDiscussionOpened value =
                GitHubCreateRepoAutomationTriggersDiscussionOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersDiscussionOpened())
                        .categories(source)
                        .build();

        source.add("categories");

        assertThat(value.categories()).hasSize(source.size() - 1);
    }
}
