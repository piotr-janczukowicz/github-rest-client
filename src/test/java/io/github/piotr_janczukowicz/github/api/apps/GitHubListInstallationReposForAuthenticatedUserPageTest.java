package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListInstallationReposForAuthenticatedUserPageTest {

    @Test
    void constructor_shouldThrow_whenRepositoriesNull() {
        final GitHubListInstallationReposForAuthenticatedUserPageBuilder builder =
                GitHubListInstallationReposForAuthenticatedUserPageBuilder.builder(
                                AppsCatalogFixtures.installationReposForAuthenticatedUserPage())
                        .repositories(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositories");
    }
}
