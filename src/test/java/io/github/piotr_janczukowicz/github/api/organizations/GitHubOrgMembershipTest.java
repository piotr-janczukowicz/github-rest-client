package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrgMembershipTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(() -> sampleMembership(null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final Throwable thrown = catchThrowable(
                () -> sampleMembership("https://api.github.com/orgs/octocat/memberships/defunkt", null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenRoleNull() {
        final Throwable thrown = catchThrowable(() -> sampleMembership(
                "https://api.github.com/orgs/octocat/memberships/defunkt",
                GitHubOrgMembershipState.ACTIVE,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("role");
    }

    @Test
    void constructor_shouldThrow_whenOrganizationUrlNull() {
        final Throwable thrown = catchThrowable(() -> sampleMembership(
                "https://api.github.com/orgs/octocat/memberships/defunkt",
                GitHubOrgMembershipState.ACTIVE,
                GitHubOrgMembershipRole.ADMIN,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("organizationUrl");
    }

    @Test
    void constructor_shouldThrow_whenOrganizationNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrgMembership(
                "https://api.github.com/orgs/octocat/memberships/defunkt",
                GitHubOrgMembershipState.ACTIVE,
                GitHubOrgMembershipRole.ADMIN,
                true,
                null,
                "https://api.github.com/orgs/octocat",
                null,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("organization");
    }

    @Test
    void constructor_shouldThrow_whenEnterpriseTeamsExceedMaxItems() {
        final List<String> teams =
                IntStream.rangeClosed(1, 101).mapToObj(i -> "ent:team-" + i).toList();

        final Throwable thrown = catchThrowable(() -> new GitHubOrgMembership(
                "https://api.github.com/orgs/octocat/memberships/defunkt",
                GitHubOrgMembershipState.ACTIVE,
                GitHubOrgMembershipRole.ADMIN,
                true,
                teams,
                "https://api.github.com/orgs/octocat",
                OrganizationsCatalogFixtures.organizationSimple(),
                null,
                null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("enterpriseTeamsProvidingIndirectMembership");
    }

    @Test
    void constructor_shouldCopyEnterpriseTeams_whenPresent() {
        final GitHubOrgMembership membership = new GitHubOrgMembership(
                "https://api.github.com/orgs/octocat/memberships/defunkt",
                GitHubOrgMembershipState.ACTIVE,
                GitHubOrgMembershipRole.ADMIN,
                true,
                List.of("ent:justice-league"),
                "https://api.github.com/orgs/octocat",
                OrganizationsCatalogFixtures.organizationSimple(),
                null,
                null);

        assertThat(membership.enterpriseTeamsProvidingIndirectMembership()).containsExactly("ent:justice-league");
    }

    @Test
    void constructor_shouldLeaveEnterpriseTeamsNull_whenEnterpriseTeamsProvidingIndirectMembershipNull() {
        final GitHubOrgMembership membership = sampleMembership(
                "https://api.github.com/orgs/octocat/memberships/defunkt",
                GitHubOrgMembershipState.ACTIVE,
                GitHubOrgMembershipRole.ADMIN,
                "https://api.github.com/orgs/octocat");

        assertThat(membership.enterpriseTeamsProvidingIndirectMembership()).isNull();
    }

    private static GitHubOrgMembership sampleMembership(
            final String url,
            final GitHubOrgMembershipState state,
            final GitHubOrgMembershipRole role,
            final String organizationUrl) {
        return new GitHubOrgMembership(
                url,
                state,
                role,
                true,
                null,
                organizationUrl,
                OrganizationsCatalogFixtures.organizationSimple(),
                null,
                null);
    }
}
