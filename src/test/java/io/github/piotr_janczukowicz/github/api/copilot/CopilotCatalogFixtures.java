package io.github.piotr_janczukowicz.github.api.copilot;

import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.teams.TeamCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public final class CopilotCatalogFixtures {

    private static final LocalDate REPORT_DAY = LocalDate.parse("2025-10-13");
    private static final Instant CREATED_AT = Instant.parse("2023-01-01T00:00:00Z");
    private static final Instant UPDATED_AT = Instant.parse("2023-01-01T12:00:00Z");

    private CopilotCatalogFixtures() {}

    static GitHubCopilotEnterpriseOneDayUsageMetricsFilter copilotEnterpriseOneDayUsageMetricsFilter() {
        return GitHubCopilotEnterpriseOneDayUsageMetricsFilterBuilder.builder()
                .day(REPORT_DAY)
                .build();
    }

    static GitHubCopilotEnterpriseReposOneDayReportFilter copilotEnterpriseReposOneDayReportFilter() {
        return GitHubCopilotEnterpriseReposOneDayReportFilterBuilder.builder()
                .day(REPORT_DAY)
                .build();
    }

    static GitHubCopilotEnterpriseUserTeamsOneDayReportFilter copilotEnterpriseUserTeamsOneDayReportFilter() {
        return GitHubCopilotEnterpriseUserTeamsOneDayReportFilterBuilder.builder()
                .day(REPORT_DAY)
                .build();
    }

    static GitHubCopilotUsersOneDayUsageMetricsFilter copilotUsersOneDayUsageMetricsFilter() {
        return GitHubCopilotUsersOneDayUsageMetricsFilterBuilder.builder()
                .day(REPORT_DAY)
                .build();
    }

    static GitHubCopilotOrganizationOneDayUsageMetricsFilter copilotOrganizationOneDayUsageMetricsFilter() {
        return GitHubCopilotOrganizationOneDayUsageMetricsFilterBuilder.builder()
                .day(REPORT_DAY)
                .build();
    }

    static GitHubCopilotOrganizationReposOneDayReportFilter copilotOrganizationReposOneDayReportFilter() {
        return GitHubCopilotOrganizationReposOneDayReportFilterBuilder.builder()
                .day(REPORT_DAY)
                .build();
    }

    static GitHubCopilotOrganizationUserTeamsOneDayReportFilter copilotOrganizationUserTeamsOneDayReportFilter() {
        return GitHubCopilotOrganizationUserTeamsOneDayReportFilterBuilder.builder()
                .day(REPORT_DAY)
                .build();
    }

    static GitHubCopilotOrganizationUsersOneDayUsageMetricsFilter copilotOrganizationUsersOneDayUsageMetricsFilter() {
        return GitHubCopilotOrganizationUsersOneDayUsageMetricsFilterBuilder.builder()
                .day(REPORT_DAY)
                .build();
    }

    static GitHubSetEnterpriseCodingAgentPolicyPayload setEnterpriseCodingAgentPolicyPayload() {
        return GitHubSetEnterpriseCodingAgentPolicyPayloadBuilder.builder()
                .policyState(GitHubCodingAgentPolicyState.ENABLED_FOR_SELECTED_ORGS)
                .build();
    }

    static GitHubAddOrganizationsToEnterpriseCodingAgentPolicyPayload
            addOrganizationsToEnterpriseCodingAgentPolicyPayload() {
        return GitHubAddOrganizationsToEnterpriseCodingAgentPolicyPayloadBuilder.builder()
                .organizations(List.of("octo-org"))
                .customProperties(List.of(codingAgentCustomProperty()))
                .build();
    }

    static GitHubRemoveOrganizationsFromEnterpriseCodingAgentPolicyPayload
            removeOrganizationsFromEnterpriseCodingAgentPolicyPayload() {
        return GitHubRemoveOrganizationsFromEnterpriseCodingAgentPolicyPayloadBuilder.builder()
                .organizations(List.of("octo-org"))
                .customProperties(List.of(codingAgentCustomProperty()))
                .build();
    }

    static GitHubCodingAgentCustomProperty codingAgentCustomProperty() {
        return GitHubCodingAgentCustomPropertyBuilder.builder()
                .propertyName("environment")
                .values(List.of("production"))
                .build();
    }

    static GitHubAddCopilotSeatsForTeamsPayload addCopilotSeatsForTeamsPayload() {
        return GitHubAddCopilotSeatsForTeamsPayloadBuilder.builder()
                .selectedTeams(List.of("justice-league"))
                .build();
    }

    static GitHubCancelCopilotSeatAssignmentForTeamsPayload cancelCopilotSeatAssignmentForTeamsPayload() {
        return GitHubCancelCopilotSeatAssignmentForTeamsPayloadBuilder.builder()
                .selectedTeams(List.of("justice-league"))
                .build();
    }

    static GitHubAddCopilotSeatsForUsersPayload addCopilotSeatsForUsersPayload() {
        return GitHubAddCopilotSeatsForUsersPayloadBuilder.builder()
                .selectedUsernames(List.of("octocat"))
                .build();
    }

    static GitHubCancelCopilotSeatAssignmentForUsersPayload cancelCopilotSeatAssignmentForUsersPayload() {
        return GitHubCancelCopilotSeatAssignmentForUsersPayloadBuilder.builder()
                .selectedUsernames(List.of("octocat"))
                .build();
    }

    static GitHubSetCopilotCodingAgentPermissionsOrganizationPayload
            setCopilotCodingAgentPermissionsOrganizationPayload() {
        return GitHubSetCopilotCodingAgentPermissionsOrganizationPayloadBuilder.builder()
                .enabledRepositories(GitHubCopilotCodingAgentEnabledRepositories.SELECTED)
                .build();
    }

    static GitHubSetCopilotCodingAgentSelectedRepositoriesForOrganizationPayload
            setCopilotCodingAgentSelectedRepositoriesForOrganizationPayload() {
        return GitHubSetCopilotCodingAgentSelectedRepositoriesForOrganizationPayloadBuilder.builder()
                .selectedRepositoryIds(List.of(1_296_269L, 1_296_270L))
                .build();
    }

    static GitHubAddCopilotSeatsForTeamsResponse addCopilotSeatsForTeamsResponse() {
        return GitHubAddCopilotSeatsForTeamsResponseBuilder.builder()
                .seatsCreated(1)
                .build();
    }

    static GitHubCancelCopilotSeatAssignmentForTeamsResponse cancelCopilotSeatAssignmentForTeamsResponse() {
        return GitHubCancelCopilotSeatAssignmentForTeamsResponseBuilder.builder()
                .seatsCancelled(1)
                .build();
    }

    static GitHubAddCopilotSeatsForUsersResponse addCopilotSeatsForUsersResponse() {
        return GitHubAddCopilotSeatsForUsersResponseBuilder.builder()
                .seatsCreated(2)
                .build();
    }

    static GitHubCancelCopilotSeatAssignmentForUsersResponse cancelCopilotSeatAssignmentForUsersResponse() {
        return GitHubCancelCopilotSeatAssignmentForUsersResponseBuilder.builder()
                .seatsCancelled(1)
                .build();
    }

    static GitHubGetCopilotCodingAgentPermissionsOrganizationResponse
            getCopilotCodingAgentPermissionsOrganizationResponse() {
        return GitHubGetCopilotCodingAgentPermissionsOrganizationResponseBuilder.builder()
                .enabledRepositories(GitHubCopilotCodingAgentEnabledRepositories.SELECTED)
                .selectedRepositoriesUrl(
                        "https://api.github.com/orgs/octo-org/copilot/coding-agent/permissions/repositories")
                .build();
    }

    static GitHubListCopilotCodingAgentSelectedRepositoriesForOrganizationResponse
            listCopilotCodingAgentSelectedRepositoriesForOrganizationResponse() {
        return GitHubListCopilotCodingAgentSelectedRepositoriesForOrganizationResponseBuilder.builder()
                .totalCount(1)
                .repositories(List.of(RepositoriesCatalogFixtures.helloWorldMinimalRepository()))
                .build();
    }

    static GitHubGetCopilotCloudAgentConfigurationResponse getCopilotCloudAgentConfigurationResponse() {
        return GitHubGetCopilotCloudAgentConfigurationResponseBuilder.builder()
                .mcpConfiguration(Map.of("server", "https://example.com/mcp"))
                .enabledTools(copilotCloudAgentEnabledTools())
                .requireActionsWorkflowApproval(true)
                .isFirewallEnabled(true)
                .isFirewallRecommendedAllowlistEnabled(true)
                .customAllowlist(List.of("example.com"))
                .isAutomationsEnabled(true)
                .requireWriteAccessForAutomationTriggers(false)
                .build();
    }

    static GitHubCopilotCloudAgentEnabledTools copilotCloudAgentEnabledTools() {
        return GitHubCopilotCloudAgentEnabledToolsBuilder.builder()
                .codeql(true)
                .copilotCodeReview(true)
                .secretScanning(false)
                .dependencyVulnerabilityChecks(true)
                .build();
    }

    static GitHubSetCopilotContentExclusionForOrganizationResponse setCopilotContentExclusionForOrganizationResponse() {
        return GitHubSetCopilotContentExclusionForOrganizationResponseBuilder.builder()
                .message("Content exclusion rules updated successfully.")
                .build();
    }

    static GitHubCopilotOrganizationDetails copilotOrganizationDetails() {
        return GitHubCopilotOrganizationDetailsBuilder.builder()
                .seatBreakdown(copilotOrganizationSeatBreakdown())
                .publicCodeSuggestions(GitHubCopilotOrganizationPublicCodeSuggestions.ALLOW)
                .ideChat(GitHubCopilotOrganizationFeatureSetting.ENABLED)
                .platformChat(GitHubCopilotOrganizationFeatureSetting.ENABLED)
                .cli(GitHubCopilotOrganizationFeatureSetting.DISABLED)
                .seatManagementSetting(GitHubCopilotOrganizationSeatManagementSetting.ASSIGN_SELECTED)
                .planType(GitHubCopilotOrganizationPlanType.BUSINESS)
                .build();
    }

    static GitHubCopilotOrganizationSeatBreakdown copilotOrganizationSeatBreakdown() {
        return GitHubCopilotOrganizationSeatBreakdownBuilder.builder()
                .total(10)
                .addedThisCycle(2)
                .pendingCancellation(1)
                .pendingInvitation(3)
                .activeThisCycle(6)
                .inactiveThisCycle(4)
                .build();
    }

    static GitHubCopilotUsageMetrics1DayReport copilotUsageMetrics1DayReport() {
        return GitHubCopilotUsageMetrics1DayReportBuilder.builder()
                .downloadLinks(downloadLinks())
                .reportDay(REPORT_DAY)
                .build();
    }

    static GitHubCopilotUsageMetrics28DayReport copilotUsageMetrics28DayReport() {
        return GitHubCopilotUsageMetrics28DayReportBuilder.builder()
                .downloadLinks(downloadLinks())
                .reportStartDay(LocalDate.parse("2025-09-16"))
                .reportEndDay(REPORT_DAY)
                .build();
    }

    static GitHubCopilotSeatDetails copilotSeatDetails() {
        return GitHubCopilotSeatDetailsBuilder.builder()
                .assignee(UsersCatalogFixtures.octocat())
                .organization(OrganizationsCatalogFixtures.organizationSimple())
                .assigningTeam(TeamCatalogFixtures.team())
                .pendingCancellationDate(LocalDate.parse("2025-11-01"))
                .lastActivityAt(Instant.parse("2025-10-10T09:00:00Z"))
                .lastActivityEditor("vscode/1.95.0")
                .lastAuthenticatedAt(Instant.parse("2025-10-10T08:00:00Z"))
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .planType(GitHubCopilotSeatPlanType.BUSINESS)
                .build();
    }

    static GitHubListCopilotSeatsResponse listCopilotSeatsResponse() {
        return new GitHubListCopilotSeatsResponse(1, List.of(copilotSeatDetails()));
    }

    public static GitHubCopilotContentExclusionRule copilotContentExclusionRule() {
        return GitHubCopilotContentExclusionRule.ofPath("/src/some-dir/kernel.rs");
    }

    public static GitHubCopilotContentExclusionRule copilotContentExclusionRuleIfAnyMatch() {
        return GitHubCopilotContentExclusionRule.ofIfAnyMatch(copilotContentExclusionIfAnyMatchDetails());
    }

    static GitHubCopilotContentExclusionRule.IfAnyMatchDetails copilotContentExclusionIfAnyMatchDetails() {
        return new GitHubCopilotContentExclusionRule.IfAnyMatchDetails(List.of("*.rs"));
    }

    public static GitHubCopilotContentExclusionRule copilotContentExclusionRuleIfNoneMatch() {
        return GitHubCopilotContentExclusionRule.ofIfNoneMatch(copilotContentExclusionIfNoneMatchDetails());
    }

    static GitHubCopilotContentExclusionRule.IfNoneMatchDetails copilotContentExclusionIfNoneMatchDetails() {
        return new GitHubCopilotContentExclusionRule.IfNoneMatchDetails(List.of("*.md"));
    }

    static GitHubSetCopilotContentExclusionForOrganizationPayload setCopilotContentExclusionForOrganizationPayload() {
        return new GitHubSetCopilotContentExclusionForOrganizationPayload(
                Map.of("octo-repo", List.of(copilotContentExclusionRule())));
    }

    static GitHubCopilotOrganizationContentExclusionDetails copilotOrganizationContentExclusionDetails() {
        return new GitHubCopilotOrganizationContentExclusionDetails(
                Map.of("octo-repo", List.of("/src/some-dir/kernel.rs")));
    }

    public static GitHubCopilotEnterpriseContentExclusionRule copilotEnterpriseContentExclusionRule() {
        return GitHubCopilotEnterpriseContentExclusionRule.ofPath("/src/some-dir/kernel.rs");
    }

    public static GitHubCopilotEnterpriseContentExclusionRule copilotEnterpriseContentExclusionRuleIfAnyMatch() {
        return GitHubCopilotEnterpriseContentExclusionRule.ofIfAnyMatch(
                copilotEnterpriseContentExclusionIfAnyMatchDetails());
    }

    static GitHubCopilotEnterpriseContentExclusionRule.IfAnyMatchDetails
            copilotEnterpriseContentExclusionIfAnyMatchDetails() {
        return new GitHubCopilotEnterpriseContentExclusionRule.IfAnyMatchDetails(List.of("*.rs"));
    }

    public static GitHubCopilotEnterpriseContentExclusionRule copilotEnterpriseContentExclusionRuleIfNoneMatch() {
        return GitHubCopilotEnterpriseContentExclusionRule.ofIfNoneMatch(
                copilotEnterpriseContentExclusionIfNoneMatchDetails());
    }

    static GitHubCopilotEnterpriseContentExclusionRule.IfNoneMatchDetails
            copilotEnterpriseContentExclusionIfNoneMatchDetails() {
        return new GitHubCopilotEnterpriseContentExclusionRule.IfNoneMatchDetails(List.of("*.md"));
    }

    static GitHubSetCopilotContentExclusionForEnterprisePayload setCopilotContentExclusionForEnterprisePayload() {
        return new GitHubSetCopilotContentExclusionForEnterprisePayload(
                Map.of("octo-repo", List.of(copilotEnterpriseContentExclusionRule())));
    }

    static GitHubCopilotEnterpriseContentExclusionDetails copilotEnterpriseContentExclusionDetails() {
        return new GitHubCopilotEnterpriseContentExclusionDetails(
                Map.of("octo-repo", List.of("/src/some-dir/kernel.rs")));
    }

    static GitHubGetUsageRecordsFilter getUsageRecordsFilter() {
        return GitHubGetUsageRecordsFilterBuilder.builder()
                .phrase("type:request")
                .order(GitHubGetUsageRecordsSortOrder.DESC)
                .build();
    }

    static GitHubAddCopilotSeatsForEnterpriseTeamsPayload addCopilotSeatsForEnterpriseTeamsPayload() {
        return GitHubAddCopilotSeatsForEnterpriseTeamsPayloadBuilder.builder()
                .selectedEnterpriseTeams(List.of("sample-selected_enterprise_teams"))
                .build();
    }

    static GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsPayload
            cancelCopilotSeatAssignmentForEnterpriseTeamsPayload() {
        return GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsPayloadBuilder.builder()
                .selectedEnterpriseTeams(List.of("sample-selected_enterprise_teams"))
                .build();
    }

    static GitHubAddCopilotSeatsForEnterpriseUsersPayload addCopilotSeatsForEnterpriseUsersPayload() {
        return GitHubAddCopilotSeatsForEnterpriseUsersPayloadBuilder.builder()
                .selectedUsernames(List.of("sample-selected_usernames"))
                .build();
    }

    static GitHubCancelCopilotSeatAssignmentForEnterpriseUsersPayload
            cancelCopilotSeatAssignmentForEnterpriseUsersPayload() {
        return GitHubCancelCopilotSeatAssignmentForEnterpriseUsersPayloadBuilder.builder()
                .selectedUsernames(List.of("sample-selected_usernames"))
                .build();
    }

    static GitHubSetCopilotCustomAgentsSourceForEnterprisePayload setCopilotCustomAgentsSourceForEnterprisePayload() {
        return GitHubSetCopilotCustomAgentsSourceForEnterprisePayloadBuilder.builder()
                .organizationId(1L)
                .createRuleset(true)
                .build();
    }

    static GitHubUpdateOrganizationModelPoliciesPayload updateOrganizationModelPoliciesPayload() {
        return GitHubUpdateOrganizationModelPoliciesPayloadBuilder.builder()
                .defaultAvailability(updateOrganizationModelPoliciesDefaultAvailability())
                .models(List.of(updateOrganizationModelPoliciesModel()))
                .build();
    }

    static GitHubUpdateOrganizationModelPoliciesDefaultAvailability
            updateOrganizationModelPoliciesDefaultAvailability() {
        return GitHubUpdateOrganizationModelPoliciesDefaultAvailabilityBuilder.builder()
                .policyState(GitHubUpdateOrganizationModelPoliciesDefaultAvailabilityPolicyState.ENABLED)
                .build();
    }

    static GitHubUpdateOrganizationModelPoliciesModel updateOrganizationModelPoliciesModel() {
        return GitHubUpdateOrganizationModelPoliciesModelBuilder.builder()
                .modelName("sample-model_name")
                .policyState(GitHubUpdateOrganizationModelPoliciesModelPolicyState.ENABLED)
                .build();
    }

    static GitHubListCopilotSeatsForEnterpriseResponse listCopilotSeatsForEnterpriseResponse() {
        return GitHubListCopilotSeatsForEnterpriseResponseBuilder.builder()
                .totalSeats(1)
                .seats(List.of(copilotSeatDetails()))
                .build();
    }

    static GitHubAddCopilotSeatsForEnterpriseTeamsResponse addCopilotSeatsForEnterpriseTeamsResponse() {
        return GitHubAddCopilotSeatsForEnterpriseTeamsResponseBuilder.builder()
                .seatsCreated(1)
                .build();
    }

    static GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResponse
            cancelCopilotSeatAssignmentForEnterpriseTeamsResponse() {
        return GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResponseBuilder.builder()
                .seatsCancelled(1)
                .build();
    }

    static GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsAcceptedResponse
            cancelCopilotSeatAssignmentForEnterpriseTeamsAcceptedResponse() {
        return GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsAcceptedResponseBuilder.builder()
                .message("sample-message")
                .build();
    }

    static GitHubAddCopilotSeatsForEnterpriseUsersResponse addCopilotSeatsForEnterpriseUsersResponse() {
        return GitHubAddCopilotSeatsForEnterpriseUsersResponseBuilder.builder()
                .seatsCreated(1)
                .build();
    }

    static GitHubCancelCopilotSeatAssignmentForEnterpriseUsersResponse
            cancelCopilotSeatAssignmentForEnterpriseUsersResponse() {
        return GitHubCancelCopilotSeatAssignmentForEnterpriseUsersResponseBuilder.builder()
                .seatsCancelled(1)
                .build();
    }

    static GitHubSetCopilotContentExclusionForEnterpriseResponse setCopilotContentExclusionForEnterpriseResponse() {
        return GitHubSetCopilotContentExclusionForEnterpriseResponseBuilder.builder()
                .message("sample-message")
                .build();
    }

    static GitHubGetCopilotCustomAgentsForEnterpriseResponse getCopilotCustomAgentsForEnterpriseResponse() {
        return GitHubGetCopilotCustomAgentsForEnterpriseResponseBuilder.builder()
                .customAgents(List.of(copilotCustomAgent()))
                .build();
    }

    static GitHubCopilotCustomAgent copilotCustomAgent() {
        return GitHubCopilotCustomAgentBuilder.builder()
                .name("sample-name")
                .filePath("sample-file_path")
                .url("sample-url")
                .build();
    }

    static GitHubGetCopilotCustomAgentsSourceForEnterpriseResponse getCopilotCustomAgentsSourceForEnterpriseResponse() {
        return GitHubGetCopilotCustomAgentsSourceForEnterpriseResponseBuilder.builder()
                .organization(getCopilotCustomAgentsSourceForEnterpriseResponseOrganization())
                .repository(getCopilotCustomAgentsSourceForEnterpriseResponseRepository())
                .build();
    }

    static GitHubGetCopilotCustomAgentsSourceForEnterpriseResponseOrganization
            getCopilotCustomAgentsSourceForEnterpriseResponseOrganization() {
        return GitHubGetCopilotCustomAgentsSourceForEnterpriseResponseOrganizationBuilder.builder()
                .id(1L)
                .login("sample-login")
                .build();
    }

    static GitHubGetCopilotCustomAgentsSourceForEnterpriseResponseRepository
            getCopilotCustomAgentsSourceForEnterpriseResponseRepository() {
        return GitHubGetCopilotCustomAgentsSourceForEnterpriseResponseRepositoryBuilder.builder()
                .id(1L)
                .name("sample-name")
                .fullName("sample-full_name")
                .build();
    }

    static GitHubSetCopilotCustomAgentsSourceForEnterpriseResponse setCopilotCustomAgentsSourceForEnterpriseResponse() {
        return GitHubSetCopilotCustomAgentsSourceForEnterpriseResponseBuilder.builder()
                .organization(setCopilotCustomAgentsSourceForEnterpriseResponseOrganization())
                .repository(setCopilotCustomAgentsSourceForEnterpriseResponseRepository())
                .ruleset(setCopilotCustomAgentsSourceForEnterpriseResponseRuleset())
                .build();
    }

    static GitHubSetCopilotCustomAgentsSourceForEnterpriseResponseOrganization
            setCopilotCustomAgentsSourceForEnterpriseResponseOrganization() {
        return GitHubSetCopilotCustomAgentsSourceForEnterpriseResponseOrganizationBuilder.builder()
                .id(1L)
                .login("sample-login")
                .avatarUrl("sample-avatar_url")
                .build();
    }

    static GitHubSetCopilotCustomAgentsSourceForEnterpriseResponseRepository
            setCopilotCustomAgentsSourceForEnterpriseResponseRepository() {
        return GitHubSetCopilotCustomAgentsSourceForEnterpriseResponseRepositoryBuilder.builder()
                .id(1L)
                .name("sample-name")
                .fullName("sample-full_name")
                .build();
    }

    static GitHubSetCopilotCustomAgentsSourceForEnterpriseResponseRuleset
            setCopilotCustomAgentsSourceForEnterpriseResponseRuleset() {
        return GitHubSetCopilotCustomAgentsSourceForEnterpriseResponseRulesetBuilder.builder()
                .id(1L)
                .name("sample-name")
                .enforcement("sample-enforcement")
                .build();
    }

    static GitHubGetUsageRecordsItem getUsageRecordsItem() {
        return GitHubGetUsageRecordsItemBuilder.builder()
                .type("sample-type")
                .userId(1L)
                .enterpriseId(1L)
                .githubRequestId("sample-github_request_id")
                .endpoint("sample-endpoint")
                .body("sample-body")
                .timestamp(1L)
                .eventId("sample-event_id")
                .build();
    }

    static GitHubGetCopilotSeatDetailsForEnterpriseUserResponse getCopilotSeatDetailsForEnterpriseUserResponse() {
        return GitHubGetCopilotSeatDetailsForEnterpriseUserResponseBuilder.builder()
                .totalSeats(1)
                .seats(List.of(copilotSeatDetails()))
                .build();
    }

    static GitHubGetOrganizationModelPoliciesResponse getOrganizationModelPoliciesResponse() {
        return GitHubGetOrganizationModelPoliciesResponseBuilder.builder()
                .defaultAvailability(getOrganizationModelPoliciesResponseDefaultAvailability())
                .models(List.of(getOrganizationModelPoliciesResponseModel()))
                .build();
    }

    static GitHubGetOrganizationModelPoliciesResponseDefaultAvailability
            getOrganizationModelPoliciesResponseDefaultAvailability() {
        return GitHubGetOrganizationModelPoliciesResponseDefaultAvailabilityBuilder.builder()
                .policyState(GitHubGetOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState.ENABLED)
                .editable(true)
                .build();
    }

    static GitHubGetOrganizationModelPoliciesResponseModel getOrganizationModelPoliciesResponseModel() {
        return GitHubGetOrganizationModelPoliciesResponseModelBuilder.builder()
                .modelName("sample-model_name")
                .displayName("sample-display_name")
                .policyState(GitHubGetOrganizationModelPoliciesResponseModelPolicyState.ENABLED)
                .build();
    }

    static GitHubUpdateOrganizationModelPoliciesResponse updateOrganizationModelPoliciesResponse() {
        return GitHubUpdateOrganizationModelPoliciesResponseBuilder.builder()
                .defaultAvailability(updateOrganizationModelPoliciesResponseDefaultAvailability())
                .models(List.of(updateOrganizationModelPoliciesResponseModel()))
                .build();
    }

    static GitHubUpdateOrganizationModelPoliciesResponseDefaultAvailability
            updateOrganizationModelPoliciesResponseDefaultAvailability() {
        return GitHubUpdateOrganizationModelPoliciesResponseDefaultAvailabilityBuilder.builder()
                .policyState(GitHubUpdateOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState.ENABLED)
                .editable(true)
                .build();
    }

    static GitHubUpdateOrganizationModelPoliciesResponseModel updateOrganizationModelPoliciesResponseModel() {
        return GitHubUpdateOrganizationModelPoliciesResponseModelBuilder.builder()
                .modelName("sample-model_name")
                .displayName("sample-display_name")
                .policyState(GitHubUpdateOrganizationModelPoliciesResponseModelPolicyState.ENABLED)
                .build();
    }

    private static List<String> downloadLinks() {
        return List.of(
                "https://example.com/copilot-usage-report-1.ndjson",
                "https://example.com/copilot-usage-report-2.ndjson");
    }
}
