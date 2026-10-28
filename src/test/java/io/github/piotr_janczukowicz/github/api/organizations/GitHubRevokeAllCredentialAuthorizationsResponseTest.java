package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRevokeAllCredentialAuthorizationsResponseTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubRevokeAllCredentialAuthorizationsResponse value =
                OrganizationsCatalogFixtures.revokeAllCredentialAuthorizationsResponse();
        final GitHubRevokeAllCredentialAuthorizationsResponse expected =
                OrganizationsCatalogFixtures.revokeAllCredentialAuthorizationsResponse();

        assertThat(value.message()).isEqualTo(expected.message());
        assertThat(value.warning()).isEqualTo(expected.warning());
    }
}
