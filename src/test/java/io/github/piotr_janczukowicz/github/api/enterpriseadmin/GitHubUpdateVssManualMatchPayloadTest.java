package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateVssManualMatchPayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubUpdateVssManualMatchPayload value = EnterpriseAdminCatalogFixtures.updateVssManualMatchPayload();

        assertThat(value.userIdentifier()).isEqualTo("sample-user_identifier");
    }
}
