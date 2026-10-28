package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAuditLogStreamConfigTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubGetAuditLogStreamConfig value = EnterpriseAdminCatalogFixtures.getAuditLogStreamConfig();

        assertThat(GitHubGetAuditLogStreamConfigBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenStreamTypeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetAuditLogStreamConfigBuilder.builder(
                        EnterpriseAdminCatalogFixtures.getAuditLogStreamConfig())
                .streamType(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
