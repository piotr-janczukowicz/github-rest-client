package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetServerStatisticsFilterTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubGetServerStatisticsFilter value = EnterpriseAdminCatalogFixtures.getServerStatisticsFilter();

        assertThat(GitHubGetServerStatisticsFilterBuilder.builder(value).build())
                .isEqualTo(value);
    }

    @Test
    void empty_shouldHaveNoQueryParameters() {
        assertThat(GitHubGetServerStatisticsFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldContainEverySetField_whenFilled() {
        assertThat(EnterpriseAdminCatalogFixtures.getServerStatisticsFilter().toQueryParameters())
                .containsEntry("date_start", "cursor-start")
                .containsEntry("date_end", "cursor-end");
    }
}
