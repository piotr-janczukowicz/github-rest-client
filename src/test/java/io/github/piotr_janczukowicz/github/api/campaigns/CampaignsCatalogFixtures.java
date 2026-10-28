package io.github.piotr_janczukowicz.github.api.campaigns;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.teams.TeamCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.net.URI;
import java.time.Instant;
import java.util.List;

final class CampaignsCatalogFixtures {

    private CampaignsCatalogFixtures() {}

    static GitHubCampaignSummary campaignSummary() {
        return GitHubCampaignSummaryBuilder.builder()
                .number(1)
                .createdAt(Instant.parse("2024-01-10T12:00:00Z"))
                .updatedAt(Instant.parse("2024-03-20T09:30:00Z"))
                .name("Critical CodeQL alerts")
                .description("Address critical alerts before they are exploited to prevent breaches, protect sensitive "
                        + "data, and mitigate financial and reputational damage.")
                .managers(List.of(UsersCatalogFixtures.octocat()))
                .teamManagers(List.of(teamManager()))
                .publishedAt(Instant.parse("2024-01-15T08:00:00Z"))
                .endsAt(Instant.parse("2024-03-14T00:00:00Z"))
                .closedAt(Instant.parse("2024-03-20T09:30:00Z"))
                .state(GitHubCampaignState.CLOSED)
                .contactLink("https://example.com/campaigns/critical-codeql")
                .alertStats(campaignSummaryAlertStats())
                .build();
    }

    static GitHubCampaignSummaryAlertStats campaignSummaryAlertStats() {
        return GitHubCampaignSummaryAlertStatsBuilder.builder()
                .openCount(5)
                .closedCount(3)
                .inProgressCount(2)
                .build();
    }

    static GitHubUpdateCampaignPayload updateCampaignPayload() {
        return GitHubUpdateCampaignPayloadBuilder.builder()
                .name("Critical CodeQL alerts")
                .description("Address critical alerts before they are exploited.")
                .managers(List.of("octocat"))
                .teamManagers(List.of("justice-league"))
                .endsAt(Instant.parse("2024-03-14T00:00:00Z"))
                .contactLink(URI.create("https://example.com/campaigns/critical-codeql"))
                .state(GitHubCampaignState.OPEN)
                .build();
    }

    static GitHubCreateCampaignPayload createCampaignPayload() {
        return GitHubCreateCampaignPayloadBuilder.builder()
                .name("Critical CodeQL alerts")
                .description("Address critical alerts before they are exploited to prevent breaches, protect sensitive "
                        + "data, and mitigate financial and reputational damage.")
                .managers(List.of("octocat"))
                .teamManagers(List.of("justice-league"))
                .endsAt(Instant.parse("2024-03-14T00:00:00Z"))
                .contactLink(URI.create("https://example.com/campaigns/critical-codeql"))
                .codeScanningAlerts(List.of(createCampaignCodeScanningAlert()))
                .generateIssues(true)
                .build();
    }

    static GitHubCreateCampaignCodeScanningAlert createCampaignCodeScanningAlert() {
        return GitHubCreateCampaignCodeScanningAlertBuilder.builder()
                .repositoryId(1_296_269)
                .alertNumbers(List.of(1, 2))
                .build();
    }

    static GitHubListOrgCampaignsFilter listOrgCampaignsFilter() {
        return GitHubListOrgCampaignsFilterBuilder.builder()
                .direction(GitHubDirection.DESC)
                .state(GitHubCampaignState.OPEN)
                .sort(GitHubListOrgCampaignsSort.ENDS_AT)
                .build();
    }

    static GitHubCampaignState campaignState() {
        return GitHubCampaignState.OPEN;
    }

    static GitHubListOrgCampaignsSort listOrgCampaignsSort() {
        return GitHubListOrgCampaignsSort.ENDS_AT;
    }

    private static GitHubTeam teamManager() {
        return TeamCatalogFixtures.team();
    }
}
