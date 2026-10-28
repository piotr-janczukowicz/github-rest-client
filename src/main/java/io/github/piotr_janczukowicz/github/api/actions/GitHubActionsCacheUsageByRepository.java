package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * GitHub Actions cache usage of a repository.
 *
 * @param fullName The repository owner and name for the cache usage being shown
 * @param activeCachesSizeInBytes The sum of the size in bytes of all the active cache items in the repository
 * @param activeCachesCount The number of active caches in the repository
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-cache-usage-by-repository")
public record GitHubActionsCacheUsageByRepository(String fullName, int activeCachesSizeInBytes, int activeCachesCount) {

    public GitHubActionsCacheUsageByRepository {
        requireNonNull(fullName, "fullName");
    }
}
