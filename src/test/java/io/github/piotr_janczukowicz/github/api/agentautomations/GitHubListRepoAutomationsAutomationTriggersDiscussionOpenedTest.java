package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationsAutomationTriggersDiscussionOpenedTest {

    @Test
    void constructor_shouldLeaveCategoriesNull_whenNull() {
        final GitHubListRepoAutomationsAutomationTriggersDiscussionOpenedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersDiscussionOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersDiscussionOpened())
                        .categories(null);

        final GitHubListRepoAutomationsAutomationTriggersDiscussionOpened sut = builder.build();

        assertThat(sut.categories()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubListRepoAutomationsAutomationTriggersDiscussionOpenedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersDiscussionOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersDiscussionOpened())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubListRepoAutomationsAutomationTriggersDiscussionOpenedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersDiscussionOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersDiscussionOpened())
                        .categories(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }
}
