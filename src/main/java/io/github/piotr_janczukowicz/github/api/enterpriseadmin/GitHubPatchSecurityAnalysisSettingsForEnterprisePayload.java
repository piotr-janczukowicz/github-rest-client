package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Code security and analysis settings to update for an enterprise.
 *
 * @param advancedSecurityEnabledForNewRepositories Whether GitHub Advanced Security is automatically enabled for new
 *     repositories
 * @param advancedSecurityEnabledNewUserNamespaceRepos Whether GitHub Advanced Security is automatically enabled for new
 *     user namespace repositories
 * @param dependabotAlertsEnabledForNewRepositories Whether Dependabot alerts are automatically enabled for new
 *     repositories
 * @param secretScanningEnabledForNewRepositories Whether secret scanning is automatically enabled for new repositories
 * @param secretScanningPushProtectionEnabledForNewRepositories Whether secret scanning push protection is automatically
 *     enabled for new repositories
 * @param secretScanningPushProtectionCustomLink The URL displayed to contributors who are blocked from pushing a secret
 * @param secretScanningNonProviderPatternsEnabledForNewRepositories Whether secret scanning of non-provider patterns is
 *     enabled for new repositories
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/secret-scanning/patch-security-analysis-settings-for-enterprise/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubPatchSecurityAnalysisSettingsForEnterprisePayload(
        @Nullable Boolean advancedSecurityEnabledForNewRepositories,
        @Nullable Boolean advancedSecurityEnabledNewUserNamespaceRepos,
        @Nullable Boolean dependabotAlertsEnabledForNewRepositories,
        @Nullable Boolean secretScanningEnabledForNewRepositories,
        @Nullable Boolean secretScanningPushProtectionEnabledForNewRepositories,
        @Nullable String secretScanningPushProtectionCustomLink,
        @Nullable Boolean secretScanningNonProviderPatternsEnabledForNewRepositories) {}
