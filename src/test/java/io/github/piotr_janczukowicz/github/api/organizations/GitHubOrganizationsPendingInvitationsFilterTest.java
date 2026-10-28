package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationsPendingInvitationsFilterTest {

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubOrganizationsPendingInvitationsFilter filter = GitHubOrganizationsPendingInvitationsFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeRoleAndInvitationSource_whenSet() {
        final GitHubOrganizationsPendingInvitationsFilter filter =
                GitHubOrganizationsPendingInvitationsFilterBuilder.builder()
                        .role(GitHubOrganizationPendingInvitationRole.ADMIN)
                        .invitationSource(GitHubOrganizationPendingInvitationSource.SCIM)
                        .build();

        assertThat(filter.toQueryParameters())
                .containsEntry("role", "admin")
                .containsEntry("invitation_source", "scim");
    }
}
