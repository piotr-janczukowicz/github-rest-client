package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateAuditLogStreamPayloadTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubUpdateAuditLogStreamPayload value = EnterpriseAdminCatalogFixtures.updateAuditLogStreamPayload();

        assertThat(GitHubUpdateAuditLogStreamPayloadBuilder.builder(value).build())
                .isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenStreamTypeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateAuditLogStreamPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.updateAuditLogStreamPayload())
                .streamType(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
