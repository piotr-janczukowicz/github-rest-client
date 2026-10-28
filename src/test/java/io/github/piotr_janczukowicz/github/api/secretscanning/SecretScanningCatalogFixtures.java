package io.github.piotr_janczukowicz.github.api.secretscanning;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuiteTimePeriod;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.json.OptionalNull;
import java.time.Instant;
import java.util.List;

public final class SecretScanningCatalogFixtures {

    static final String CUSTOM_PATTERN_VERSION = "0ujsswThIGTUYm2K8FjOOfXtY1K";
    static final String START_DELIMITER = "\\A|[^0-9A-Za-z]";
    static final String END_DELIMITER = "\\z|[^0-9A-Za-z]";

    private SecretScanningCatalogFixtures() {}

    static GitHubBypassResponse bypassResponse() {
        return GitHubBypassResponseBuilder.builder()
                .id(1L)
                .reviewer(bypassResponseReviewer())
                .status(GitHubBypassResponseStatus.APPROVED)
                .createdAt(Instant.parse("2026-08-01T10:15:30Z"))
                .build();
    }

    static GitHubBypassResponseReviewer bypassResponseReviewer() {
        return GitHubBypassResponseReviewerBuilder.builder()
                .actorId(583_231L)
                .actorName("octocat")
                .build();
    }

    public static GitHubPushRuleBypassRequest pushRuleBypassRequest() {
        return GitHubPushRuleBypassRequestBuilder.builder()
                .id(1L)
                .number(1)
                .repository(pushRuleBypassRequestRepository())
                .organization(pushRuleBypassRequestOrganization())
                .requester(pushRuleBypassRequestRequester())
                .requestType("push_rule")
                .data(List.of(pushRuleBypassRequestData()))
                .resourceIdentifier("827efc6d56897b048c772eb4087f854f46256132")
                .status(GitHubPushRuleBypassRequestStatus.PENDING)
                .requesterComment("Needed for the release.")
                .expiresAt(Instant.parse("2026-08-08T10:15:30Z"))
                .createdAt(Instant.parse("2026-08-01T10:15:30Z"))
                .responses(List.of(bypassResponse()))
                .url("https://api.github.com/repos/octo-org/smile/bypass-requests/push-rules/1")
                .htmlUrl("https://github.com/octo-org/smile/exemptions/1")
                .build();
    }

    static GitHubPushRuleBypassRequestRepository pushRuleBypassRequestRepository() {
        return GitHubPushRuleBypassRequestRepositoryBuilder.builder()
                .id(1_296_269L)
                .name("smile")
                .fullName("octo-org/smile")
                .build();
    }

    static GitHubPushRuleBypassRequestOrganization pushRuleBypassRequestOrganization() {
        return GitHubPushRuleBypassRequestOrganizationBuilder.builder()
                .id(2000L)
                .name("octo-org")
                .build();
    }

    static GitHubPushRuleBypassRequestRequester pushRuleBypassRequestRequester() {
        return GitHubPushRuleBypassRequestRequesterBuilder.builder()
                .actorId(1001L)
                .actorName("monalisa")
                .build();
    }

    static GitHubPushRuleBypassRequestData pushRuleBypassRequestData() {
        return GitHubPushRuleBypassRequestDataBuilder.builder()
                .rulesetId(42L)
                .rulesetName("protect main")
                .totalViolations(1)
                .ruleType("commit_message_pattern")
                .build();
    }

    static GitHubSecretScanningCustomPatternToCreate customPatternToCreate() {
        return new GitHubSecretScanningCustomPatternToCreate(
                "My custom pattern",
                "a_string",
                START_DELIMITER,
                END_DELIMITER,
                List.of("[a-z]+"),
                List.of("password"));
    }

    static GitHubSecretScanningLocationCommit firstDetectedLocation() {
        return new GitHubSecretScanningLocationCommit(
                "/example/secrets.txt",
                1,
                1,
                1,
                40,
                "af5626b4a114abcb82d63db7c8082c3c4756e51b",
                "https://api.github.com/repos/octocat/Hello-World/git/blobs/af5626b4a114abcb82d63db7c8082c3c4756e51b",
                "af5626b4a114abcb82d63db7c8082c3c4756e51b",
                "https://api.github.com/repos/octocat/Hello-World/git/commits/af5626b4a114abcb82d63db7c8082c3c4756e51b",
                "https://github.com/octocat/Hello-World/blob/af5626b4a114abcb/example/secrets.txt#L1-L1");
    }

