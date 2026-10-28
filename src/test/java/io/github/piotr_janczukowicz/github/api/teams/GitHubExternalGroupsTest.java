package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubExternalGroupsTest {

    @Test
    void externalGroups_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubExternalGroups value = TeamCatalogFixtures.externalGroups();

        assertThat(value.groups()).containsExactly(TeamCatalogFixtures.externalGroupsGroup());
    }

    @Test
    void constructor_shouldKeepItems_whenItemsSet() {
        final GitHubExternalGroups value = new GitHubExternalGroups(List.of(TeamCatalogFixtures.externalGroupsGroup()));

        assertThat(value.groups()).containsExactly(TeamCatalogFixtures.externalGroupsGroup());
    }

    @Test
    void constructor_shouldAllowNull_whenItemsAbsent() {
        final GitHubExternalGroups value = new GitHubExternalGroups(null);

        assertThat(value.groups()).isNull();
    }
}
