package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotAlertPackageTest {

    @Test
    void constructor_shouldThrow_whenEcosystemNull() {
        final GitHubDependabotAlertPackageBuilder builder = GitHubDependabotAlertPackageBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlertPackage())
                .ecosystem(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ecosystem");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubDependabotAlertPackageBuilder builder = GitHubDependabotAlertPackageBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlertPackage())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
