package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAuditLogStreamKeyTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubAuditLogStreamKey value = EnterpriseAdminCatalogFixtures.auditLogStreamKey();

        assertThat(GitHubAuditLogStreamKeyBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenKeyIdNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubAuditLogStreamKeyBuilder.builder(EnterpriseAdminCatalogFixtures.auditLogStreamKey())
                        .keyId(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
