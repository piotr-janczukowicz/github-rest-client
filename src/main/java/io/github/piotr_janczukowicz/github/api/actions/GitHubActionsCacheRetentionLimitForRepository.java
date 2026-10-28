package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHub Actions cache retention policy for a repository.
 *
 * @param maxCacheRetentionDays The maximum duration, in days, for which caches are kept
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-cache-retention-limit-for-repository")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsCacheRetentionLimitForRepository(
        @Nullable Integer maxCacheRetentionDays) {}
