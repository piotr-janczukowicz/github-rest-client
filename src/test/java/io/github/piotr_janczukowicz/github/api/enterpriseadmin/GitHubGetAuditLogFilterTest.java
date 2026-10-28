package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAuditLogFilterTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubGetAuditLogFilter value = EnterpriseAdminCatalogFixtures.getAuditLogFilter();

        assertThat(GitHubGetAuditLogFilterBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldLeavePhraseNull_whenNull() {
        final GitHubGetAuditLogFilter value = GitHubGetAuditLogFilterBuilder.builder(
                        EnterpriseAdminCatalogFixtures.getAuditLogFilter())
                .phrase(null)
                .build();

        assertThat(value.phrase()).isNull();
    }

    @Test
    void empty_shouldHaveNoQueryParameters() {
        assertThat(GitHubGetAuditLogFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldContainEverySetField_whenFilled() {
        assertThat(EnterpriseAdminCatalogFixtures.getAuditLogFilter().toQueryParameters())
                .containsEntry("phrase", "action:org.create")
                .containsEntry("include", "web")
                .containsEntry("order", "desc");
    }
}
