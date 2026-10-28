package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRevokeCredentialTypeForEnterpriseResponseTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubRevokeCredentialTypeForEnterpriseResponse value =
                EnterpriseAdminCatalogFixtures.revokeCredentialTypeForEnterpriseResponse();

        assertThat(value.message()).isEqualTo("sample-message");
        assertThat(value.warning()).isEqualTo("sample-warning");
    }
}
