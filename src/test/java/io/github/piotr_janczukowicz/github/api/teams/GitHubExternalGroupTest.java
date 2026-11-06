package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubExternalGroupTest {

    @Test
    void externalGroup_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubExternalGroup value = TeamCatalogFixtures.externalGroup();

        assertThat(value.groupId()).isEqualTo(1L);
        assertThat(value.groupName()).isEqualTo("group-azuread-test");
        assertThat(value.updatedAt()).isEqualTo(Instant.parse("2026-01-01T00:00:00Z"));
        assertThat(value.teams()).containsExactly(TeamCatalogFixtures.externalGroupTeam());
        assertThat(value.members()).containsExactly(TeamCatalogFixtures.externalGroupMember());
    }
}
