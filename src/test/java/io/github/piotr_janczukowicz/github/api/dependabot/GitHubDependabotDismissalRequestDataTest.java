package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotDismissalRequestDataTest {

    @Test
    void gitHubDependabotDismissalRequestData_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubDependabotDismissalRequestData value = DependabotCatalogFixtures.dependabotDismissalRequestData();

        assertThat(value.reason()).isEqualTo("fix_started");
        assertThat(value.alertNumber()).isEqualTo("1");
        assertThat(value.alertTitle()).isEqualTo("Prototype pollution in lodash");
    }
}
