package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrUpdateIdpGroupConnectionsInOrgPayloadTest {

    @Test
    void createOrUpdateIdpGroupConnectionsInOrgPayload_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubCreateOrUpdateIdpGroupConnectionsInOrgPayload value =
                TeamCatalogFixtures.createOrUpdateIdpGroupConnectionsInOrgPayload();

        assertThat(value.groups()).containsExactly(TeamCatalogFixtures.createOrUpdateIdpGroupConnectionsInOrgGroup());
    }

    @Test
    void constructor_shouldKeepItems_whenItemsSet() {
        final GitHubCreateOrUpdateIdpGroupConnectionsInOrgPayload value =
                new GitHubCreateOrUpdateIdpGroupConnectionsInOrgPayload(
                        List.of(TeamCatalogFixtures.createOrUpdateIdpGroupConnectionsInOrgGroup()));

        assertThat(value.groups()).containsExactly(TeamCatalogFixtures.createOrUpdateIdpGroupConnectionsInOrgGroup());
    }

    @Test
    void constructor_shouldAllowNull_whenItemsAbsent() {
        final GitHubCreateOrUpdateIdpGroupConnectionsInOrgPayload value =
                new GitHubCreateOrUpdateIdpGroupConnectionsInOrgPayload(null);

        assertThat(value.groups()).isNull();
    }
}
