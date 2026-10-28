package io.github.piotr_janczukowicz.github.api.codesecurity;

import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import java.time.Instant;
import java.util.List;

final class CodeSecurityCatalogFixtures {

    private CodeSecurityCatalogFixtures() {}

    static GitHubCodeSecurityConfiguration codeSecurityConfiguration() {
        return GitHubCodeSecurityConfigurationBuilder.builder()
                .id(1L)
                .name("octo-security-configuration")
                .targetType(GitHubCodeSecurityTargetType.GLOBAL)
                .description("Security configuration for production repositories")
                .advancedSecurity(GitHubCodeSecurityAdvancedSecurity.ENABLED)
                .dependencyGraph(GitHubCodeSecurityEnablement.ENABLED)
                .dependencyGraphAutosubmitAction(GitHubCodeSecurityEnablement.ENABLED)
                .dependencyGraphAutosubmitActionOptions(dependencyGraphAutosubmitActionOptions())
                .dependabotAlerts(GitHubCodeSecurityEnablement.ENABLED)
                .dependabotSecurityUpdates(GitHubCodeSecurityEnablement.ENABLED)
                .dependabotDelegatedAlertDismissal(GitHubCodeSecurityEnablement.ENABLED)
                .codeScanningOptions(codeSecurityConfigurationCodeScanningOptions())
                .codeScanningDefaultSetup(GitHubCodeSecurityEnablement.ENABLED)
                .codeScanningDefaultSetupOptions(codeSecurityConfigurationCodeScanningDefaultSetupOptions())
                .codeScanningDelegatedAlertDismissal(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanning(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningPushProtection(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningDelegatedBypass(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningDelegatedBypassOptions(secretScanningDelegatedBypassOptions())
                .secretScanningValidityChecks(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningNonProviderPatterns(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningGenericSecrets(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningDelegatedAlertDismissal(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningExtendedMetadata(GitHubCodeSecurityEnablement.ENABLED)
                .privateVulnerabilityReporting(GitHubCodeSecurityEnablement.ENABLED)
                .enforcement(GitHubCodeSecurityEnforcement.ENFORCED)
                .url("https://api.github.com/orgs/octo-org/code-security/configurations/1")
                .htmlUrl("https://github.com/organizations/octo-org/settings/security_analysis/configurations/1")
                .createdAt(Instant.parse("2024-10-01T12:00:00Z"))
                .updatedAt(Instant.parse("2024-11-01T12:00:00Z"))
                .build();
    }

    static GitHubCodeSecurityConfigurationForRepository codeSecurityConfigurationForRepository() {
        return GitHubCodeSecurityConfigurationForRepositoryBuilder.builder()
                .status(GitHubCodeSecurityAttachmentStatus.ATTACHED)
                .configuration(codeSecurityConfiguration())
                .build();
    }

    static GitHubCodeSecurityConfigurationRepositories codeSecurityConfigurationRepositories() {
        return GitHubCodeSecurityConfigurationRepositoriesBuilder.builder()
                .status(GitHubCodeSecurityAttachmentStatus.ATTACHED)
                .repository(RepositoriesCatalogFixtures.helloWorldSimpleRepository())
                .build();
    }

    static GitHubCodeSecurityDefaultConfiguration codeSecurityDefaultConfiguration() {
        return GitHubCodeSecurityDefaultConfigurationBuilder.builder()
                .defaultForNewRepos(GitHubDefaultConfigurationForNewRepos.PUBLIC)
                .configuration(codeSecurityConfiguration())
                .build();
    }

    static GitHubCodeScanningDefaultSetupOptions codeScanningDefaultSetupOptions() {
        return GitHubCodeScanningDefaultSetupOptionsBuilder.builder()
                .runnerType(GitHubCodeScanningRunnerType.STANDARD)
                .runnerLabel("ubuntu-latest")
                .build();
    }

    static GitHubCodeScanningOptions codeScanningOptions() {
        return GitHubCodeScanningOptionsBuilder.builder().allowAdvanced(true).build();
    }

    static GitHubDependencyGraphAutosubmitActionOptions dependencyGraphAutosubmitActionOptions() {
        return GitHubDependencyGraphAutosubmitActionOptionsBuilder.builder()
                .labeledRunners(true)
                .build();
    }

    static GitHubCodeSecurityConfigurationCodeScanningOptions codeSecurityConfigurationCodeScanningOptions() {
        return GitHubCodeSecurityConfigurationCodeScanningOptionsBuilder.builder()
                .allowAdvanced(true)
                .build();
    }

    static GitHubCodeSecurityConfigurationCodeScanningDefaultSetupOptions
            codeSecurityConfigurationCodeScanningDefaultSetupOptions() {
        return GitHubCodeSecurityConfigurationCodeScanningDefaultSetupOptionsBuilder.builder()
                .runnerType(GitHubCodeScanningRunnerType.STANDARD)
                .runnerLabel("ubuntu-latest")
                .build();
    }

    static GitHubSecretScanningDelegatedBypassOptions secretScanningDelegatedBypassOptions() {
        return GitHubSecretScanningDelegatedBypassOptionsBuilder.builder()
                .reviewers(List.of(secretScanningDelegatedBypassReviewer()))
                .build();
    }

    static GitHubSecretScanningDelegatedBypassReviewer secretScanningDelegatedBypassReviewer() {
        return GitHubSecretScanningDelegatedBypassReviewerBuilder.builder()
                .reviewerId(42)
                .reviewerType(GitHubBypassReviewerType.TEAM)
                .mode(GitHubBypassMode.ALWAYS)
                .securityConfigurationId(7L)
                .build();
    }

    static GitHubSecretScanningDelegatedBypassOptionsInput secretScanningDelegatedBypassOptionsInput() {
        return GitHubSecretScanningDelegatedBypassOptionsInputBuilder.builder()
                .reviewers(List.of(secretScanningDelegatedBypassReviewerInput()))
                .build();
    }

    static GitHubSecretScanningDelegatedBypassReviewerInput secretScanningDelegatedBypassReviewerInput() {
        return GitHubSecretScanningDelegatedBypassReviewerInputBuilder.builder()
                .reviewerId(42)
                .reviewerType(GitHubBypassReviewerType.TEAM)
                .mode(GitHubBypassMode.ALWAYS)
                .build();
    }

    static GitHubSetConfigurationAsDefaultForEnterpriseResponse setConfigurationAsDefaultForEnterpriseResponse() {
        return GitHubSetConfigurationAsDefaultForEnterpriseResponseBuilder.builder()
                .defaultForNewRepos(GitHubDefaultForNewRepos.ALL)
                .configuration(codeSecurityConfiguration())
                .build();
    }

    static GitHubSetConfigurationAsDefaultResponse setConfigurationAsDefaultResponse() {
        return GitHubSetConfigurationAsDefaultResponseBuilder.builder()
                .defaultForNewRepos(GitHubDefaultForNewRepos.ALL)
                .configuration(codeSecurityConfiguration())
                .build();
    }

    static GitHubCreateConfigurationForEnterprisePayload createConfigurationForEnterprisePayload() {
        return GitHubCreateConfigurationForEnterprisePayloadBuilder.builder()
                .name("octo-security-configuration")
                .description("Security configuration for production repositories")
                .advancedSecurity(GitHubCodeSecurityAdvancedSecurity.ENABLED)
                .codeSecurity(GitHubCodeSecurityEnablement.ENABLED)
                .dependencyGraph(GitHubCodeSecurityEnablement.ENABLED)
                .dependencyGraphAutosubmitAction(GitHubCodeSecurityEnablement.ENABLED)
                .dependencyGraphAutosubmitActionOptions(dependencyGraphAutosubmitActionOptions())
                .dependabotAlerts(GitHubCodeSecurityEnablement.ENABLED)
                .dependabotSecurityUpdates(GitHubCodeSecurityEnablement.ENABLED)
                .codeScanningOptions(codeScanningOptions())
                .codeScanningDefaultSetup(GitHubCodeSecurityEnablement.ENABLED)
                .codeScanningDefaultSetupOptions(codeScanningDefaultSetupOptions())
                .codeScanningDelegatedAlertDismissal(GitHubCodeSecurityEnablement.ENABLED)
                .secretProtection(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanning(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningPushProtection(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningValidityChecks(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningNonProviderPatterns(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningGenericSecrets(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningDelegatedAlertDismissal(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningExtendedMetadata(GitHubCodeSecurityEnablement.ENABLED)
                .privateVulnerabilityReporting(GitHubCodeSecurityEnablement.ENABLED)
                .enforcement(GitHubCodeSecurityEnforcement.ENFORCED)
                .build();
    }

    static GitHubUpdateEnterpriseConfigurationPayload updateEnterpriseConfigurationPayload() {
        return GitHubUpdateEnterpriseConfigurationPayloadBuilder.builder()
                .name("octo-security-configuration")
                .description("Security configuration for production repositories")
                .advancedSecurity(GitHubCodeSecurityAdvancedSecurity.ENABLED)
                .codeSecurity(GitHubCodeSecurityEnablement.ENABLED)
                .dependencyGraph(GitHubCodeSecurityEnablement.ENABLED)
                .dependencyGraphAutosubmitAction(GitHubCodeSecurityEnablement.ENABLED)
                .dependencyGraphAutosubmitActionOptions(dependencyGraphAutosubmitActionOptions())
                .dependabotAlerts(GitHubCodeSecurityEnablement.ENABLED)
                .dependabotSecurityUpdates(GitHubCodeSecurityEnablement.ENABLED)
                .codeScanningDefaultSetup(GitHubCodeSecurityEnablement.ENABLED)
                .codeScanningDefaultSetupOptions(codeScanningDefaultSetupOptions())
                .codeScanningOptions(codeScanningOptions())
                .codeScanningDelegatedAlertDismissal(GitHubCodeSecurityEnablement.ENABLED)
                .secretProtection(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanning(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningPushProtection(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningValidityChecks(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningNonProviderPatterns(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningGenericSecrets(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningDelegatedAlertDismissal(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningExtendedMetadata(GitHubCodeSecurityEnablement.ENABLED)
                .privateVulnerabilityReporting(GitHubCodeSecurityEnablement.ENABLED)
                .enforcement(GitHubCodeSecurityEnforcement.ENFORCED)
                .build();
    }

    static GitHubAttachEnterpriseConfigurationPayload attachEnterpriseConfigurationPayload() {
        return GitHubAttachEnterpriseConfigurationPayloadBuilder.builder()
                .scope(GitHubAttachEnterpriseConfigurationScope.ALL)
                .build();
    }

    static GitHubSetConfigurationAsDefaultForEnterprisePayload setConfigurationAsDefaultForEnterprisePayload() {
        return GitHubSetConfigurationAsDefaultForEnterprisePayloadBuilder.builder()
                .defaultForNewRepos(GitHubDefaultForNewRepos.ALL)
                .build();
    }

    static GitHubCreateConfigurationPayload createConfigurationPayload() {
        return GitHubCreateConfigurationPayloadBuilder.builder()
                .name("octo-security-configuration")
                .description("Security configuration for production repositories")
                .advancedSecurity(GitHubCodeSecurityAdvancedSecurity.ENABLED)
                .codeSecurity(GitHubCodeSecurityEnablement.ENABLED)
                .dependencyGraph(GitHubCodeSecurityEnablement.ENABLED)
                .dependencyGraphAutosubmitAction(GitHubCodeSecurityEnablement.ENABLED)
                .dependencyGraphAutosubmitActionOptions(dependencyGraphAutosubmitActionOptions())
                .dependabotAlerts(GitHubCodeSecurityEnablement.ENABLED)
                .dependabotSecurityUpdates(GitHubCodeSecurityEnablement.ENABLED)
                .dependabotDelegatedAlertDismissal(GitHubCodeSecurityEnablement.ENABLED)
                .codeScanningOptions(codeScanningOptions())
                .codeScanningDefaultSetup(GitHubCodeSecurityEnablement.ENABLED)
                .codeScanningDefaultSetupOptions(codeScanningDefaultSetupOptions())
                .codeScanningDelegatedAlertDismissal(GitHubCodeSecurityEnablement.ENABLED)
                .secretProtection(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanning(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningPushProtection(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningDelegatedBypass(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningDelegatedBypassOptions(secretScanningDelegatedBypassOptionsInput())
                .secretScanningValidityChecks(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningNonProviderPatterns(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningGenericSecrets(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningDelegatedAlertDismissal(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningExtendedMetadata(GitHubCodeSecurityEnablement.ENABLED)
                .privateVulnerabilityReporting(GitHubCodeSecurityEnablement.ENABLED)
                .enforcement(GitHubCodeSecurityEnforcement.ENFORCED)
                .build();
    }

    static GitHubDetachConfigurationPayload detachConfigurationPayload() {
        return GitHubDetachConfigurationPayloadBuilder.builder()
                .selectedRepositoryIds(List.of(32L, 91L))
                .build();
    }

    static GitHubUpdateConfigurationPayload updateConfigurationPayload() {
        return GitHubUpdateConfigurationPayloadBuilder.builder()
                .name("octo-security-configuration")
                .description("Security configuration for production repositories")
                .advancedSecurity(GitHubCodeSecurityAdvancedSecurity.ENABLED)
                .codeSecurity(GitHubCodeSecurityEnablement.ENABLED)
                .dependencyGraph(GitHubCodeSecurityEnablement.ENABLED)
                .dependencyGraphAutosubmitAction(GitHubCodeSecurityEnablement.ENABLED)
                .dependencyGraphAutosubmitActionOptions(dependencyGraphAutosubmitActionOptions())
                .dependabotAlerts(GitHubCodeSecurityEnablement.ENABLED)
                .dependabotSecurityUpdates(GitHubCodeSecurityEnablement.ENABLED)
                .dependabotDelegatedAlertDismissal(GitHubCodeSecurityEnablement.ENABLED)
                .codeScanningDefaultSetup(GitHubCodeSecurityEnablement.ENABLED)
                .codeScanningDefaultSetupOptions(codeScanningDefaultSetupOptions())
                .codeScanningOptions(codeScanningOptions())
                .codeScanningDelegatedAlertDismissal(GitHubCodeSecurityEnablement.ENABLED)
                .secretProtection(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanning(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningPushProtection(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningDelegatedBypass(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningDelegatedBypassOptions(secretScanningDelegatedBypassOptionsInput())
                .secretScanningValidityChecks(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningNonProviderPatterns(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningGenericSecrets(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningDelegatedAlertDismissal(GitHubCodeSecurityEnablement.ENABLED)
                .secretScanningExtendedMetadata(GitHubCodeSecurityEnablement.ENABLED)
                .privateVulnerabilityReporting(GitHubCodeSecurityEnablement.ENABLED)
                .enforcement(GitHubCodeSecurityEnforcement.ENFORCED)
                .build();
    }

    static GitHubAttachConfigurationPayload attachConfigurationPayload() {
        return GitHubAttachConfigurationPayloadBuilder.builder()
                .scope(GitHubAttachConfigurationScope.ALL)
                .selectedRepositoryIds(List.of(32L, 91L))
                .build();
    }

    static GitHubSetConfigurationAsDefaultPayload setConfigurationAsDefaultPayload() {
        return GitHubSetConfigurationAsDefaultPayloadBuilder.builder()
                .defaultForNewRepos(GitHubDefaultForNewRepos.ALL)
                .build();
    }

    static GitHubGetRepositoriesForEnterpriseConfigurationFilter getRepositoriesForEnterpriseConfigurationFilter() {
        return GitHubGetRepositoriesForEnterpriseConfigurationFilterBuilder.builder()
                .status("attached")
                .build();
    }

    static GitHubGetConfigurationsForOrgFilter getConfigurationsForOrgFilter() {
        return GitHubGetConfigurationsForOrgFilterBuilder.builder()
                .targetType(GitHubGetConfigurationsForOrgTargetType.GLOBAL)
                .build();
    }

    static GitHubGetRepositoriesForConfigurationFilter getRepositoriesForConfigurationFilter() {
        return GitHubGetRepositoriesForConfigurationFilterBuilder.builder()
                .status("attached")
                .build();
    }
}
