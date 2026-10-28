package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Cache usage of an enterprise or an organization.
 *
 * @param totalActiveCachesCount The count of active caches across all repositories
 * @param totalActiveCachesSizeInBytes The total size in bytes of all active cache items across all repositories
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-cache-usage-org-enterprise")
public record GitHubActionsCacheUsageOrgEnterprise(int totalActiveCachesCount, int totalActiveCachesSizeInBytes) {}
