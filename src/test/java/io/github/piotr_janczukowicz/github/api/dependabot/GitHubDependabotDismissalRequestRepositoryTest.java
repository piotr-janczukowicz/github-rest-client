package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotDismissalRequestRepositoryTest {

    @Test
    void gitHubDependabotDismissalRequestRepository_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubDependabotDismissalRequestRepository value =
                DependabotCatalogFixtures.dependabotDismissalRequestRepository();

        assertThat(value.id()).isEqualTo(1L);
        assertThat(value.name()).isEqualTo("octo-repo");
        assertThat(value.fullName()).isEqualTo("octo-org/octo-repo");
    }
}
