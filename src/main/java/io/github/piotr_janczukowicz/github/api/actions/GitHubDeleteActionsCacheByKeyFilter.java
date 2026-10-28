package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code DELETE /repos/{owner}/{repo}/actions/caches}, excluding pagination.
 *
 * @param key A key for identifying the cache
 * @param ref The Git reference for the cache
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/actions/delete-actions-cache-by-key/parameters")
@RecordBuilder
public record GitHubDeleteActionsCacheByKeyFilter(
        String key, @Nullable String ref) {

    public GitHubDeleteActionsCacheByKeyFilter {
        requireNonNull(key, "key");
    }

    /**
     * Filter with the required key.
     *
     * @param key key of the cache
     * @return filter
     */
    public static GitHubDeleteActionsCacheByKeyFilter of(final String key) {
        return new GitHubDeleteActionsCacheByKeyFilter(key, null);
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        query.put("key", key);
        if (ref != null) {
            query.put("ref", ref);
        }

        return Collections.unmodifiableMap(query);
    }
}
