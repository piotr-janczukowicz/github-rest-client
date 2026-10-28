package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRemoveResourceFromCostCenterPayloadTest {

    @Test
    void fixture_shouldBuildRecord_whenFixtureUsed() {
        assertThat(BillingCatalogFixtures.removeResourceFromCostCenterPayload()).isNotNull();
    }

    @Test
    void constructor_shouldLeaveUsersNull_whenNull() {
        final GitHubRemoveResourceFromCostCenterPayload value =
                GitHubRemoveResourceFromCostCenterPayloadBuilder.builder(
                                BillingCatalogFixtures.removeResourceFromCostCenterPayload())
                        .users(null)
                        .build();

        assertThat(value.users()).isNull();
    }

    @Test
    void constructor_shouldLeaveOrganizationsNull_whenNull() {
        final GitHubRemoveResourceFromCostCenterPayload value =
                GitHubRemoveResourceFromCostCenterPayloadBuilder.builder(
                                BillingCatalogFixtures.removeResourceFromCostCenterPayload())
                        .organizations(null)
                        .build();

        assertThat(value.organizations()).isNull();
    }

    @Test
    void constructor_shouldLeaveRepositoriesNull_whenNull() {
        final GitHubRemoveResourceFromCostCenterPayload value =
                GitHubRemoveResourceFromCostCenterPayloadBuilder.builder(
                                BillingCatalogFixtures.removeResourceFromCostCenterPayload())
                        .repositories(null)
                        .build();

        assertThat(value.repositories()).isNull();
    }

    @Test
    void constructor_shouldLeaveEnterpriseTeamsNull_whenNull() {
        final GitHubRemoveResourceFromCostCenterPayload value =
                GitHubRemoveResourceFromCostCenterPayloadBuilder.builder(
                                BillingCatalogFixtures.removeResourceFromCostCenterPayload())
                        .enterpriseTeams(null)
                        .build();

        assertThat(value.enterpriseTeams()).isNull();
    }

    @Test
    void constructor_shouldAccept_whenOnlyLastGroupGiven() {
        final GitHubRemoveResourceFromCostCenterPayload value =
                GitHubRemoveResourceFromCostCenterPayloadBuilder.builder(
                                BillingCatalogFixtures.removeResourceFromCostCenterPayload())
                        .users(null)
                        .organizations(null)
                        .repositories(null)
                        .build();

        assertThat(value.enterpriseTeams()).isNotNull();
    }

    @Test
    void constructor_shouldThrow_whenNothingGiven() {
        final Throwable thrown = catchThrowable(() -> GitHubRemoveResourceFromCostCenterPayloadBuilder.builder(
                        BillingCatalogFixtures.removeResourceFromCostCenterPayload())
                .users(null)
                .organizations(null)
                .repositories(null)
                .enterpriseTeams(null)
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
