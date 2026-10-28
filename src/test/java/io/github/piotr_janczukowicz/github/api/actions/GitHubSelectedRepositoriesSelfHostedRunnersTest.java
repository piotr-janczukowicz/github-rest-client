package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSelectedRepositoriesSelfHostedRunnersTest {

    @Test
    void constructor_shouldAcceptNullRepositories_whenRepositoriesNull() {
        final GitHubSelectedRepositoriesSelfHostedRunners selectedRepositories =
                GitHubSelectedRepositoriesSelfHostedRunnersBuilder.builder(
                                ActionsCatalogFixtures.selectedRepositoriesSelfHostedRunners())
                        .repositories(null)
                        .build();

        assertThat(selectedRepositories.repositories()).isNull();
    }
}
