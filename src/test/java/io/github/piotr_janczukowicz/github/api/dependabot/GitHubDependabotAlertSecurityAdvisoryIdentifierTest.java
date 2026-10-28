package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotAlertSecurityAdvisoryIdentifierTest {

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubDependabotAlertSecurityAdvisoryIdentifierBuilder builder =
                GitHubDependabotAlertSecurityAdvisoryIdentifierBuilder.builder(
                                DependabotCatalogFixtures.dependabotAlertSecurityAdvisoryIdentifier())
                        .type(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldThrow_whenValueNull() {
        final GitHubDependabotAlertSecurityAdvisoryIdentifierBuilder builder =
                GitHubDependabotAlertSecurityAdvisoryIdentifierBuilder.builder(
                                DependabotCatalogFixtures.dependabotAlertSecurityAdvisoryIdentifier())
                        .value(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
