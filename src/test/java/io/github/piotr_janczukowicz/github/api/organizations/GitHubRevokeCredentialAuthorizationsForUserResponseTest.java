package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRevokeCredentialAuthorizationsForUserResponseTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubRevokeCredentialAuthorizationsForUserResponse value =
                OrganizationsCatalogFixtures.revokeCredentialAuthorizationsForUserResponse();
        final GitHubRevokeCredentialAuthorizationsForUserResponse expected =
                OrganizationsCatalogFixtures.revokeCredentialAuthorizationsForUserResponse();

        assertThat(value.message()).isEqualTo(expected.message());
        assertThat(value.warning()).isEqualTo(expected.warning());
    }
}
