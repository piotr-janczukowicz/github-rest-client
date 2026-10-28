package io.github.piotr_janczukowicz.github.api.teams;

import java.time.Instant;

final class TeamOrganizationCatalogFixtures {

    private TeamOrganizationCatalogFixtures() {}

    static GitHubTeamOrganization sampleOrganization() {
        return new GitHubTeamOrganization(
                "github",
                1L,
                "MDEyOk9yZ2FuaXphdGlvbjE=",
                "https://api.github.com/orgs/github",
                "https://api.github.com/orgs/github/repos",
                "https://api.github.com/orgs/github/events",
                "https://api.github.com/orgs/github/hooks",
                "https://api.github.com/orgs/github/issues",
                "https://api.github.com/orgs/github/members{/member}",
                "https://api.github.com/orgs/github/public_members{/member}",
                "https://github.com/images/error/octocat_happy.gif",
                "A great organization",
                "GitHub",
                "GitHub Inc",
                "https://github.com/blog",
                "San Francisco",
                "support@github.com",
                "github",
                true,
                true,
                true,
                2L,
                1L,
                20L,
                0L,
                "https://github.com/octocat",
                Instant.parse("2008-01-14T04:33:35Z"),
                "Organization",
                100L,
                100L,
                81L,
                10_000L,
                8L,
                "billing@github.com",
                new GitHubTeamOrganizationPlan("free", 976_562_499L, 0L, 3L, 5L),
                "read",
                true,
                false,
                "all",
                true,
                true,
                false,
                true,
                true,
                true,
                false,
                false,
                Instant.parse("2015-01-01T00:00:00Z"),
                Instant.parse("2020-06-01T00:00:00Z"));
    }
}