    static GitHubSecretScanningLocation commitLocation() {
        return new GitHubSecretScanningLocation(GitHubSecretScanningLocationType.COMMIT, firstDetectedLocation());
    }

    static GitHubSecretScanningPatternOverride githubPersonalAccessTokenOverride() {
        return new GitHubSecretScanningPatternOverride(
                "GITHUB_PERSONAL_ACCESS_TOKEN",
                "1a2b3c4d5e6f7g8h9i0jKLMNOPq",
                "github_personal_access_token",
                "GitHub Personal Access Token",
                10,
                50,
                2,
                20,
                5,
                GitHubSecretScanningCustomPatternPushProtectionSetting.DISABLED,
                GitHubSecretScanningPatternPushProtectionSetting.ENABLED,
                GitHubSecretScanningPatternPushProtectionSetting.ENABLED);
    }

    static GitHubSecretScanningPatternOverride customApiKeyOverride() {
        return new GitHubSecretScanningPatternOverride(
                "cp_2",
                CUSTOM_PATTERN_VERSION,
                "custom-api-key",
                "Custom API Key",
                15,
                36,
                3,
                20,
                20,
                GitHubSecretScanningCustomPatternPushProtectionSetting.DISABLED,
                GitHubSecretScanningPatternPushProtectionSetting.NOT_SET,
                GitHubSecretScanningPatternPushProtectionSetting.ENABLED);
    }

    static GitHubSecretScanningAlert repoSecretScanningAlert() {
        return new GitHubSecretScanningAlert(
                2,
                Instant.parse("2020-11-06T18:48:51Z"),
                Instant.parse("2020-11-06T19:00:00Z"),
                "https://api.github.com/repos/owner/private-repo/secret-scanning/alerts/2",
                "https://github.com/owner/private-repo/security/secret-scanning/2",
                "https://api.github.com/repos/owner/private-repo/secret-scanning/alerts/2/locations",
                GitHubSecretScanningAlertState.RESOLVED,
                GitHubSecretScanningAlertResolution.FALSE_POSITIVE,
                Instant.parse("2020-11-07T02:47:13Z"),
                UsersCatalogFixtures.monalisa(),
                "Example comment",
                "adafruit_io_key",
                "Adafruit IO Key",
                "Adafruit",
                "adafruit_io",
                "aio_XXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                true,
                UsersCatalogFixtures.monalisa(),
                Instant.parse("2020-11-06T21:48:51Z"),
                UsersCatalogFixtures.octocat(),
                "Example response",
                "Example comment",
                "https://github.com/owner/repo/secret_scanning_exemptions/1",
                GitHubSecretScanningAlertValidity.ACTIVE,
                false,
                false,
                false,
                firstDetectedLocation(),
                false,
                UsersCatalogFixtures.octocat(),
                "Requesting closure",
                "Approved closure",
                UsersCatalogFixtures.octocat());
    }

    static GitHubOrganizationSecretScanningAlert organizationSecretScanningAlert() {
        return new GitHubOrganizationSecretScanningAlert(
                2,
                Instant.parse("2020-11-06T18:48:51Z"),
                Instant.parse("2020-11-06T19:00:00Z"),
                "https://api.github.com/repos/owner/private-repo/secret-scanning/alerts/2",
                "https://github.com/owner/private-repo/security/secret-scanning/2",
                "https://api.github.com/repos/owner/private-repo/secret-scanning/alerts/2/locations",
                GitHubSecretScanningAlertState.RESOLVED,
                GitHubSecretScanningAlertResolution.FALSE_POSITIVE,
                Instant.parse("2020-11-07T02:47:13Z"),
                UsersCatalogFixtures.monalisa(),
                "adafruit_io_key",
                "Adafruit IO Key",
                "Adafruit",
                "adafruit_io",
                "aio_XXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                RepositoriesCatalogFixtures.helloWorldSimpleRepository(),
                true,
                UsersCatalogFixtures.monalisa(),
                Instant.parse("2020-11-06T21:48:51Z"),
                UsersCatalogFixtures.octocat(),
                "Example response",
                "Example comment",
                "https://github.com/owner/repo/secret_scanning_exemptions/1",
                "Example comment",
                GitHubSecretScanningAlertValidity.ACTIVE,
                false,
                false,
                false,
                firstDetectedLocation(),
                false,
                UsersCatalogFixtures.octocat(),
                "Requesting closure",
                "Approved closure",
                UsersCatalogFixtures.octocat());
    }

