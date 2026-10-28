package io.github.piotr_janczukowicz.github.api.teams;

public final class TeamSimpleCatalogFixtures {

    private TeamSimpleCatalogFixtures() {}

    public static GitHubTeamSimple sampleParentTeam() {
        return new GitHubTeamSimple(
                1L,
                "MDQ6VGVhbTE=",
                "https://api.github.com/organizations/1/team/1",
                "https://api.github.com/organizations/1/team/1/members{/member}",
                "Justice League",
                "A great team.",
                "admin",
                "closed",
                "notifications_enabled",
                "https://github.com/orgs/rails/teams/core",
                "https://api.github.com/organizations/1/team/1/repos",
                "justice-league",
                "cn=parent-team,ou=groups,dc=github,dc=com",
                GitHubTeamOwnershipType.ORGANIZATION,
                1L,
                2L);
    }
}
