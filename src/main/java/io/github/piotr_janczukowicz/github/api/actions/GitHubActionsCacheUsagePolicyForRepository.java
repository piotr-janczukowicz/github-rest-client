package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * GitHub Actions cache usage policy for a repository.
 *
 * @param repoCacheSizeLimitInGb The size limit for the sum of all caches, in gigabytes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-cache-usage-policy-for-repository")
@Flavors(GitHubFlavor.GHES)
public record GitHubActionsCacheUsagePolicyForRepository(int repoCacheSizeLimitInGb) {}
