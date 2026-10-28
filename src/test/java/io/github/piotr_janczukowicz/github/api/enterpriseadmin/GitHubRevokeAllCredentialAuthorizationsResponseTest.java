package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRevokeAllCredentialAuthorizationsResponseTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubRevokeAllCredentialAuthorizationsResponse value =
                EnterpriseAdminCatalogFixtures.revokeAllCredentialAuthorizationsResponse();

        assertThat(value.message()).isEqualTo("sample-message");
        assertThat(value.warning()).isEqualTo("sample-warning");
    }
}
