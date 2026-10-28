package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRevokeDelegatedCredentialAuthorizationsResponseTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubRevokeDelegatedCredentialAuthorizationsResponse value =
                EnterpriseAdminCatalogFixtures.revokeDelegatedCredentialAuthorizationsResponse();

        assertThat(value.message()).isEqualTo("sample-message");
    }
}
