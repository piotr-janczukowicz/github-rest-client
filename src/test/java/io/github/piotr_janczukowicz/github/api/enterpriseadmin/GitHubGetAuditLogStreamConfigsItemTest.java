package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAuditLogStreamConfigsItemTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubGetAuditLogStreamConfigsItem value = EnterpriseAdminCatalogFixtures.getAuditLogStreamConfigsItem();

        assertThat(GitHubGetAuditLogStreamConfigsItemBuilder.builder(value).build())
                .isEqualTo(value);
    }

    @Test
    void constructor_shouldLeavePausedAtNull_whenNull() {
        final GitHubGetAuditLogStreamConfigsItem value = GitHubGetAuditLogStreamConfigsItemBuilder.builder(
                        EnterpriseAdminCatalogFixtures.getAuditLogStreamConfigsItem())
                .pausedAt(null)
                .build();

        assertThat(value.pausedAt()).isNull();
    }
}
