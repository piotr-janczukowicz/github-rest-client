package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * An actions cache entry of a repository.
 *
 * @param id The ID of the cache
 * @param ref The ref of the cache
 * @param key The key of the cache
 * @param version The version of the cache
 * @param lastAccessedAt The last accessed time of the cache
 * @param createdAt The creation time of the cache
 * @param sizeInBytes The size of the cache in bytes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-cache-list/properties/actions_caches/items")
public record GitHubActionsCache(
        @Nullable Long id,
        @Nullable String ref,
        @Nullable String key,
        @Nullable String version,
        @Nullable Instant lastAccessedAt,
        @Nullable Instant createdAt,
        @Nullable Integer sizeInBytes) {}
