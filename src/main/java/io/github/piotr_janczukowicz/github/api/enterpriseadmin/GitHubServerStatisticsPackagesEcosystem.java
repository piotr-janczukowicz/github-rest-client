package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Package ecosystem metrics.
 *
 * @param name The name of the package ecosystem
 * @param enabled Whether the package ecosystem is enabled, disabled, or read-only
 * @param publishedPackagesCount The number of published packages
 * @param privatePackagesCount The number of private packages
 * @param publicPackagesCount The number of public packages
 * @param internalPackagesCount The number of internal packages
 * @param userPackagesCount The number of user packages
 * @param organizationPackagesCount The number of organization packages
 * @param dailyDownloadCount The number of packages downloaded in the prior 24 hours
 * @param dailyUpdateCount The number of packages updated in the prior 24 hours
 * @param dailyDeleteCount The number of packages deleted in the prior 24 hours
 * @param dailyCreateCount The number of packages created in the prior 24 hours
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/server-statistics-packages/properties/ecosystems/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubServerStatisticsPackagesEcosystem(
        @Nullable GitHubServerStatisticsPackagesEcosystemName name,
        @Nullable GitHubServerStatisticsPackagesEcosystemEnabled enabled,
        @Nullable Integer publishedPackagesCount,
        @Nullable Integer privatePackagesCount,
        @Nullable Integer publicPackagesCount,
        @Nullable Integer internalPackagesCount,
        @Nullable Integer userPackagesCount,
        @Nullable Integer organizationPackagesCount,
        @Nullable Integer dailyDownloadCount,
        @Nullable Integer dailyUpdateCount,
        @Nullable Integer dailyDeleteCount,
        @Nullable Integer dailyCreateCount) {}
