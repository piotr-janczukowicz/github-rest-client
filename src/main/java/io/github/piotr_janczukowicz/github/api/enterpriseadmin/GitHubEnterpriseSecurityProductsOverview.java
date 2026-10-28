package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Enterprise Security Products Stats
 *
 * @param totalRepos total repos
 * @param nonarchivedRepos nonarchived repos
 * @param secretScanningEnabledRepos secret scanning enabled repos
 * @param secretScanningPushProtectionEnabledRepos secret scanning push protection enabled repos
 * @param codeScanningEnabledRepos code scanning enabled repos
 * @param codeScanningPrReviewsEnabledRepos code scanning pr reviews enabled repos
 * @param codeScanningDefaultSetupEnabledRepos code scanning default setup enabled repos
 * @param codeScanningDefaultSetupEligibleRepos code scanning default setup eligible repos
 * @param dependabotAlertsEnabledRepos dependabot alerts enabled repos
 * @param dependabotSecurityUpdatesEnabledRepos dependabot security updates enabled repos
 * @param dependabotVersionUpdatesEnabledRepos dependabot version updates enabled repos
 * @param advancedSecurityEnabledRepos advanced security enabled repos
 * @param activeCommitters active committers
 * @param purchasedCommitters purchased committers
 * @param maximumCommitters maximum committers
 * @param secretProtectionLicenses secret protection licenses
 * @param secretProtectionActiveCommitters secret protection active committers
 * @param codeSecurityLicenses code security licenses
 * @param codeSecurityActiveCommitters code security active committers
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-security-products-overview")
@Flavors(GitHubFlavor.GHES)
public record GitHubEnterpriseSecurityProductsOverview(
        @Nullable Integer totalRepos,
        @Nullable Integer nonarchivedRepos,
        @Nullable Integer secretScanningEnabledRepos,
        @Nullable Integer secretScanningPushProtectionEnabledRepos,
        @Nullable Integer codeScanningEnabledRepos,
        @Nullable Integer codeScanningPrReviewsEnabledRepos,
        @Nullable Integer codeScanningDefaultSetupEnabledRepos,
        @Nullable Integer codeScanningDefaultSetupEligibleRepos,
        @Nullable Integer dependabotAlertsEnabledRepos,
        @Nullable Integer dependabotSecurityUpdatesEnabledRepos,
        @Nullable Integer dependabotVersionUpdatesEnabledRepos,
        @Nullable Integer advancedSecurityEnabledRepos,
        @Nullable Integer activeCommitters,
        @Nullable Integer purchasedCommitters,
        @Nullable Integer maximumCommitters,
        @Nullable Integer secretProtectionLicenses,
        @Nullable Integer secretProtectionActiveCommitters,
        @Nullable Integer codeSecurityLicenses,
        @Nullable Integer codeSecurityActiveCommitters) {}
