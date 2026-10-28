package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLinkExternalIdpGroupToTeamForOrgPayloadTest {

    @Test
    void linkExternalIdpGroupToTeamForOrgPayload_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubLinkExternalIdpGroupToTeamForOrgPayload value =
                TeamCatalogFixtures.linkExternalIdpGroupToTeamForOrgPayload();

        assertThat(value.groupId()).isEqualTo(123L);
    }
}
