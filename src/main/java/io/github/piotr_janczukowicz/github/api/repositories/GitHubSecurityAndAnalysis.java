package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Security and analysis.
 *
 * @param advancedSecurity Enable or disable GitHub Advanced Security for the repository. For standalone Code Scanning
 *     or Secret Protection products, this parameter cannot be used.
 * @param codeSecurity Code security
 * @param dependabotSecurityUpdates Enable or disable Dependabot security updates for the repository.
 * @param secretScanning Secret scanning
 * @param secretScanningPushProtection Secret scanning push protection
 * @param secretScanningNonProviderPatterns Secret scanning non provider patterns
 * @param secretScanningAiDetection Secret scanning ai detection
 * @param secretScanningValidityChecks Secret scanning validity checks
 * @param secretScanningDelegatedAlertDismissal Secret scanning delegated alert dismissal
 * @param secretScanningDelegatedBypass Secret scanning delegated bypass
 * @param secretScanningDelegatedBypassOptions Secret scanning delegated bypass options
 */
@CatalogSchema("components/schemas/security-and-analysis")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecurityAndAnalysis(
        @Nullable GitHubSecurityAndAnalysisStatus advancedSecurity,
        @Nullable GitHubSecurityAndAnalysisStatus codeSecurity,
        @Nullable GitHubSecurityAndAnalysisStatus dependabotSecurityUpdates,
        @Nullable GitHubSecurityAndAnalysisStatus secretScanning,
        @Nullable GitHubSecurityAndAnalysisStatus secretScanningPushProtection,
        @Nullable GitHubSecurityAndAnalysisStatus secretScanningNonProviderPatterns,
        @Nullable GitHubSecurityAndAnalysisStatus secretScanningAiDetection,
        @Nullable GitHubSecurityAndAnalysisStatus secretScanningValidityChecks,
        @Nullable GitHubSecurityAndAnalysisStatus secretScanningDelegatedAlertDismissal,
        @Nullable GitHubSecurityAndAnalysisStatus secretScanningDelegatedBypass,
        @Nullable GitHubSecretScanningDelegatedBypassOptions secretScanningDelegatedBypassOptions) {}
