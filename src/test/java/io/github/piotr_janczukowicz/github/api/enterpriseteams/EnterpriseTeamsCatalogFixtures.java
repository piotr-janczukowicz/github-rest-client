package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import java.time.Instant;
import java.util.List;

final class EnterpriseTeamsCatalogFixtures {

    private static final String ENTERPRISE_TEAM_URL =
            "https://api.github.com/enterprises/octo-enterprise/teams/justice-league";
    private static final String GROUP_ID = "62ab9291-fae2-468e-974b-7e45096d5021";

    private EnterpriseTeamsCatalogFixtures() {}

    static GitHubEnterpriseTeamWithMemberCount enterpriseTeamWithMemberCount() {
        return GitHubEnterpriseTeamWithMemberCountBuilder.builder()
                .id(1L)
                .url(ENTERPRISE_TEAM_URL)
                .membersUrl(ENTERPRISE_TEAM_URL + "/members{/member}")
                .membersCount(3)
                .name("Justice League")
                .description("A great team.")
                .slug("justice-league")
                .syncToOrganizations("disabled")
                .organizationSelectionType("disabled")
                .groupId(GROUP_ID)
                .groupName("Octo group")
                .htmlUrl("https://github.com/enterprises/octo-enterprise/teams/justice-league")
                .createdAt(Instant.parse("2023-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2023-01-01T12:00:00Z"))
                .notificationSetting(GitHubEnterpriseTeamWithMemberCountNotificationSetting.NOTIFICATIONS_ENABLED)
                .build();
    }

    static GitHubCreateEnterpriseTeamPayload createEnterpriseTeamPayload() {
        return GitHubCreateEnterpriseTeamPayloadBuilder.builder()
                .name("Justice League")
                .description("A great team.")
                .syncToOrganizations(GitHubCreateEnterpriseTeamSyncToOrganizations.DISABLED)
                .organizationSelectionType(GitHubCreateEnterpriseTeamOrganizationSelectionType.SELECTED)
                .groupId(GROUP_ID)
                .notificationSetting(GitHubCreateEnterpriseTeamNotificationSetting.NOTIFICATIONS_ENABLED)
                .build();
    }

    static GitHubUpdateEnterpriseTeamPayload updateEnterpriseTeamPayload() {
        return GitHubUpdateEnterpriseTeamPayloadBuilder.builder()
                .name("Justice League")
                .description("A new description for the team.")
                .syncToOrganizations(GitHubUpdateEnterpriseTeamSyncToOrganizations.DISABLED)
                .organizationSelectionType(GitHubUpdateEnterpriseTeamOrganizationSelectionType.ALL)
                .groupId(GROUP_ID)
                .notificationSetting(GitHubUpdateEnterpriseTeamNotificationSetting.NOTIFICATIONS_DISABLED)
                .build();
    }

    static GitHubBulkAddEnterpriseTeamMembershipsPayload bulkAddEnterpriseTeamMembershipsPayload() {
        return GitHubBulkAddEnterpriseTeamMembershipsPayloadBuilder.builder()
                .usernames(List.of("monalisa", "octocat"))
                .build();
    }

    static GitHubBulkRemoveEnterpriseTeamMembershipsPayload bulkRemoveEnterpriseTeamMembershipsPayload() {
        return GitHubBulkRemoveEnterpriseTeamMembershipsPayloadBuilder.builder()
                .usernames(List.of("monalisa", "octocat"))
                .build();
    }

    static GitHubBulkAddEnterpriseTeamOrganizationsPayload bulkAddEnterpriseTeamOrganizationsPayload() {
        return GitHubBulkAddEnterpriseTeamOrganizationsPayloadBuilder.builder()
                .organizationSlugs(List.of("github"))
                .build();
    }

    static GitHubBulkRemoveEnterpriseTeamOrganizationsPayload bulkRemoveEnterpriseTeamOrganizationsPayload() {
        return GitHubBulkRemoveEnterpriseTeamOrganizationsPayloadBuilder.builder()
                .organizationSlugs(List.of("github"))
                .build();
    }
}
