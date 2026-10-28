package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubExternalGroupTest {

    @Test
    void externalGroup_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubExternalGroup value = TeamCatalogFixtures.externalGroup();

        assertThat(value.groupId()).isEqualTo(1L);
        assertThat(value.groupName()).isEqualTo("group-azuread-test");
        assertThat(value.updatedAt()).isEqualTo("2021-01-03 22:27:15:000 -700");
        assertThat(value.teams()).containsExactly(TeamCatalogFixtures.externalGroupTeam());
        assertThat(value.members()).containsExactly(TeamCatalogFixtures.externalGroupMember());
    }
}
