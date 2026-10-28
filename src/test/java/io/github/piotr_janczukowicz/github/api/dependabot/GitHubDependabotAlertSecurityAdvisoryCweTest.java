package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotAlertSecurityAdvisoryCweTest {

    @Test
    void constructor_shouldThrow_whenCweIdNull() {
        final GitHubDependabotAlertSecurityAdvisoryCweBuilder builder =
                GitHubDependabotAlertSecurityAdvisoryCweBuilder.builder(
                                DependabotCatalogFixtures.dependabotAlertSecurityAdvisoryCwe())
                        .cweId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cweId");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubDependabotAlertSecurityAdvisoryCweBuilder builder =
                GitHubDependabotAlertSecurityAdvisoryCweBuilder.builder(
                                DependabotCatalogFixtures.dependabotAlertSecurityAdvisoryCwe())
                        .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
