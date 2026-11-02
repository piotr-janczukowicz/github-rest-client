package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemTriggersDiscussionOpenedTest {

    @Test
    void constructor_shouldThrow_whenCategoriesTooSmall() {
        final GitHubListAutomationsItemTriggersDiscussionOpenedBuilder builder =
                GitHubListAutomationsItemTriggersDiscussionOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersDiscussionOpened())
                        .categories(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldThrow_whenCategoriesDuplicated() {
        final GitHubListAutomationsItemTriggersDiscussionOpenedBuilder builder =
                GitHubListAutomationsItemTriggersDiscussionOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersDiscussionOpened())
                        .categories(List.of("categories", "categories"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("categories");
    }

    @Test
    void constructor_shouldAcceptNull_whenCategoriesNull() {
        final GitHubListAutomationsItemTriggersDiscussionOpened value =
                GitHubListAutomationsItemTriggersDiscussionOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersDiscussionOpened())
                        .categories(null)
                        .build();

        assertThat(value.categories()).isNull();
    }
}
