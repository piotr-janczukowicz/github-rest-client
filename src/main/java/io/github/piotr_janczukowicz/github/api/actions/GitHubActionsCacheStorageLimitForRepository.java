package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHub Actions cache storage policy for a repository.
 *
 * @param maxCacheSizeGb The maximum total cache size, in gigabytes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-cache-storage-limit-for-repository")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsCacheStorageLimitForRepository(
        @Nullable Integer maxCacheSizeGb) {}