    static GitHubSecretScanningAlertWithMetadata secretScanningAlertWithMetadata() {
        return new GitHubSecretScanningAlertWithMetadata(
                42,
                Instant.parse("2020-11-06T18:18:30Z"),
                Instant.parse("2020-11-06T19:00:00Z"),
                "https://api.github.com/repos/owner/private-repo/secret-scanning/alerts/42",
                "https://github.com/owner/private-repo/security/secret-scanning/42",
                "https://api.github.com/repos/owner/private-repo/secret-scanning/alerts/42/locations",
                GitHubSecretScanningAlertState.RESOLVED,
                GitHubSecretScanningAlertResolution.USED_IN_TESTS,
                Instant.parse("2020-11-16T22:42:07Z"),
                UsersCatalogFixtures.monalisa(),
                "Example comment",
                "mailchimp_api_key",
                "Mailchimp API Key",
                "Mailchimp",
                "mailchimp",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX-us2",
                true,
                UsersCatalogFixtures.monalisa(),
                Instant.parse("2020-11-06T21:48:51Z"),
                UsersCatalogFixtures.octocat(),
                "Example response",
                "Example comment",
                "https://github.com/owner/repo/secret_scanning_exemptions/1",
                GitHubSecretScanningAlertValidity.UNKNOWN,
                false,
                false,
                false,
                firstDetectedLocation(),
                false,
                UsersCatalogFixtures.octocat(),
                "Requesting closure",
                "Approved closure",
                UsersCatalogFixtures.octocat(),
                List.of(new GitHubSecretScanningAlertMetadata("owner_email", "owner@example.com")));
    }

    static GitHubBulkCreateSecretScanningCustomPatternsPayload bulkCreateSecretScanningCustomPatternsPayload() {
        return new GitHubBulkCreateSecretScanningCustomPatternsPayload(
                List.of(new GitHubSecretScanningCustomPatternToCreate(
                        "My custom pattern", "a_string", null, null, null, null)));
    }

    static GitHubBulkDeleteSecretScanningCustomPatternsPayload bulkDeleteSecretScanningCustomPatternsPayload() {
        return new GitHubBulkDeleteSecretScanningCustomPatternsPayload(
                List.of(new GitHubSecretScanningCustomPatternToDelete(1L, CUSTOM_PATTERN_VERSION)),
                GitHubSecretScanningCustomPatternPostDeleteAction.DELETE_ALERTS);
    }

    static GitHubCreatePushProtectionBypassPayload createPushProtectionBypassPayload() {
        return new GitHubCreatePushProtectionBypassPayload(
                GitHubSecretScanningPushProtectionBypassReason.WILL_FIX_LATER, "2k4dM4tseyC5lPIsjl5emX9sPNk");
    }

    static GitHubUpdateSecretScanningAlertPayload updateSecretScanningAlertPayload() {
        return new GitHubUpdateSecretScanningAlertPayload(
                GitHubSecretScanningAlertState.RESOLVED,
                GitHubSecretScanningAlertResolution.FALSE_POSITIVE,
                null,
                null,
                null);
    }

    static GitHubUpdateSecretScanningCustomPatternPayload updateSecretScanningCustomPatternPayload() {
        return new GitHubUpdateSecretScanningCustomPatternPayload(
                "updated_string", null, null, null, null, OptionalNull.of(CUSTOM_PATTERN_VERSION));
    }

    static GitHubUpdateSecretScanningPatternConfigsPayload updateSecretScanningPatternConfigsPayload() {
        return new GitHubUpdateSecretScanningPatternConfigsPayload(
                OptionalNull.of(CUSTOM_PATTERN_VERSION),
                List.of(new GitHubSecretScanningProviderPatternSetting(
                        "GITHUB_PERSONAL_ACCESS_TOKEN", GitHubSecretScanningPatternPushProtectionSetting.ENABLED)),
                null);
    }

    static GitHubBulkCreateSecretScanningCustomPatternsResult bulkCreateSecretScanningCustomPatternsResult() {
        return new GitHubBulkCreateSecretScanningCustomPatternsResult(List.of(new GitHubSecretScanningCustomPattern(
                1,
                "My custom pattern",
                "a_string",
                "my-custom-pattern",
                GitHubSecretScanningCustomPatternState.PUBLISHED,
                true,
                START_DELIMITER,
                END_DELIMITER,
                List.of("[a-z]+"),
                List.of("password"),
                CUSTOM_PATTERN_VERSION,
                Instant.parse("2020-11-06T18:48:51Z"),
                Instant.parse("2020-11-07T02:47:13Z"))));
    }

