package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAddResourceToCostCenterPayloadTest {

    @Test
    void fixture_shouldBuildRecord_whenFixtureUsed() {
        assertThat(BillingCatalogFixtures.addResourceToCostCenterPayload()).isNotNull();
    }

    @Test
    void constructor_shouldLeaveUsersNull_whenNull() {
        final GitHubAddResourceToCostCenterPayload value = GitHubAddResourceToCostCenterPayloadBuilder.builder(
                        BillingCatalogFixtures.addResourceToCostCenterPayload())
                .users(null)
                .build();

        assertThat(value.users()).isNull();
    }

    @Test
    void constructor_shouldLeaveOrganizationsNull_whenNull() {
        final GitHubAddResourceToCostCenterPayload value = GitHubAddResourceToCostCenterPayloadBuilder.builder(
                        BillingCatalogFixtures.addResourceToCostCenterPayload())
                .organizations(null)
                .build();

        assertThat(value.organizations()).isNull();
    }

    @Test
    void constructor_shouldLeaveRepositoriesNull_whenNull() {
        final GitHubAddResourceToCostCenterPayload value = GitHubAddResourceToCostCenterPayloadBuilder.builder(
                        BillingCatalogFixtures.addResourceToCostCenterPayload())
                .repositories(null)
                .build();

        assertThat(value.repositories()).isNull();
    }

    @Test
    void constructor_shouldLeaveEnterpriseTeamsNull_whenNull() {
        final GitHubAddResourceToCostCenterPayload value = GitHubAddResourceToCostCenterPayloadBuilder.builder(
                        BillingCatalogFixtures.addResourceToCostCenterPayload())
                .enterpriseTeams(null)
                .build();

        assertThat(value.enterpriseTeams()).isNull();
    }

    @Test
    void constructor_shouldAccept_whenOnlyLastGroupGiven() {
        final GitHubAddResourceToCostCenterPayload value = GitHubAddResourceToCostCenterPayloadBuilder.builder(
                        BillingCatalogFixtures.addResourceToCostCenterPayload())
                .users(null)
                .organizations(null)
                .repositories(null)
                .build();

        assertThat(value.enterpriseTeams()).isNotNull();
    }

    @Test
    void constructor_shouldThrow_whenNothingGiven() {
        final Throwable thrown = catchThrowable(() -> GitHubAddResourceToCostCenterPayloadBuilder.builder(
                        BillingCatalogFixtures.addResourceToCostCenterPayload())
                .users(null)
                .organizations(null)
                .repositories(null)
                .enterpriseTeams(null)
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
