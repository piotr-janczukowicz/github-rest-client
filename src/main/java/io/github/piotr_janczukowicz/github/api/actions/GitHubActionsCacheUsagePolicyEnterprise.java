package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHub Actions cache usage policy for an enterprise.
 *
 * @param repoCacheSizeLimitInGb For repositories in an enterprise, the default size limit for the sum of all caches in
 *     a repository, in gigabytes
 * @param maxRepoCacheSizeLimitInGb For repositories in an enterprise, the maximum value that can be set as the limit
 *     for the sum of all caches in a repository, in gigabytes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-cache-usage-policy-enterprise")
@Flavors(GitHubFlavor.GHES)
public record GitHubActionsCacheUsagePolicyEnterprise(
        @Nullable Integer repoCacheSizeLimitInGb, @Nullable Integer maxRepoCacheSizeLimitInGb) {}
