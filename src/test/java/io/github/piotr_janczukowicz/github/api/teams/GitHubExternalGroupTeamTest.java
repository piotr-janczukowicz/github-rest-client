package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubExternalGroupTeamTest {

    @Test
    void externalGroupTeam_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubExternalGroupTeam value = TeamCatalogFixtures.externalGroupTeam();

        assertThat(value.teamId()).isEqualTo(1L);
        assertThat(value.teamName()).isEqualTo("team-test");
    }
}
