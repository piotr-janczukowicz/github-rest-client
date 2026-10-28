package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListReposAccessibleToInstallationPageTest {

    @Test
    void constructor_shouldThrow_whenRepositoriesNull() {
        final GitHubListReposAccessibleToInstallationPageBuilder builder =
                GitHubListReposAccessibleToInstallationPageBuilder.builder(
                                AppsCatalogFixtures.reposAccessibleToInstallationPage())
                        .repositories(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositories");
    }
}
