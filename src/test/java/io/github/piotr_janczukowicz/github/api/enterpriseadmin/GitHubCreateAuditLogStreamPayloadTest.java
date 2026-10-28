package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateAuditLogStreamPayloadTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubCreateAuditLogStreamPayload value = EnterpriseAdminCatalogFixtures.createAuditLogStreamPayload();

        assertThat(GitHubCreateAuditLogStreamPayloadBuilder.builder(value).build())
                .isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenStreamTypeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateAuditLogStreamPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.createAuditLogStreamPayload())
                .streamType(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
