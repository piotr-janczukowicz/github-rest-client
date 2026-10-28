package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Code security and analysis settings of an enterprise.
 *
 * @param advancedSecurityEnabledForNewRepositories Whether GitHub Advanced Security is automatically enabled for new
 *     repositories
 * @param advancedSecurityEnabledForNewUserNamespaceRepositories Whether GitHub Advanced Security is automatically
 *     enabled for new user namespace repositories
 * @param dependabotAlertsEnabledForNewRepositories Whether Dependabot alerts are automatically enabled for new
 *     repositories
 * @param secretScanningEnabledForNewRepositories Whether secret scanning is automatically enabled for new repositories
 * @param secretScanningPushProtectionEnabledForNewRepositories Whether secret scanning push protection is automatically
 *     enabled for new repositories
 * @param secretScanningPushProtectionCustomLink An optional URL displayed to contributors who are blocked from pushing
 *     a secret
 * @param secretScanningNonProviderPatternsEnabledForNewRepositories Whether secret scanning of non-provider patterns is
 *     enabled for new repositories
 * @param secretScanningValidityChecksEnabled Whether automatic validity checks on supported partner tokens are enabled
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-security-analysis-settings")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubEnterpriseSecurityAnalysisSettings(
        boolean advancedSecurityEnabledForNewRepositories,
        @Nullable Boolean advancedSecurityEnabledForNewUserNamespaceRepositories,
        boolean dependabotAlertsEnabledForNewRepositories,
        boolean secretScanningEnabledForNewRepositories,
        boolean secretScanningPushProtectionEnabledForNewRepositories,
        @Nullable String secretScanningPushProtectionCustomLink,
        @Nullable Boolean secretScanningNonProviderPatternsEnabledForNewRepositories,
        @Nullable Boolean secretScanningValidityChecksEnabled) {}
