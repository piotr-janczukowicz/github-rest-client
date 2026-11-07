package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.entry;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetCostCenterMembershipFilterTest {

    @Test
    void constructor_shouldThrow_whenResourceTypeNull() {
        final GitHubGetCostCenterMembershipFilterBuilder builder = GitHubGetCostCenterMembershipFilterBuilder.builder(
                        BillingCatalogFixtures.getCostCenterMembershipFilter())
                .resourceType(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("resourceType");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubGetCostCenterMembershipFilterBuilder builder = GitHubGetCostCenterMembershipFilterBuilder.builder(
                        BillingCatalogFixtures.getCostCenterMembershipFilter())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void of_shouldSetFields_whenCalled() {
        final GitHubGetCostCenterMembershipFilter filter = GitHubGetCostCenterMembershipFilter.of(
                GitHubGetCostCenterMembershipResourceType.TEAM, "justice-league");

        assertThat(filter.resourceType()).isEqualTo(GitHubGetCostCenterMembershipResourceType.TEAM);
        assertThat(filter.name()).isEqualTo("justice-league");
    }

    @Test
    void toQueryParameters_shouldIncludeBothParameters_whenFilterSet() {
        final GitHubGetCostCenterMembershipFilter filter = BillingCatalogFixtures.getCostCenterMembershipFilter();

        assertThat(filter.toQueryParameters()).containsOnly(entry("resource_type", "user"), entry("name", "name"));
    }
}
