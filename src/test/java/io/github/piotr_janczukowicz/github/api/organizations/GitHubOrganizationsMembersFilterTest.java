package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationsMembersFilterTest {

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubOrganizationsMembersFilter filter = GitHubOrganizationsMembersFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeFilterAndRole_whenSet() {
        final GitHubOrganizationsMembersFilter filter = GitHubOrganizationsMembersFilterBuilder.builder()
                .filter(GitHubOrganizationMembersKind.TWO_FA_DISABLED)
                .role(GitHubOrganizationMembersListRole.ADMIN)
                .build();

        assertThat(filter.toQueryParameters())
                .containsEntry("filter", "2fa_disabled")
                .containsEntry("role", "admin");
    }
}