    static GitHubSecretScanningCustomPattern secretScanningCustomPattern() {
        return new GitHubSecretScanningCustomPattern(
                1,
                "My custom pattern",
                "a_string",
                "my-custom-pattern",
                GitHubSecretScanningCustomPatternState.PUBLISHED,
                true,
                "\\A|[^0-9A-Za-z]",
                "\\z|[^0-9A-Za-z]",
                List.of("^[A-Za-z0-9]{20}$"),
                List.of("^test_"),
                "0ujsswThIGTUYm2K8FjOOfXtY1K",
                Instant.parse("2020-11-06T18:48:51Z"),
                Instant.parse("2020-11-07T02:47:13Z"));
    }

    static GitHubSecretScanningPatternConfiguration secretScanningPatternConfiguration() {
        return new GitHubSecretScanningPatternConfiguration(
                CUSTOM_PATTERN_VERSION, List.of(githubPersonalAccessTokenOverride()), List.of(customApiKeyOverride()));
    }

    static GitHubSecretScanningPushProtectionBypass secretScanningPushProtectionBypass() {
        return new GitHubSecretScanningPushProtectionBypass(
                GitHubSecretScanningPushProtectionBypassReason.WILL_FIX_LATER,
                Instant.parse("2020-11-07T02:47:13Z"),
                "GITHUB_PERSONAL_ACCESS_TOKEN");
    }

    static GitHubSecretScanningScanHistory secretScanningScanHistory() {
        return new GitHubSecretScanningScanHistory(
                List.of(new GitHubSecretScanningScan(
                        "incremental",
                        "completed",
                        Instant.parse("2020-11-06T18:48:51Z"),
                        Instant.parse("2020-11-06T18:48:00Z"))),
                List.of(new GitHubSecretScanningScan(
                        "discussion",
                        "in_progress",
                        Instant.parse("2024-10-07T02:51:00Z"),
                        Instant.parse("2024-10-07T02:47:00Z"))),
                List.of(new GitHubSecretScanningScan(
                        "git",
                        "completed",
                        Instant.parse("2024-10-07T02:50:00Z"),
                        Instant.parse("2024-10-07T02:47:00Z"))),
                List.of(new GitHubSecretScanningCustomPatternBackfillScan(
                        "custom_pattern_backfill",
                        "completed",
                        Instant.parse("2020-11-06T18:48:51Z"),
                        Instant.parse("2020-11-06T18:48:00Z"),
                        "My custom pattern",
                        "organization")),
                List.of(new GitHubSecretScanningScan(
                        "git",
                        "completed",
                        Instant.parse("2024-10-07T02:55:00Z"),
                        Instant.parse("2024-10-07T02:47:00Z"))));
    }

    static GitHubUpdateSecretScanningPatternConfigsResult updateSecretScanningPatternConfigsResult() {
        return new GitHubUpdateSecretScanningPatternConfigsResult("0ujsswThIGTUYm2K8FjOOfXtY1K");
    }

    static GitHubBulkCreateEnterpriseCustomPatternsPayload bulkCreateEnterpriseCustomPatternsPayload() {
        return new GitHubBulkCreateEnterpriseCustomPatternsPayload(
                List.of(new GitHubSecretScanningCustomPatternToCreate(
                        "My custom pattern", "a_string", null, null, null, null)));
    }

    static GitHubBulkCreateEnterpriseCustomPatternsResult bulkCreateEnterpriseCustomPatternsResult() {
        return new GitHubBulkCreateEnterpriseCustomPatternsResult(List.of(new GitHubSecretScanningCustomPattern(
                1,
                "My custom pattern",
                "a_string",
                "my-custom-pattern",
                GitHubSecretScanningCustomPatternState.PUBLISHED,
                true,
                START_DELIMITER,
                END_DELIMITER,
                List.of("[a-z]+"),
                List.of("password"),
                CUSTOM_PATTERN_VERSION,
                Instant.parse("2020-11-06T18:48:51Z"),
                Instant.parse("2020-11-07T02:47:13Z"))));
    }

