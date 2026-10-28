package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListEnterpriseTokenInventoryFilterTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubListEnterpriseTokenInventoryFilter value =
                EnterpriseAdminCatalogFixtures.listEnterpriseTokenInventoryFilter();

        assertThat(GitHubListEnterpriseTokenInventoryFilterBuilder.builder(value)
                        .build())
                .isEqualTo(value);
    }

    @Test
    void empty_shouldHaveNoQueryParameters() {
        assertThat(GitHubListEnterpriseTokenInventoryFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldContainEverySetField_whenFilled() {
        assertThat(EnterpriseAdminCatalogFixtures.listEnterpriseTokenInventoryFilter()
                        .toQueryParameters())
                .containsEntry("token_types", "classic_pat,ssh_key")
                .containsEntry("authorization_state", "currently_authorized")
                .containsEntry("owner", "octocat")
                .containsEntry("organization", "octo-org")
                .containsEntry("application", "octo-app");
    }
}
