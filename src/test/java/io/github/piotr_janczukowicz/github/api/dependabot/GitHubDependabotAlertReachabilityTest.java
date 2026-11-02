package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotAlertReachabilityTest {

    @Test
    void constructor_shouldThrow_whenVerdictNull() {
        final GitHubDependabotAlertReachabilityBuilder builder = GitHubDependabotAlertReachabilityBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlertReachability())
                .verdict(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("verdict");
    }
}
