package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationsAuthenticatedMembershipsFilterTest {

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubOrganizationsAuthenticatedMembershipsFilter filter =
                GitHubOrganizationsAuthenticatedMembershipsFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeState_whenSet() {
        final GitHubOrganizationsAuthenticatedMembershipsFilter filter =
                GitHubOrganizationsAuthenticatedMembershipsFilterBuilder.builder()
                        .state(GitHubOrgMembershipState.PENDING)
                        .build();

        assertThat(filter.toQueryParameters()).containsEntry("state", "pending");
    }
}
