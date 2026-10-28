package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrUpdateIdpGroupConnectionsLegacyPayloadTest {

    @Test
    void createOrUpdateIdpGroupConnectionsLegacyPayload_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubCreateOrUpdateIdpGroupConnectionsLegacyPayload value =
                TeamCatalogFixtures.createOrUpdateIdpGroupConnectionsLegacyPayload();

        assertThat(value.groups()).containsExactly(TeamCatalogFixtures.createOrUpdateIdpGroupConnectionsLegacyGroup());
        assertThat(value.syncedAt()).isEqualTo("2021-01-03T22:27:15Z");
    }

    @Test
    void constructor_shouldThrow_whenGroupsNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCreateOrUpdateIdpGroupConnectionsLegacyPayload(null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("groups");
    }
}
