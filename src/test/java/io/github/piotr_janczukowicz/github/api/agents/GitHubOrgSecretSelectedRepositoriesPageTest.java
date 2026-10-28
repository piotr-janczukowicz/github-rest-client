package io.github.piotr_janczukowicz.github.api.agents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrgSecretSelectedRepositoriesPageTest {

    @Test
    void constructor_shouldThrow_whenRepositoriesNull() {
        final GitHubOrgSecretSelectedRepositoriesPageBuilder builder =
                GitHubOrgSecretSelectedRepositoriesPageBuilder.builder(
                                AgentsCatalogFixtures.orgSecretSelectedRepositoriesPage())
                        .repositories(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositories");
    }
}
