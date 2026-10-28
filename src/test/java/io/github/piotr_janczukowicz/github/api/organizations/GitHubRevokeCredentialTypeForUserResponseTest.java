package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRevokeCredentialTypeForUserResponseTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubRevokeCredentialTypeForUserResponse value =
                OrganizationsCatalogFixtures.revokeCredentialTypeForUserResponse();
        final GitHubRevokeCredentialTypeForUserResponse expected =
                OrganizationsCatalogFixtures.revokeCredentialTypeForUserResponse();

        assertThat(value.message()).isEqualTo(expected.message());
        assertThat(value.warning()).isEqualTo(expected.warning());
    }
}
