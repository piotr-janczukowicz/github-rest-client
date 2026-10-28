package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRevokeCredentialTypeResponseTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubRevokeCredentialTypeResponse value = OrganizationsCatalogFixtures.revokeCredentialTypeResponse();
        final GitHubRevokeCredentialTypeResponse expected = OrganizationsCatalogFixtures.revokeCredentialTypeResponse();

        assertThat(value.message()).isEqualTo(expected.message());
        assertThat(value.warning()).isEqualTo(expected.warning());
    }
}