    static GitHubBulkDeleteEnterpriseCustomPatternsPayload bulkDeleteEnterpriseCustomPatternsPayload() {
        return new GitHubBulkDeleteEnterpriseCustomPatternsPayload(
                List.of(new GitHubSecretScanningCustomPatternToDelete(1L, CUSTOM_PATTERN_VERSION)),
                GitHubBulkDeleteEnterpriseCustomPatternsPostDeleteAction.DELETE_ALERTS);
    }

    static GitHubUpdateEnterprisePatternConfigsPayload updateEnterprisePatternConfigsPayload() {
        return new GitHubUpdateEnterprisePatternConfigsPayload(
                OptionalNull.of(CUSTOM_PATTERN_VERSION),
                List.of(new GitHubUpdateEnterprisePatternConfigsProviderPatternSetting(
                        "GITHUB_PERSONAL_ACCESS_TOKEN",
                        GitHubUpdateEnterprisePatternConfigsPushProtectionSetting.ENABLED)),
                null);
    }

    static GitHubUpdateEnterprisePatternConfigsResult updateEnterprisePatternConfigsResult() {
        return new GitHubUpdateEnterprisePatternConfigsResult("0ujsswThIGTUYm2K8FjOOfXtY1K");
    }

    static GitHubSecretScanningBypassRequest secretScanningBypassRequest() {
        return GitHubSecretScanningBypassRequestBuilder.builder()
                .id(1L)
                .number(1)
                .repository(secretScanningBypassRequestRepository())
                .organization(secretScanningBypassRequestOrganization())
                .requester(secretScanningBypassRequestRequester())
                .requestType("push_protection")
                .data(List.of(secretScanningBypassRequestData()))
                .resourceIdentifier("827efc6d56897b048c772eb4087f854f46256132")
                .status(GitHubSecretScanningBypassRequestStatus.PENDING)
                .requesterComment("Needed for the release.")
                .expiresAt(Instant.parse("2026-08-08T10:15:30Z"))
                .createdAt(Instant.parse("2026-08-01T10:15:30Z"))
                .responses(List.of(bypassResponse()))
                .url("https://api.github.com/repos/octo-org/smile/bypass-requests/secret-scanning/1")
                .htmlUrl("https://github.com/octo-org/smile/exemptions/1")
                .build();
    }

    static GitHubSecretScanningBypassRequestRepository secretScanningBypassRequestRepository() {
        return GitHubSecretScanningBypassRequestRepositoryBuilder.builder()
                .id(1_296_269L)
                .name("smile")
                .fullName("octo-org/smile")
                .build();
    }

    static GitHubSecretScanningBypassRequestOrganization secretScanningBypassRequestOrganization() {
        return GitHubSecretScanningBypassRequestOrganizationBuilder.builder()
                .id(2000L)
                .name("octo-org")
                .build();
    }

    static GitHubSecretScanningBypassRequestRequester secretScanningBypassRequestRequester() {
        return GitHubSecretScanningBypassRequestRequesterBuilder.builder()
                .actorId(1001L)
                .actorName("monalisa")
                .build();
    }

    static GitHubSecretScanningBypassRequestData secretScanningBypassRequestData() {
        return GitHubSecretScanningBypassRequestDataBuilder.builder()
                .secretType("github_personal_access_token")
                .bypassReason(GitHubSecretScanningBypassRequestBypassReason.USED_IN_TESTS)
                .path("src/config.yml")
                .branch("main")
                .build();
    }

    static GitHubSecretScanningDismissalRequest secretScanningDismissalRequest() {
        return GitHubSecretScanningDismissalRequestBuilder.builder()
                .id(2L)
                .number(2)
                .repository(secretScanningDismissalRequestRepository())
                .organization(secretScanningDismissalRequestOrganization())
                .requester(secretScanningDismissalRequestRequester())
                .requestType("alert_dismissal")
                .data(List.of(secretScanningDismissalRequestData()))
                .resourceIdentifier("1")
                .status(GitHubSecretScanningDismissalRequestStatus.PENDING)
                .requesterComment("Fixed in a later commit.")
                .expiresAt(Instant.parse("2026-08-08T10:15:30Z"))
                .createdAt(Instant.parse("2026-08-01T10:15:30Z"))
                .responses(List.of(bypassResponse()))
                .url("https://api.github.com/repos/octo-org/smile/dismissal-requests/secret-scanning/1")
                .htmlUrl("https://github.com/octo-org/smile/security/secret-scanning/1")
                .build();
    }

    static GitHubSecretScanningDismissalRequestRepository secretScanningDismissalRequestRepository() {
        return GitHubSecretScanningDismissalRequestRepositoryBuilder.builder()
                .id(1_296_269L)
                .name("smile")
                .fullName("octo-org/smile")
                .build();
    }

