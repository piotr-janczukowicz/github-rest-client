package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update a custom code security configuration for an enterprise.
 *
 * @param name The name of the code security configuration. Must be unique across the enterprise.
 * @param description A description of the code security configuration
 * @param advancedSecurity The enablement status of GitHub Advanced Security features. `enabled` will enable both Code
 *     Security and Secret Protection features. &gt; [!WARNING] &gt; `code_security` and `secret_protection` are
 *     deprecated values for this field. Prefer the individual `code_security` and `secret_protection` fields to set the
 *     status of these features.
 * @param codeSecurity The enablement status of GitHub Code Security features.
 * @param dependencyGraph The enablement status of Dependency Graph
 * @param dependencyGraphAutosubmitAction The enablement status of Automatic dependency submission
 * @param dependencyGraphAutosubmitActionOptions Feature options for Automatic dependency submission
 * @param dependabotAlerts The enablement status of Dependabot alerts
 * @param dependabotSecurityUpdates The enablement status of Dependabot security updates
 * @param codeScanningDefaultSetup The enablement status of code scanning default setup
 * @param codeScanningDefaultSetupOptions Code scanning default setup options
 * @param codeScanningOptions Code scanning options
 * @param codeScanningDelegatedAlertDismissal The enablement status of code scanning delegated alert dismissal
 * @param secretProtection The enablement status of GitHub Secret Protection features.
 * @param secretScanning The enablement status of secret scanning
 * @param secretScanningPushProtection The enablement status of secret scanning push protection
 * @param secretScanningValidityChecks The enablement status of secret scanning validity checks
 * @param secretScanningNonProviderPatterns The enablement status of secret scanning non-provider patterns
 * @param secretScanningGenericSecrets The enablement status of Copilot secret scanning
 * @param secretScanningDelegatedAlertDismissal The enablement status of secret scanning delegated alert dismissal
 * @param secretScanningExtendedMetadata The enablement status of secret scanning extended metadata
 * @param privateVulnerabilityReporting The enablement status of private vulnerability reporting
 * @param enforcement The enforcement status for a security configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/code-security/update-enterprise-configuration/requestBody")
@RecordBuilder
public record GitHubUpdateEnterpriseConfigurationPayload(
        @Nullable String name,
        @Nullable String description,

        @Nullable GitHubCodeSecurityAdvancedSecurity advancedSecurity,
        @Nullable GitHubCodeSecurityEnablement codeSecurity,
        @Nullable GitHubCodeSecurityEnablement dependencyGraph,
        @Nullable GitHubCodeSecurityEnablement dependencyGraphAutosubmitAction,
        @Nullable GitHubDependencyGraphAutosubmitActionOptions dependencyGraphAutosubmitActionOptions,
        @Nullable GitHubCodeSecurityEnablement dependabotAlerts,
        @Nullable GitHubCodeSecurityEnablement dependabotSecurityUpdates,
        @Nullable GitHubCodeSecurityEnablement codeScanningDefaultSetup,
        @Nullable GitHubCodeScanningDefaultSetupOptions codeScanningDefaultSetupOptions,
        @Nullable GitHubCodeScanningOptions codeScanningOptions,
        @Nullable GitHubCodeSecurityEnablement codeScanningDelegatedAlertDismissal,
        @Nullable GitHubCodeSecurityEnablement secretProtection,
        @Nullable GitHubCodeSecurityEnablement secretScanning,
        @Nullable GitHubCodeSecurityEnablement secretScanningPushProtection,
        @Nullable GitHubCodeSecurityEnablement secretScanningValidityChecks,
        @Nullable GitHubCodeSecurityEnablement secretScanningNonProviderPatterns,
        @Nullable GitHubCodeSecurityEnablement secretScanningGenericSecrets,
        @Nullable GitHubCodeSecurityEnablement secretScanningDelegatedAlertDismissal,
        @Nullable GitHubCodeSecurityEnablement secretScanningExtendedMetadata,
        @Nullable GitHubCodeSecurityEnablement privateVulnerabilityReporting,
        @Nullable GitHubCodeSecurityEnforcement enforcement) {

    public GitHubUpdateEnterpriseConfigurationPayload {
        description = requireMaxLengthIfPresent(description, 255, "description");
    }
}
