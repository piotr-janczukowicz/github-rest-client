package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRevokeCredentialTypeForUserResponseTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubRevokeCredentialTypeForUserResponse value =
                EnterpriseAdminCatalogFixtures.revokeCredentialTypeForUserResponse();

        assertThat(value.message()).isEqualTo("sample-message");
        assertThat(value.warning()).isEqualTo("sample-warning");
    }
}