    static GitHubSecretScanningDismissalRequestOrganization secretScanningDismissalRequestOrganization() {
        return GitHubSecretScanningDismissalRequestOrganizationBuilder.builder()
                .id(2000L)
                .name("octo-org")
                .build();
    }

    static GitHubSecretScanningDismissalRequestRequester secretScanningDismissalRequestRequester() {
        return GitHubSecretScanningDismissalRequestRequesterBuilder.builder()
                .actorId(1001L)
                .actorName("monalisa")
                .build();
    }

    static GitHubSecretScanningDismissalRequestData secretScanningDismissalRequestData() {
        return GitHubSecretScanningDismissalRequestDataBuilder.builder()
                .secretType("github_personal_access_token")
                .alertNumber("1")
                .reason(GitHubSecretScanningDismissalRequestReason.FIXED_LATER)
                .build();
    }

    static GitHubListEnterpriseBypassRequestsFilter listEnterpriseBypassRequestsFilter() {
        return GitHubListEnterpriseBypassRequestsFilterBuilder.builder()
                .organizationName("octo-org")
                .reviewer("octocat")
                .requester("monalisa")
                .timePeriod(GitHubRuleSuiteTimePeriod.DAY)
                .requestStatus(GitHubListEnterpriseBypassRequestsRequestStatus.OPEN)
                .build();
    }

    static GitHubListOrgBypassRequestsFilter listOrgBypassRequestsFilter() {
        return GitHubListOrgBypassRequestsFilterBuilder.builder()
                .repositoryName("smile")
                .reviewer("octocat")
                .requester("monalisa")
                .timePeriod(GitHubRuleSuiteTimePeriod.WEEK)
                .requestStatus(GitHubListOrgBypassRequestsRequestStatus.APPROVED)
                .build();
    }

    static GitHubListRepoBypassRequestsFilter listRepoBypassRequestsFilter() {
        return GitHubListRepoBypassRequestsFilterBuilder.builder()
                .reviewer("octocat")
                .requester("monalisa")
                .timePeriod(GitHubRuleSuiteTimePeriod.MONTH)
                .requestStatus(GitHubListRepoBypassRequestsRequestStatus.ALL)
                .build();
    }

    static GitHubListEnterpriseDismissalRequestsFilter listEnterpriseDismissalRequestsFilter() {
        return GitHubListEnterpriseDismissalRequestsFilterBuilder.builder()
                .organizationName("octo-org")
                .reviewer("octocat")
                .requester("monalisa")
                .timePeriod(GitHubRuleSuiteTimePeriod.HOUR)
                .requestStatus(GitHubListEnterpriseDismissalRequestsRequestStatus.DENIED)
                .build();
    }

    static GitHubListOrgDismissalRequestsFilter listOrgDismissalRequestsFilter() {
        return GitHubListOrgDismissalRequestsFilterBuilder.builder()
                .repositoryName("smile")
                .reviewer("octocat")
                .requester("monalisa")
                .timePeriod(GitHubRuleSuiteTimePeriod.DAY)
                .requestStatus(GitHubListOrgDismissalRequestsRequestStatus.EXPIRED)
                .build();
    }

    static GitHubListRepoDismissalRequestsFilter listRepoDismissalRequestsFilter() {
        return GitHubListRepoDismissalRequestsFilterBuilder.builder()
                .reviewer("octocat")
                .requester("monalisa")
                .timePeriod(GitHubRuleSuiteTimePeriod.DAY)
                .requestStatus(GitHubListRepoDismissalRequestsRequestStatus.OPEN)
                .build();
    }

    static GitHubReviewBypassRequestPayload reviewBypassRequestPayload() {
        return new GitHubReviewBypassRequestPayload(
                GitHubReviewBypassRequestStatus.REJECT, "This secret has not been revoked.");
    }

    static GitHubReviewDismissalRequestPayload reviewDismissalRequestPayload() {
        return new GitHubReviewDismissalRequestPayload(
                GitHubReviewDismissalRequestStatus.DENY, "The alert was a test value.");
    }

    static GitHubReviewBypassRequestResult reviewBypassRequestResult() {
        return new GitHubReviewBypassRequestResult(1L);
    }

    static GitHubReviewDismissalRequestResult reviewDismissalRequestResult() {
        return new GitHubReviewDismissalRequestResult(1L);
    }
}
