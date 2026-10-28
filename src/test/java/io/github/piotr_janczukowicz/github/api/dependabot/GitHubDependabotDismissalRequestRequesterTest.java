package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotDismissalRequestRequesterTest {

    @Test
    void gitHubDependabotDismissalRequestRequester_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubDependabotDismissalRequestRequester value =
                DependabotCatalogFixtures.dependabotDismissalRequestRequester();

        assertThat(value.actorId()).isEqualTo(583_231L);
        assertThat(value.actorName()).isEqualTo("octocat");
    }
}
