package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPlanningStatusTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubPlanningStatusBuilder builder = GitHubPlanningStatusBuilder.builder(
                        RepositoriesCatalogFixtures.planningStatus())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenCategoryNull() {
        final GitHubPlanningStatusBuilder builder = GitHubPlanningStatusBuilder.builder(
                        RepositoriesCatalogFixtures.planningStatus())
                .category(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("category");
    }
}
