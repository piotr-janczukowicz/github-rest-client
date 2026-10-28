package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotDismissalRequestOrganizationTest {

    @Test
    void gitHubDependabotDismissalRequestOrganization_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubDependabotDismissalRequestOrganization value =
                DependabotCatalogFixtures.dependabotDismissalRequestOrganization();

        assertThat(value.id()).isEqualTo(1L);
        assertThat(value.name()).isEqualTo("octo-org");
    }
}
