package io.github.piotr_janczukowicz.github.api.codesecurity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A code security configuration.
 *
 * @param id The ID of the code security configuration
 * @param name The name of the code security configuration. Must be unique within the organization.
 * @param targetType The type of the code security configuration.
 * @param description A description of the code security configuration
 * @param advancedSecurity The enablement status of GitHub Advanced Security
 * @param dependencyGraph The enablement status of Dependency Graph
 * @param dependencyGraphAutosubmitAction The enablement status of Automatic dependency submission
 * @param dependencyGraphAutosubmitActionOptions Feature options for Automatic dependency submission
 * @param dependabotAlerts The enablement status of Dependabot alerts
 * @param dependabotSecurityUpdates The enablement status of Dependabot security updates
 * @param dependabotDelegatedAlertDismissal The enablement status of Dependabot delegated alert dismissal
 * @param codeScanningOptions Feature options for code scanning
 * @param codeScanningDefaultSetup The enablement status of code scanning default setup
 * @param codeScanningDefaultSetupOptions Feature options for code scanning default setup
 * @param codeScanningDelegatedAlertDismissal The enablement status of code scanning delegated alert dismissal
 * @param secretScanning The enablement status of secret scanning
 * @param secretScanningPushProtection The enablement status of secret scanning push protection
 * @param secretScanningDelegatedBypass The enablement status of secret scanning delegated bypass
 * @param secretScanningDelegatedBypassOptions Feature options for secret scanning delegated bypass
 * @param secretScanningValidityChecks The enablement status of secret scanning validity checks
 * @param secretScanningNonProviderPatterns The enablement status of secret scanning non-provider patterns
 * @param secretScanningGenericSecrets The enablement status of Copilot secret scanning
 * @param secretScanningDelegatedAlertDismissal The enablement status of secret scanning delegated alert dismissal
 * @param secretScanningExtendedMetadata The enablement status of secret scanning extended metadata
 * @param privateVulnerabilityReporting The enablement status of private vulnerability reporting
 * @param enforcement The enforcement status for a security configuration
 * @param url The URL of the configuration
 * @param htmlUrl The URL of the configuration
 * @param createdAt Created at
 * @param updatedAt Updated at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/code-security-configuration")
@RecordBuilder
public record GitHubCodeSecurityConfiguration(
        @Nullable Long id,
        @Nullable String name,
        @Nullable GitHubCodeSecurityTargetType targetType,
        @Nullable String description,
        @Nullable GitHubCodeSecurityAdvancedSecurity advancedSecurity,
        @Nullable GitHubCodeSecurityEnablement dependencyGraph,
        @Nullable GitHubCodeSecurityEnablement dependencyGraphAutosubmitAction,
        @Nullable GitHubDependencyGraphAutosubmitActionOptions dependencyGraphAutosubmitActionOptions,
        @Nullable GitHubCodeSecurityEnablement dependabotAlerts,
        @Nullable GitHubCodeSecurityEnablement dependabotSecurityUpdates,
        @Nullable GitHubCodeSecurityEnablement dependabotDelegatedAlertDismissal,
        @Nullable GitHubCodeSecurityConfigurationCodeScanningOptions codeScanningOptions,
        @Nullable GitHubCodeSecurityEnablement codeScanningDefaultSetup,
        @Nullable GitHubCodeSecurityConfigurationCodeScanningDefaultSetupOptions codeScanningDefaultSetupOptions,
        @Nullable GitHubCodeSecurityEnablement codeScanningDelegatedAlertDismissal,
        @Nullable GitHubCodeSecurityEnablement secretScanning,
        @Nullable GitHubCodeSecurityEnablement secretScanningPushProtection,
        @Nullable GitHubCodeSecurityEnablement secretScanningDelegatedBypass,
        @Nullable GitHubSecretScanningDelegatedBypassOptions secretScanningDelegatedBypassOptions,
        @Nullable GitHubCodeSecurityEnablement secretScanningValidityChecks,
        @Nullable GitHubCodeSecurityEnablement secretScanningNonProviderPatterns,
        @Nullable GitHubCodeSecurityEnablement secretScanningGenericSecrets,
        @Nullable GitHubCodeSecurityEnablement secretScanningDelegatedAlertDismissal,
        @Nullable GitHubCodeSecurityEnablement secretScanningExtendedMetadata,
        @Nullable GitHubCodeSecurityEnablement privateVulnerabilityReporting,
        @Nullable GitHubCodeSecurityEnforcement enforcement,
        @Nullable String url,
        @Nullable String htmlUrl,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt) {}
