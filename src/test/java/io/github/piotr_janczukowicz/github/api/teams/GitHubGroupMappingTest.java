package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGroupMappingTest {

    @Test
    void groupMapping_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubGroupMapping value = TeamCatalogFixtures.groupMapping();

        assertThat(value.groups()).containsExactly(TeamCatalogFixtures.groupMappingGroup());
    }

    @Test
    void constructor_shouldKeepItems_whenItemsSet() {
        final GitHubGroupMapping value = new GitHubGroupMapping(List.of(TeamCatalogFixtures.groupMappingGroup()));

        assertThat(value.groups()).containsExactly(TeamCatalogFixtures.groupMappingGroup());
    }

    @Test
    void constructor_shouldAllowNull_whenItemsAbsent() {
        final GitHubGroupMapping value = new GitHubGroupMapping(null);

        assertThat(value.groups()).isNull();
    }
}
