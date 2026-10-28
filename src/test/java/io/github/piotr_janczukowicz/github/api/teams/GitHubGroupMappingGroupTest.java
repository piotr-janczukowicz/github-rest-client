package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGroupMappingGroupTest {

    @Test
    void groupMappingGroup_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubGroupMappingGroup value = TeamCatalogFixtures.groupMappingGroup();

        assertThat(value.groupId()).isEqualTo("111a1a11-aaa1-1aaa-11a1-a1a1a1a1a1aa");
        assertThat(value.groupName()).isEqualTo("saml-azuread-test");
        assertThat(value.groupDescription()).isEqualTo("A group of Developers working on AzureAD SAML SSO");
        assertThat(value.status()).isEqualTo("synced");
        assertThat(value.syncedAt()).isEqualTo("2021-01-03T22:27:15Z");
    }
}
