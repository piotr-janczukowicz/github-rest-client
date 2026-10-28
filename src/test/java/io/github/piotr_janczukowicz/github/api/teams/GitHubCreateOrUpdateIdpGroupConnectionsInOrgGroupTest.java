package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrUpdateIdpGroupConnectionsInOrgGroupTest {

    @Test
    void createOrUpdateIdpGroupConnectionsInOrgGroup_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubCreateOrUpdateIdpGroupConnectionsInOrgGroup value =
                TeamCatalogFixtures.createOrUpdateIdpGroupConnectionsInOrgGroup();

        assertThat(value.groupId()).isEqualTo("111a1a11-aaa1-1aaa-11a1-a1a1a1a1a1aa");
        assertThat(value.groupName()).isEqualTo("saml-azuread-test");
        assertThat(value.groupDescription()).isEqualTo("A group of Developers working on AzureAD SAML SSO");
    }
}
