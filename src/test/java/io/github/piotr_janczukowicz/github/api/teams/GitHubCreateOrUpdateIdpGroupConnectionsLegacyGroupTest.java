package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrUpdateIdpGroupConnectionsLegacyGroupTest {

    @Test
    void createOrUpdateIdpGroupConnectionsLegacyGroup_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubCreateOrUpdateIdpGroupConnectionsLegacyGroup value =
                TeamCatalogFixtures.createOrUpdateIdpGroupConnectionsLegacyGroup();

        assertThat(value.groupId()).isEqualTo("111a1a11-aaa1-1aaa-11a1-a1a1a1a1a1aa");
        assertThat(value.groupName()).isEqualTo("saml-azuread-test");
        assertThat(value.groupDescription()).isEqualTo("A group of Developers working on AzureAD SAML SSO");
        assertThat(value.id()).isEqualTo("caceab43fc9ffa20081c");
        assertThat(value.name()).isEqualTo("external-team-6c13e7288ef7");
        assertThat(value.description()).isEqualTo("moar cheese pleese");
    }
}
